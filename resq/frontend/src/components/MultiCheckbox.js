import {
    Button,
    Chip, DialogActions,
    DialogContent, FormControl,
    InputLabel,
    MenuItem,
    OutlinedInput,
    Select, TextField,
    useTheme
} from "@mui/material";
import * as React from "react";
import {useEffect, useId, useState} from "react";
import Box from "@mui/material/Box";
import Dialog from "@mui/material/Dialog";

function getDropDownStyles(name, personName, theme) {
    return {
        fontWeight:
            personName.indexOf(name) === -1
                ? theme.typography.fontWeightRegular
                : theme.typography.fontWeightMedium,
    };
}

export const MultiCheckbox = ({name, choices, onChosenChanged}) => {
    const theme = useTheme();

    const ITEM_HEIGHT = 48;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
        PaperProps: {
            style: {
                maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
                width: 250,
            },
        },
    };

    const [currentChoices, setCurrentChoices] = React.useState([]);
    const label_id = useId();
    const select_id = useId();
    const input_id = useId();


    const handleChange = (event) => {
        const {
            target: {value},
        } = event;
        setCurrentChoices(
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    useEffect(() => {
        onChosenChanged && onChosenChanged(choices.filter(a => currentChoices.indexOf(a.id) !== -1))
    }, [onChosenChanged, currentChoices, choices])

    return <FormControl sx={{m: 1}}>
        <InputLabel id={label_id}>{name}</InputLabel>
        <Select
            sx={{minWidth: "100px"}}
            labelId={label_id}
            multiple
            id={select_id}
            value={currentChoices}
            onChange={handleChange}
            input={<OutlinedInput id={input_id} label={name}/>}
            renderValue={(selected) => (
                <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                    {choices.filter(a => selected.indexOf(a.id) !== -1).map((value) => (
                        <Chip key={value.id} label={value.data}/>
                    ))}
                </Box>
            )}
            MenuProps={MenuProps}
        >
            {choices.map((value) => (
                <MenuItem
                    key={value.id}
                    value={value.id}
                    style={getDropDownStyles(value.id, currentChoices, theme)}
                >
                    {value.data}
                </MenuItem>
            ))}
        </Select>
    </FormControl>
}

export const AmountSelector = ({name, onChosenChanged}) => {
    const [open, setOpen] = React.useState(false);

    const [min, setMin] = useState(null)
    const [max, setMax] = useState(null)

    const handleClose = () => {
        setOpen(false);
        setCurrentChoices([`${min}-${max}`])
    };


    const choices = ['All', '1-9', '10-99', '100-999', '1000+', "Custom"]
    const theme = useTheme();

    const ITEM_HEIGHT = 48;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
        PaperProps: {
            style: {
                maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
                width: 250,
            },
        },
    };

    const [currentChoices, setCurrentChoices] = React.useState(["All"]);
    const label_id = useId();
    const select_id = useId();
    const input_id = useId();


    const handleChange = (event) => {
        const {
            target: {value},
        } = event;
        if (value === "Custom")
            setOpen(true);
        else
            setCurrentChoices(
                typeof value === 'string' ? value.split(',') : value,
            );
    };

    useEffect(() => {
        onChosenChanged && onChosenChanged(currentChoices)
    }, [onChosenChanged, currentChoices])

    return <>
        <React.Fragment>
            <Dialog open={open} onClose={handleClose}>
                <DialogContent>
                    <TextField
                        sx={{m: 1}}
                        autoFocus
                        id="min"
                        value={min}
                        onChange={e => setMin(e.target.value)}
                        label="Min"
                        type="number"
                        variant="standard"
                    />
                    <TextField
                        sx={{m: 1}}
                        autoFocus
                        id="max"
                        value={max}
                        onChange={e => setMax(e.target.value)}
                        label="Max"
                        type="number"
                        variant="standard"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Set</Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
        <FormControl sx={{m: 1}}>
            <InputLabel id={label_id}>{name}</InputLabel>
            <Select
                sx={{minWidth: "100px"}}
                labelId={label_id}
                id={select_id}
                value={currentChoices}
                onChange={handleChange}
                input={<OutlinedInput id={input_id} label={name}/>}
                renderValue={(selected) => (
                    <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                        {selected.map((value) => (
                            <div key={value}>{value}</div>
                        ))}
                    </Box>
                )}
                MenuProps={MenuProps}
            >
                {choices.map((name) => (
                    <MenuItem
                        key={name}
                        value={name}
                        style={getDropDownStyles(name, currentChoices, theme)}
                    >
                        {name}
                    </MenuItem>
                ))}
            </Select>
        </FormControl>
    </>
}