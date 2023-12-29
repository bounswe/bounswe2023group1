import * as React from "react";
import {useEffect, useState} from "react";
import {useQuery, useQueryClient} from "@tanstack/react-query";
import {acceptTask, completeAction, completeTask, getCategoryTree, getUserInfo} from "../../AppService";
import {Button, Card, CardContent, CardHeader, Collapse, FormGroup} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import {distinct_colors} from "../../Colors";
import Typography from "@mui/material/Typography";
import {ExpandMore, getAddress, OffsetActions} from "./ListCards";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Box from "@mui/material/Box";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";

const Location = ({latitude, longitude}) => {
    const location = useQuery({
        queryKey: ['location', latitude, longitude],
        queryFn: () => getAddress(latitude, longitude)
    })
    return <>{location?.data || "Loading"}</>
}

const Action = ({
                    action: {
                        id,
                        description,
                        completed,
                        endLongitude,
                        endLatitude,
                        startLongitude,
                        startLatitude
                    }
                }) => {
    const [checked, setChecked] = useState(completed)
    const queryClient = useQueryClient()

    const handleChange = async (event) => {
        if (!checked) {
            setChecked(event.target.checked);
            completeAction(id).catch(() => {
            })
            await queryClient.invalidateQueries({queryKey: ['getTasks']})
        }
    };

    return <FormControlLabel
        control={<Checkbox checked={checked}
                           onChange={handleChange}/>}
        label={<>From <Location latitude={startLatitude} longitude={startLongitude}/> to <Location
            latitude={endLatitude} longitude={endLongitude}/>: {description}</>}/>
}

export const TaskCard = ({
                             item: {
                                 id,
                                 //assignee: assigneeId,
                                 assigner: assignerId,
                                 actions,
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

    const handleSetTodo = async () => {
        acceptTask(id).catch(() => {
        })
        await queryClient.invalidateQueries({queryKey: ['getTasks']})
    }
    const handleSetDone = async () => {
        completeTask(id).catch(() => {
        })
        await queryClient.invalidateQueries({queryKey: ['getTasks']})
    }

    const StateButtons = (props) => ({
        PENDING: <Button {...props} onClick={handleSetTodo}>Accept Task</Button>,
        TODO: actions.every(({completed}) => completed) &&
            <Button {...props} onClick={handleSetDone}>Task Complete</Button>,
        DONE: <></>,
    })[status]

    const statusColors = {
        "TODO": "#f1f1f1",
        "PENDING": "#FFF",
        "DONE": "#f3fff3"
    }

    return <Card variant="outlined"
                 style={{backgroundColor: statusColors[status]}}
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
                Locations:
                {actions.map(({endLatitude, endLongitude, startLatitude, startLongitude}) =>
                    <>
                        <Location latitude={startLatitude} longitude={startLongitude}/>,
                        <Location latitude={endLatitude} longitude={endLongitude}/>,
                    </>)}
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
                <FormGroup>
                    {actions.map(action => <Action action={action}/>)}
                </FormGroup>
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