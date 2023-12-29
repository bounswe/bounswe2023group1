import * as React from 'react';
import {useEffect, useState} from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import {getAddress} from "../components/Cards/ListCards";
import {useQuery} from "@tanstack/react-query";
import {
    addRequests,
    addResources,
    createAction, createTask,
    getAllResources,
    getAllUsers,
    getCategoryTree,
    viewAllNeeds,
    viewAllRequests
} from "../AppService";
import {DataGrid, GridToolbar} from "@mui/x-data-grid";
import Typography from "@mui/material/Typography";
import {Add} from "@mui/icons-material";
import {
    Autocomplete,
    Button,
    DialogActions,
    DialogContent,
    Fab, FormControl,
    InputLabel,
    MenuItem,
    Select,
    TextField
} from "@mui/material";
import DialogTitle from "@mui/material/DialogTitle";
import Dialog from "@mui/material/Dialog";


const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

const MyDataGrid = ({title, rows, columns, onSelectedChanged}) => {
    const rowsMap = new Map(rows.map(row => [row.id, row]))

    return <Box sx={{
        flexBasis: "33%",
        flexShrink: 0,
        height: "100%",
        overflow: "scroll",
        margin: "0.5rem"
    }}>
        <Typography component="h5" variant="h5" sx={{marginBottom: "1rem", textAlign: "center"}}>{title}</Typography>
        <DataGrid
            rows={rows}
            columns={columns}
            components={{Toolbar: GridToolbar}}
            sx={{
                '& .MuiDataGrid-toolbarContainer': {
                    justifyContent: 'flex-end',
                    align: 'right',
                    marginBottom: '16px',
                }
            }}
            initialState={{
                pagination: {
                    paginationModel: {page: 0, pageSize: 10},
                },
            }}
            pageSizeOptions={[5, 10, 15, 20, 25]}
            autoHeight
            checkboxSelection
            disableRowSelectionOnClick
            onRowSelectionModelChange={(selected) =>
                onSelectedChanged(selected.map(row => rowsMap.get(row)))}
        />
    </Box>
}

const ResourcesDataGrid = ({resources, ...props}) => {
    const [rows, setRows] = useState([])

    const categoryTree = useQuery({
        queryKey: ['categoryTree'],
        queryFn: () => getCategoryTree()
    })

    const columns = [
        {
            field: 'Type',
            headerName: <strong>{'Type'}</strong>,
            width: 100,
            align: 'left',
            flex: 1,
        },
        {
            field: 'Quantity',
            headerName: <strong>{'Quantity'}</strong>,
            type: 'number',
            width: 80,
            align: 'right',
            flex: 0.5,
        },
        {
            field: 'Location',
            headerName: <strong>{'Location'}</strong>,
            align: 'right',
            flex: 0.5,
        },
        {
            field: 'Status',
            headerName: <strong>{'Status'}</strong>,
            align: 'right',
            flex: 0.5,
        },
    ];

    useEffect(() => {
        (async () => setRows(
            await Promise.all(
                resources
                    .map(async ({id, quantity, categoryTreeId, longitude, latitude, status}) =>
                        ({
                            "id": id,
                            "Type": categoryTree?.data?.findCategoryWithId(parseInt(categoryTreeId))?.data || categoryTreeId,
                            "Quantity": quantity,
                            "Location": await getAddress(latitude, longitude),
                            "Status": status
                        })))))();
    }, [categoryTree?.data, resources])

    return <MyDataGrid title={"Resources"} columns={columns} rows={rows} {...props}/>
}

const amalgamateNeeds = needsArray => {
    const needsMap = new Map()
    for (const {categoryTreeId, quantity} of needsArray) {
        needsMap.set(categoryTreeId, quantity + (needsMap.get(categoryTreeId) || 0))
    }
    return [...needsMap.entries()].map(([categoryTreeId, quantity]) => ({categoryTreeId, quantity}))
}

