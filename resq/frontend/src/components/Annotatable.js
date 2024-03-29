import * as React from "react";
import {fromRange, toRange} from "xpath-range";
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Popover,
    TextField
} from "@mui/material";
import Paper from "@mui/material/Paper";
import {useState} from "react";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import {Add, Delete, Edit} from "@mui/icons-material";
import {useQuery, useQueryClient} from "@tanstack/react-query";
import {ANNO_BASE_URL, deleteAnno, editAnno, getAllAnnos} from "../AnnoService";

const getXpathParameters = (xpath) => {
    const startOffset = xpath.startOffset
    const endOffset = xpath.endOffset
    let startContainer = xpath.start
    // /div[2]/p[7]/text()[1] -> /div[2]/p[7]/text[1]
    //startContainer = startContainer.replace(/[()]/g, "")
    let endContainer = xpath.end
    //endContainer = endContainer.replace(/[()]/g, "")
    return {
        "type": "RangeSelector",
        "startSelector": {
            "type": "XPathSelector",
            "value": startContainer
        },
        "endSelector": {
            "type": "XPathSelector",
            "value": endContainer
        },
        "refinedBy": {
            "type": "TextPositionSelector",
            "start": startOffset,
            "end": endOffset
        }
    }
}

const getSafeRanges = dangerousRange => {
    const commonAncestor = dangerousRange.commonAncestorContainer;

    const s = new Array(0);
    const rs = new Array(0);
    if (dangerousRange.startContainer !== commonAncestor)
        for (let i = dangerousRange.startContainer; i !== commonAncestor; i = i.parentNode)
            s.push(i);


    if (s.length > 0) {
        for (let j = 0; j < s.length; j++) {
            const xs = document.createRange();
            if (j) {
                xs.setStartAfter(s[j - 1]);
                xs.setEndAfter(s[j].lastChild);
            } else {
                xs.setStart(s[j], dangerousRange.startOffset);
                xs.setEndAfter(
                    (s[j].nodeType === Node.TEXT_NODE)
                        ? s[j] : s[j].lastChild
                );
            }
            rs.push(xs);
        }
    }

    const e = new Array(0)
    const re = new Array(0);
    if (dangerousRange.endContainer !== commonAncestor)
        for (let k = dangerousRange.endContainer; k !== commonAncestor; k = k.parentNode)
            e.push(k);


    if (e.length > 0) {
        for (let m = 0; m < e.length; m++) {
            const xe = document.createRange();
            if (m) {
                xe.setStartBefore(e[m].firstChild);
                xe.setEndBefore(e[m - 1]);
            } else {
                xe.setStartBefore(
                    (e[m].nodeType === Node.TEXT_NODE)
                        ? e[m] : e[m].firstChild
                );
                xe.setEnd(e[m], dangerousRange.endOffset);
            }
            re.unshift(xe);
        }
    }

    const xm = document.createRange();
    if ((s.length > 0) && (e.length > 0)) {
        xm.setStartAfter(s[s.length - 1]);
        xm.setEndBefore(e[e.length - 1]);
    } else {
        return [dangerousRange];
    }

    rs.push(xm);

    return rs.concat(re);
}

