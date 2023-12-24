import * as React from 'react';
import {useEffect, useState} from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import {ExpandMore, getAddress, OffsetActions} from "../components/Cards/ListCards";
import {useQuery, useQueryClient} from "@tanstack/react-query";
import {acceptTask, completeTask, getCategoryTree, getUserInfo, viewAllTasks} from "../AppService";
import Annotatable from "../components/Annotatable";
import {Button, Card, CardContent, CardHeader, Collapse} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import {distinct_colors} from "../Colors";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

const mockTasks = [
    {
        "id": 4,
        "assignee": 9,
        "assigner": 9,
        "actions": [
            {
                "id": 4,
                "taskId": 4,
                "verifierId": 9,
                "description": "action description",
                "startLatitude": 10.5,
                "startLongitude": 38,
                "endLatitude": 39,
                "endLongitude": 39.5,
                "dueDate": null,
                "createdDate": null,
                "completed": false
            }
        ],
        "description": "task desc",
        "resources": [],
        "feedbacks": [],
        "urgency": "LOW",
        "status": "TODO"
    },
    {
        "id": 5,
        "assignee": 9,
        "assigner": 9,
        "actions": [
            {
                "id": 5,
                "taskId": 5,
                "verifierId": 9,
                "description": "action description",
                "startLatitude": 40,
                "startLongitude": 38,
                "endLatitude": 40.5,
                "endLongitude": 38.5,
                "dueDate": null,
                "createdDate": null,
                "completed": false
            }
        ],
        "description": "task desc",
        "resources": [{
            "id": 22,
            "senderId": 9,
            "receiverId": null,
            "categoryTreeId": "71",
            "gender": null,
            "quantity": 1300,
            "latitude": 40,
            "longitude": 38,
            "createdDate": "2023-11-28T12:58:51.181743",
            "size": null
        }],
        "feedbacks": [],
        "urgency": "LOW",
        "status": "PENDING"
    },
    {
        "id": 7,
        "assignee": 9,
        "assigner": 9,
        "actions": [
            {
                "id": 4,
                "taskId": 4,
                "verifierId": 9,
                "description": "action description",
                "startLatitude": 35.5,
                "startLongitude": 37,
                "endLatitude": 39,
                "endLongitude": 39.5,
                "dueDate": null,
                "createdDate": null,
                "completed": false
            }
        ],
        "description": "task desc",
        "resources": [],
        "feedbacks": [],
        "urgency": "LOW",
        "status": "TODO"
    },
]

const Location = ({latitude, longitude}) => {
    const location = useQuery({
        queryKey: ['location', latitude, longitude],
        queryFn: () => getAddress(latitude, longitude)
    })
    return <>{location?.data || "Loading"}</>
}

const TaskCard = ({
                      item: {
                          id,
                          //assignee: assigneeId,
                          assigner: assignerId,
                          actions: rawActions,
                          description,
                          resources,
                          urgency,
                          status
                      },
                      expand,
                      onClick
                  }) => {
    const [expanded, setExpanded] = useState(expand || false);

    useEffect(() => {
        if (expand)
            setExpanded(true)
    }, [expand]);

    const queryClient = useQueryClient()

    const assigner = useQuery({queryKey: ['user', assignerId], queryFn: () => getUserInfo(assignerId)})

    const categoryTree = useQuery({
        queryKey: ['categoryTree'],
        queryFn: () => getCategoryTree()
    })

    const actions = rawActions.map(action => ({
        startLocation: <Location latitude={action.startLatitude} longitude={action.startLongitude}/>,
        endLocation: <Location latitude={action.endLatitude} longitude={action.endLongitude}/>,
        ...action
    }))

    const handleSetTodo = async () => {
        acceptTask(id)
        await queryClient.invalidateQueries({queryKey: ['getTasks']})
    }
    const handleSetDone = async () => {
        completeTask(id)
        await queryClient.invalidateQueries({queryKey: ['getTasks']})
    }

    const StateButtons = (props) => ({
        PENDING: <Button {...props} onClick={handleSetTodo}>Accept Task</Button>,
        TODO: <Button {...props} onClick={handleSetDone}>Task Complete</Button>,
        DONE: <></>,
    })[status]

    return <Card variant="outlined"
                 style={{backgroundColor: status === "TODO" ? "#f1f1f1" : "#FFF"}}
                 className={"anno-root"}
                 id={"Task" + id}
                 onClick={onClick}>
        <CardHeader
            avatar={
                <Avatar sx={{bgcolor: distinct_colors[id % 30]}} aria-label="Task">
                    T
                </Avatar>
            }
            titleTypographyProps={{variant: 'h6'}}
            title={description}
        />
        <CardContent>
            <Typography variant="body1" sx={{fontSize: '16px', fontWeight: 'bold'}}>
                Urgency: <span style={{
                color: 'red', fontWeight: 'bold',
                textTransform: "capitalize"
            }}>
                    {urgency.toLowerCase()}
                </span> | Status: <span style={{
                color: 'red',
                fontWeight: 'bold',
                textTransform: "capitalize"
            }}>
                    {status === "TODO" ? "Accepted" : status.toLowerCase()}
                </span>
            </Typography>

            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Assigned by: {assigner?.data?.name} {assigner?.data?.surname}
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Locations: {actions.map(({
                                             startLocation,
                                             endLocation
                                         }) => ([startLocation, <>, </>, endLocation, <>, </>])).flat()}
            </Typography>
        </CardContent>
        <OffsetActions disableSpacing>
            {/*<IconButton aria-label="add to favorites">
                <FavoriteIcon/>
            </IconButton>
            <IconButton aria-label="share">
                <ShareIcon/>
            </IconButton>*/}
            <ExpandMore
                expand={expanded}
                onClick={() => setExpanded(!expanded)}
                aria-expanded={expanded}
                aria-label="show more"
            >
                <ExpandMoreIcon/>
            </ExpandMore>
        </OffsetActions>
        <Collapse in={expanded} timeout="auto" unmountOnExit>
            <CardContent>
                {actions.map(({description, startLocation, endLocation}) =>
                    <Typography variant="body2" color="text.primary">
                        From {startLocation} to {endLocation}: {description}
                    </Typography>
                )}
                {resources.map(({quantity, categoryTreeId, longitude, latitude}) =>
                    <Typography variant="body2" color="text.primary">
                        {quantity} {categoryTree?.data?.findCategoryWithId(parseInt(categoryTreeId))?.data || categoryTreeId}
                        at <Location latitude={latitude} longitude={longitude}/>
                    </Typography>
                )}
                <Box sx={{display: "flex", flexDirection: "row"}}>
                    <StateButtons sx={{marginLeft: "auto", marginRight: "auto", marginTop: "1rem"}}
                                  variant="contained"/>
                </Box>
            </CardContent>
        </Collapse>
    </Card>;
}

const taskRanks = {
    IN_PROGRESS: 0,
    TODO: 1,
    PENDING: 2,
    DONE: 3,
}

export default function TaskSelectPage({uid}) {
    //const [allTasks, setAllTasks] = useState(mockTasks)
    const [sortedTasks, setSortedTasks] = useState(mockTasks)

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