const RequestsDataGrid = ({requests, ...props}) => {
    const [rows, setRows] = useState([])

    const categoryTree = useQuery({
        queryKey: ['categoryTree'],
        queryFn: () => getCategoryTree()
    })

    const columns = [
        {
            field: 'Type',
            headerName: <strong>{'Type'}</strong>,
            width: 100,
            align: 'left',
            flex: 1,
        },
        {
            field: 'Quantity',
            headerName: <strong>{'Quantity'}</strong>,
            type: 'number',
            width: 80,
            align: 'right',
            flex: 0.5,
        },
        {
            field: 'Location',
            headerName: <strong>{'Location'}</strong>,
            align: 'right',
            flex: 0.5,
        },
        {
            field: 'Status',
            headerName: <strong>{'Status'}</strong>,
            align: 'right',
            flex: 0.5,
        },
    ];

    const needs = useQuery({queryKey: ['viewAllNeeds'], queryFn: viewAllNeeds})

    useEffect(() => {
        if (!needs?.data?.data)
            return
        const calcRows = async () => {
            const allNeedsMap = new Map(needs?.data?.data.map(need => [need.id, need]))
            const reqPromises = requests.map(async ({id, needIds, longitude, latitude, status}) => {
                const location = await getAddress(latitude, longitude)
                return await Promise.all(
                    amalgamateNeeds(
                        needIds.map(need_id => allNeedsMap.get(need_id))
                    ).map(async ({categoryTreeId, quantity}) => ({
                        "id": `${id}_${categoryTreeId}`,
                        "Type": categoryTree?.data?.findCategoryWithId(parseInt(categoryTreeId))?.data || categoryTreeId,
                        "Quantity": quantity,
                        "Location": location,
                        "Status": status
                    })));
            })
            setRows((await Promise.all(reqPromises)).flat());
        }
        calcRows()
    }, [categoryTree?.data, requests, needs?.data])

    return <MyDataGrid title={"Requests"} columns={columns} rows={rows} {...props}/>
}