export default function Annotatable(props) {
    const [open, setOpen] = React.useState(false);
    const [anchorEl, setAnchorEl] = React.useState(null);
    const [annotation, setAnnotation] = useState(null)
    //const annotations = useRef(mock_annots)
    const [editAnnotationValue, setEditAnnotationValue] = useState("")
    const [openDialog, setOpenDialog] = React.useState(false);
    const [editAnnotationTarget, setEditAnnotationTarget] = React.useState(null);

    const annotations = useQuery({queryKey: ['annos'], queryFn: getAllAnnos})
    const queryClient = useQueryClient()

    const invalidate = () => queryClient.invalidateQueries({queryKey: ['annos']})

    const handleClose = () => {
        setOpen(false);
        setAnnotation(null)
        window.getSelection().removeAllRanges()
    };

    const handleMouseUp = () => {
        const selection = window.getSelection();

        // Skip if selection has a length of 0
        if (!selection || selection.anchorOffset === selection.focusOffset) {
            return;
        }

        const getBoundingClientRect = () => {
            return selection.getRangeAt(0).getBoundingClientRect();
        };

        if (selection.rangeCount === 0)
            return
        const range = selection.getRangeAt(0)
        let ancestor = range.commonAncestorContainer
        if (range.commonAncestorContainer.nodeName === "#text")
            ancestor = ancestor.parentElement
        let content = ancestor.closest(".anno-root")
        if (content === null)
            return;

        setOpen(true);

        setAnchorEl({getBoundingClientRect, nodeType: 1});
    };

    const handleMakeAnnotation = () => {
        const selection = getSelection()
        if (selection.rangeCount === 0)
            return
        const range = selection.getRangeAt(0)
        let ancestor = range.commonAncestorContainer
        if (range.commonAncestorContainer.nodeName === "#text")
            ancestor = ancestor.parentElement
        let content = ancestor.closest(".anno-root")
        if (content === null) {
            handleClose()
            return;
        }
        let xpath = fromRange(range, content)

        const rangeSelector = getXpathParameters(xpath)
        const xPathSelector = {
            "type": "XPathSelector",
            "value": `//div[@id="${content.id}"]`,
            "refinedBy": rangeSelector
        }
        const anno = {
            "@context": "http://www.w3.org/ns/anno.jsonld",
            "id": `${ANNO_BASE_URL}/${crypto.randomUUID()}`,
            "type": "Annotation",
            //"bodyValue": "Test test1 test2",
            "target": {
                "source": "https://resq.org.tr",
                "selector": xPathSelector
            }
        }
        setEditAnnotationTarget(anno)
        setEditAnnotationValue("")
        setOpenDialog(true)
        handleClose()
    }

    const handleEditAnnotation = () => {
        setEditAnnotationTarget(annotation)
        setEditAnnotationValue(annotation.bodyValue)
        setOpenDialog(true)
        handleClose()
    }

    const handleDeleteAnnotation = async () => {
        await deleteAnno(annotation)
        await invalidate()
        handleClose()
    }

    const handleCloseDialog = () => {
        setOpenDialog(false)
        handleClose()
    }

    const handleSaveAnnotation = async () => {
        await editAnno({
            ...editAnnotationTarget,
            bodyValue: editAnnotationValue
        })
        await invalidate()
        handleCloseDialog()
        handleClose()
    }

    function doAnnotRender() {
        for (let mark of document.querySelectorAll("mark")) {
            const mark_annot_id = mark.getAttribute("annot_id")
            if (annotations?.data?.every(({id}) => id !== mark_annot_id)) {
                mark.replaceWith(document.createTextNode(mark.innerText))
            }
        }
        for (const annotation of annotations?.data || []) {
            try {
                const {
                    id,
                    "target": {
                        "selector": {
                            "value": xpath_query,
                            "refinedBy": {
                                "startSelector": {"value": xpathStartSelector},
                                "endSelector": {"value": xpathEndSelector},
                                "refinedBy": {"start": startOffset, "end": endOffset}
                            }
                        }
                    }
                } = annotation

                const root = document.evaluate(xpath_query, document).iterateNext()

                const range = toRange(xpathStartSelector, startOffset, xpathEndSelector, endOffset, root)

                const onMarkClick = () => {
                    setOpen(true);
                    setAnchorEl({getBoundingClientRect: () => range.getBoundingClientRect(), nodeType: 1});
                    setAnnotation(annotations?.data?.filter(anno => id === anno.id)[0])
                }

                const safe_ranges = getSafeRanges(range)

                if (!safe_ranges.every(range => range.commonAncestorContainer.nodeName !== "MARK"))
                    continue

                console.log(safe_ranges)
                for (let range of safe_ranges) {
                    if (range.collapsed)
                        continue

                    let selected = range.extractContents()
                    let marked = document.createElement("mark")
                    marked.setAttribute("annot_id", id)
                    marked.addEventListener("click", onMarkClick)
                    marked.style.padding = "0"
                    marked.appendChild(selected)
                    range.insertNode(marked)
                }
            } catch (e) {
            }
        }
    }

    const id = open ? 'virtual-element-popover' : undefined;

    setTimeout(() => doAnnotRender(), 100)

    return <div onMouseUp={handleMouseUp} key={annotations?.data} {...props}>
        <Dialog open={openDialog} onClose={handleCloseDialog}>
            <DialogTitle>Edit Annotation</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    label="Annotation text"
                    fullWidth
                    variant="standard"
                    value={editAnnotationValue}
                    onChange={(event) => {
                        setEditAnnotationValue(event.target.value);
                    }}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={handleSaveAnnotation}>Save</Button>
                <Button onClick={handleCloseDialog}>Cancel</Button>
            </DialogActions>
        </Dialog>
        <Popover
            id={id}
            open={open}
            anchorEl={anchorEl}
            anchorOrigin={{vertical: 'bottom', horizontal: 'left'}}
            onClose={handleClose}
        >
            <Paper>
                {annotation ?
                    <Box sx={{
                        display: "flex",
                        flexDirection: "column",
                        flexWrap: 'nowrap',
                        padding: "12px",
                        maxWidth: "30vw",
                        justifyContent: "right"
                    }}>
                        <Typography variant="body1">{annotation.bodyValue}</Typography>
                        <Box sx={{
                            display: "flex",
                            flexDirection: "row",
                            flexWrap: 'nowrap',
                            width: "100%",
                        }}>
                            <Button sx={{minWidth: "unset", marginLeft: "auto"}} onClick={handleEditAnnotation}><Edit/></Button>
                            <Button sx={{minWidth: "unset"}} onClick={handleDeleteAnnotation}><Delete/></Button>
                        </Box>
                    </Box>
                    :
                    <Button sx={{minWidth: "unset"}} onClick={handleMakeAnnotation}><Add/></Button>}
            </Paper>
        </Popover>
        {props.children}
    </div>
}