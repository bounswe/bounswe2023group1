import * as React from 'react';
import {useEffect, useState} from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import {cards} from "../components/Cards/ListCards";
import {AmountSelector, MultiCheckbox} from "../components/MultiCheckbox";
import {DatePicker} from "@mui/x-date-pickers";
import dayjs from "dayjs";
import {useQuery} from "@tanstack/react-query";
import {getCategoryTree} from "../AppService";
import Annotatable from "../components/Annotatable";
import {DataGrid, GridToolbar} from "@mui/x-data-grid";
import {Location} from "./Location";


const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

const MyDataGrid = ({rows, columns, onSelectedChanged}) => {
    return <Box sx={{
        flexBasis: "33%",
        flexShrink: 0,
        height: "100%",
        overflow: "scroll"
    }}>
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
            onRowSelectionModelChange={onSelectedChanged}
        />
    </Box>
}

const ResourcesDataGrid = ({resources, ...props}) => {


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
            align: 'left',
            flex: 0.5,
        },
        {
            field: 'Location',
            headerName: <strong>{'Location'}</strong>,
            align: 'left',
            flex: 0.5,
        },
    ];

    const rows = resources.map(({quantity, categoryTreeId, longitude, latitude}) => ({
        "Type": categoryTree?.data?.findCategoryWithId(parseInt(categoryTreeId))?.data || categoryTreeId,
        "Quantity": quantity,
        "Location": <Location latitude={latitude} longitude={longitude}/>
    }))

    return <MyDataGrid columns={columns} rows={rows}/>
}

export default function CreateActionPage() {
    const [shownMarkers, setShownMarkers] = useState([])
    const [selectedPoint, setSelectedPoint] = useState(null)
    const [mapCenter, setMapCenter] = useState([39, 34.5])
    const [mapBounds, setMapBounds] = useState({ne: [0, 0], sw: [0, 0]})

    const categoryTree = useQuery({queryKey: ['categoryTree'], queryFn: getCategoryTree})

    useEffect(() => {
        if (selectedPoint) {
            setMapCenter([selectedPoint.latitude, selectedPoint.longitude]);
        }
    }, [selectedPoint]);

    return (
        <ThemeProvider theme={customTheme}>
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
                    <Box sx={{
                        flexBasis: "33%",
                        flexShrink: 0,
                        height: "100%",
                        overflow: "scroll"
                    }}>
                        <Box sx={{
                            display: "flex",
                            flexDirection: "column",
                            rowGap: "16px",
                            height: "fit-content"
                        }}>
                            {shownMarkers.map((marker) => {
                                const SelectedCard = cards[marker.type]
                                return <div onClick={() => setSelectedPoint(marker)}>< SelectedCard item={marker}/>
                                </div>
                            })}
                        </Box>
                    </Box>
                    <Box sx={{width: "36px"}}/>
                    <Box sx={{flexGrow: 100}}>
                        <DisasterMap markers={shownMarkers}
                                     mapCenter={mapCenter}
                                     setMapCenter={setMapCenter}
                                     onPointSelected={setSelectedPoint}
                                     onBoundsChanged={setMapBounds}
                        />
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}