export default function CreateActionPage({uid}) {
    const [openDialog, setOpenDialog] = React.useState(false);
    const [desc, setDesc] = useState("")
    const [selectedHuman, setSelectedHuman] = useState(null)
    const [urgency, setUrgency] = useState("")

    const [selectedResources, setSelectedResources] = useState([])
    const [selectedRequests, setSelectedRequests] = useState([])
    const [mapCenter, setMapCenter] = useState([39, 34.5])

    const resources = useQuery({queryKey: ['getAllResources'], queryFn: getAllResources})
    const requests = useQuery({queryKey: ['viewAllRequests'], queryFn: viewAllRequests})
    const users = useQuery({queryKey: ['getAllUsers'], queryFn: getAllUsers})
    /*
        useEffect(() => {
            if (selectedPoint) {
                setMapCenter([selectedPoint.latitude, selectedPoint.longitude]);
            }
        }, [selectedResources]);
    */

    const paths = [...selectedResources, ...selectedRequests].map((item, i, arr) => {
        const next = arr[i + 1]
        if (!next)
            return null
        const startLatitude = item.latitude
        const startLongitude = item.longitude
        const endLatitude = next.latitude
        const endLongitude = next.longitude
        return {
            task: {startLatitude, startLongitude, endLatitude, endLongitude},
            coordinates: [[startLongitude, startLatitude], [endLongitude, endLatitude]],
            color: "#DD5555"
        };
    }).filter(a => a)

    let handleSelectedResourcesChanged = selected => {
        setSelectedResources(selected
            .map(({id: s_id}) => resources.data?.data?.filter(({id: r_id}) => s_id === r_id)[0])
            .map(res => ({...res, type: "Resource"}))
        )
    }
    let handleSelectedRequestsChanged = selected => {
        console.log(requests.data?.data)
        setSelectedRequests(selected
            .map(({id: s_id}) => requests.data?.data?.filter(({id: r_id}) => parseInt(s_id.split("_")[0]) === r_id)[0])
            .map(res => ({...res, type: "Request"}))
            .filter((item, index, arr) => arr.indexOf(item) === index)
        )
    };
    const handleCreateAction = async () => {
        const taskId = (await createTask({
            "assignerId": uid,
            "assigneeId": selectedHuman.id,
            "description": desc,
            "urgency": urgency
        })).data
        const pathPromises = paths.map(path =>
            createAction({
                taskId,
                verifierId: 1,
                description: "string",
                ...path.task,
                dueDate: "2023-12-26T00:37:53.881Z",
                completed: false,
                verified: true
            }))
        const resourcePromise = addResources({
            taskId,
            "receiverId": 4,
            "resourceIds": selectedResources.map(({id}) => id)
        })
        const requestPromise = addRequests({
            taskId,
            "requestIds": selectedRequests.map(({id}) => id)
        })
        await Promise.all([...pathPromises, resourcePromise, requestPromise])
        setOpenDialog(false)
    };

    const handleCloseDialog = () => {
        setOpenDialog(false)
    }
    return (
        <ThemeProvider theme={customTheme}>
            <Dialog fullWidth open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>Edit Annotation</DialogTitle>
                <DialogContent sx={{height: "30vh"}}>
                    <TextField
                        sx={{margin: "1rem 0"}}
                        autoFocus
                        margin="dense"
                        label="Task Description"
                        fullWidth
                        variant="standard"
                        value={desc}
                        onChange={(event) => {
                            setDesc(event.target.value);
                        }}
                    />
                    <Autocomplete
                        disablePortal
                        id="resource-category-combo-box"
                        options={users?.data
                            ?.filter(user => user?.roles?.indexOf("RESPONDER") > -1)
                            .map(({id, name, surname}) => ({id, label: `${name} ${surname}`}))}
                        value={selectedHuman}
                        onChange={(event, newValue) => {
                            setSelectedHuman(newValue);
                        }}
                        sx={{width: 300, margin: "1rem 0"}}
                        renderInput={(params) => (
                            <TextField {...params} label={"Responder"}/>
                        )}
                    />
                    <FormControl sx={{margin: "1rem 0", minWidth: 160}}>
                        <InputLabel id="alabel">Urgency</InputLabel>

                        <Select
                            labelId="alabel"
                            value={urgency}
                            label="Urgency"
                            onChange={event => setUrgency(event.target.value)}
                        >
                            <MenuItem value={"LOW"}>Low</MenuItem>
                            <MenuItem value={"MEDIUM"}>Medium</MenuItem>
                            <MenuItem value={"HIGH"}>High</MenuItem>
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCreateAction}>Create</Button>
                    <Button onClick={handleCloseDialog}>Cancel</Button>
                </DialogActions>
            </Dialog>
            <Container maxWidth="100%" style={{height: "100%", display: "flex", flexDirection: "column"}}>
                <CssBaseline/>
                <Box sx={{
                    display: "flex",
                    flexDirection: "row",
                    flexWrap: 'nowrap',
                    margin: "12px",
                    height: "100px",
                    flexGrow: 100
                }}>
                    <ResourcesDataGrid resources={resources.data?.data || []}
                                       onSelectedChanged={handleSelectedResourcesChanged}/>
                    <RequestsDataGrid requests={requests.data?.data || []}
                                      onSelectedChanged={handleSelectedRequestsChanged}/>


                    <Box sx={{width: "36px"}}/>
                    <Box sx={{flexGrow: 100}}>
                        <DisasterMap markers={[...selectedResources, ...selectedRequests]}
                                     mapCenter={mapCenter}
                                     setMapCenter={setMapCenter}
                                     paths={paths}
                        />
                    </Box>
                </Box>
            </Container>
            <Fab color="primary" variant="extended" onClick={() => setOpenDialog(true)} sx={{
                position: 'absolute',
                bottom: 32,
                right: 32,
            }}>
                <Add sx={{mr: 1}}/>
                Create Action
            </Fab>
        </ThemeProvider>
    );
}



