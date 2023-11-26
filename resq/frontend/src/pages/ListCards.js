import * as React from "react";
import {useEffect, useState} from "react";
import axios from "axios";
import {Card, CardActions, CardContent, CardHeader, Collapse, IconButton} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import {type_colors} from "../Colors";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import styled from "styled-components";
import {AnnotationIcon} from "../components/MapIcons";

const ExpandMore = styled(IconButton)`
  transform: ${({expand}) => !expand ? 'rotate(0deg)' : 'rotate(180deg)'};
  margin-left: auto;
  transition: transform 150ms;
`
const OffsetActions = styled(CardActions)`
  transform: translate(-8px, -28px);
  height: 0;
  padding: 0;
  flex-direction: row-reverse;
`

async function getAddress(latitude, longitude) {
    try {
        const response = await axios.get(
            `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=AIzaSyCehlfJwJ-V_xOWZ9JK3s0rcjkV2ga0DVg`
        );

        return response.data.results[0]?.formatted_address || 'Unknown';
    } catch (error) {
        console.error('Error fetching location name:', error);
    }
}


export const AnnotationCard = ({item: {title, short_description, long_description, latitude, longitude, icon}}) => {
    const [expanded, setExpanded] = useState(false);
    const [locationName, setLocationName] = useState('');

    useEffect(() => {
        (async () => setLocationName(await getAddress(latitude, longitude)))();
    }, [latitude, longitude]);

    return <Card variant="outlined">
        <CardHeader
            avatar={
                <Avatar sx={{bgcolor: type_colors["Request"]}} aria-label={icon}>
                    <AnnotationIcon icon={icon} color={"white"}/>
                </Avatar>
            }
            titleTypographyProps={{variant: 'h6'}}
            title={title}
        />
        <CardContent>
            <Typography variant="body1" sx={{fontSize: '16px', fontWeight: 'bold'}}>
                {short_description}
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Location: {`${locationName}`}
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
                {long_description}
            </CardContent>
        </Collapse>
    </Card>;
}

export const RequestCard = ({item: {requester, urgency, needs, status, longitude, latitude}}) => {
    const [expanded, setExpanded] = useState(false);
    const [locationName, setLocationName] = useState('');

    useEffect(() => {
        (async () => setLocationName(await getAddress(latitude, longitude)))();
    }, [latitude, longitude]);

    return <Card variant="outlined">
        <CardHeader
            avatar={
                <Avatar sx={{bgcolor: type_colors["Request"]}} aria-label="Request">
                    Rq
                </Avatar>
            }
            titleTypographyProps={{variant: 'h6'}}
            title={needs.map(({name, quantity}) => `${quantity} ${name}`).join(", ")}
        />
        <CardContent>
            <Typography variant="body1" sx={{fontSize: '16px', fontWeight: 'bold'}}>
                Urgency: <span style={{color: 'red', fontWeight: 'bold'}}>{urgency}</span> | Status: <span
                style={{color: 'red', fontWeight: 'bold'}}>{status}</span>
            </Typography>

            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Made by: {requester.name} {requester.surname}
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Location: {`${locationName}`}
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
                {needs.map(({name, description, quantity}) =>
                    <Typography variant="body2" color="text.primary">
                        {quantity} {name}: {description}
                    </Typography>
                )}
            </CardContent>
        </Collapse>
    </Card>;
}


export const ResourceCard = ({item: {owner, urgency, resources, status, longitude, latitude}}) => {
    const [expanded, setExpanded] = useState(false);
    const [locationName, setLocationName] = useState('');

    useEffect(() => {
        (async () => setLocationName(await getAddress(latitude, longitude)))();
    }, [latitude, longitude]);


    return <Card variant="outlined">
        <CardHeader
            avatar={
                <Avatar sx={{bgcolor: type_colors["Resource"]}} aria-label="Resource">
                    Rs
                </Avatar>
            }
            titleTypographyProps={{variant: 'h6'}}
            title={resources.map(({name, quantity}) => `${quantity} ${name}`).join(", ")}
        />
        <CardContent>
            <Typography variant="body1" sx={{fontSize: '16px', fontWeight: 'bold'}}>
                Urgency: <span style={{color: 'red', fontWeight: 'bold'}}>{urgency}</span> | Status: <span
                style={{color: 'blue', fontWeight: 'bold'}}>{status}</span>
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Owner: {owner.name} {owner.surname}
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Location: {`${locationName}`}
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
                {resources.map(({name, description, quantity}) =>
                    <Typography variant="body2" color="text.primary">
                        {quantity} {name}: {description}
                    </Typography>
                )}
            </CardContent>
        </Collapse>
    </Card>;
}
export const cards = {
    "Resource": ResourceCard,
    "Request": RequestCard,
    "Annotation": AnnotationCard
}