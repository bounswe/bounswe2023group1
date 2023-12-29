import * as React from 'react';
import {useEffect, useState} from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import {useQuery} from "@tanstack/react-query";
import {viewAllTasks} from "../AppService";
import Annotatable from "../components/Annotatable";
import {distinct_colors} from "../Colors";
import {TaskCard} from "../components/Cards/TaskCard";

const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});



const taskRanks = {
    IN_PROGRESS: 0,
    TODO: 1,
    PENDING: 2,
    DONE: 3,
}

export default function TaskSelectPage({uid}) {
    //const [allTasks, setAllTasks] = useState(mockTasks)
    const [sortedTasks, setSortedTasks] = useState([])

    const [shownMarkers, setShownMarkers] = useState([])
    const [shownPaths, setShownPaths] = useState([])
    const [selectedPoint, setSelectedPoint] = useState(null)
    const [mapCenter, setMapCenter] = useState([39, 34.5])

    const allTasks = useQuery({queryKey: ['getTasks'], queryFn: () => viewAllTasks(uid)})

    useEffect(() => {
        if (allTasks?.data)
            setSortedTasks(allTasks.data.sort((a, b) => taskRanks[a.status] - taskRanks[b.status]))
    }, [allTasks]);

    const avgCoords = (c, i) => (c[i][0] + c[i][1]) / 2

    useEffect(() => {
        if (selectedPoint) {
            setMapCenter([
                selectedPoint.longitude || avgCoords(selectedPoint.coordinates, 0),
                selectedPoint.latitude || avgCoords(selectedPoint.coordinates, 1)
            ]);
        }
    }, [selectedPoint]);

    const makePaths = task => task.actions.map(action => ({
        task,
        coordinates: [[action.startLongitude, action.startLatitude], [action.endLongitude, action.endLatitude]],
        color: distinct_colors[task.id % 30]
    }))
    const makeMarkers = task => task.resources.map(resource => ({
        task,
        color: distinct_colors[task.id % 30], ...resource
    }))

    useEffect(() => {
        if (selectedPoint) {
            setShownPaths(makePaths(selectedPoint.task))
            setShownMarkers(makeMarkers(selectedPoint.task))
        } else {
            setShownPaths(sortedTasks.map(task => makePaths(task)).flat(1))
            setShownMarkers(sortedTasks.map(task => makeMarkers(task)).flat(1))
        }
    }, [sortedTasks, selectedPoint]);

    const handleTaskCardClick = task => {
        setSelectedPoint(makePaths(task)[0]);
    }

    // noinspection JSValidateTypes
    return (
        <Annotatable style={{width: "100%", height: "100%"}}>
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
                                {sortedTasks.map((task) => <TaskCard item={task}
                                                                     expand={selectedPoint?.task?.id === task?.id}
                                                                     onClick={() => handleTaskCardClick(task)}/>)}
                            </Box>
                        </Box>
                        <Box sx={{width: "36px"}}/>
                        <Box sx={{flexGrow: 100}}>
                            <DisasterMap markers={shownMarkers}
                                         mapCenter={mapCenter}
                                         setMapCenter={setMapCenter}
                                         onPointSelected={setSelectedPoint}
                                         paths={shownPaths}
                            />
                        </Box>
                    </Box>
                </Container>
            </ThemeProvider>
        </Annotatable>
    );
}



