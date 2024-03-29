import * as React from "react";
import {useEffect, useState} from "react";
import axios from "axios";
import {Card, CardActions, CardContent, CardHeader, Collapse, IconButton} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import {type_colors} from "../../Colors";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import styled from "styled-components";
import {useQuery} from "@tanstack/react-query";
import {getCategoryTree, getUserInfo} from "../../AppService";
import AnnotationCard from "./AnnotationCard";

export const ExpandMore = styled(IconButton)`
  transform: ${({expand}) => !expand ? 'rotate(0deg)' : 'rotate(180deg)'};
  margin-left: auto;
  transition: transform 150ms;
`
export const OffsetActions = styled(CardActions)`
  transform: translate(-8px, -28px);
  height: 0;
  padding: 0;
  flex-direction: row-reverse;
`

export async function getAddress(latitude, longitude) {
    try {
        const response = await axios.get(
            `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=AIzaSyAQxkir-6QWOzrdH3MflAd8h_q3G8v2Uqs`,
            {
                headers: {
                    Authorization: null,
                },
            }
        );

        return response.data.results[0]?.formatted_address || 'Unknown';
    } catch (error) {
        console.error('Error fetching location name:', error);
    }
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


export const ResourceCard = ({item: {type, id, senderId, quantity, categoryTreeId, longitude, latitude}}) => {
    const categoryTree = useQuery({
        queryKey: ['categoryTree'],
        queryFn: () => getCategoryTree()
    })
    const owner = useQuery({queryKey: ['user', senderId], queryFn: () => getUserInfo(senderId)})

    const [locationName, setLocationName] = useState('');

    useEffect(() => {
        (async () => setLocationName(await getAddress(latitude, longitude)))();
    }, [latitude, longitude]);


    return <Card variant="outlined" key={type+id} id={type+id} className={"anno-root"}>
        <CardHeader
            avatar={
                <Avatar sx={{bgcolor: type_colors["Resource"]}} aria-label="Resource">
                    Rs
                </Avatar>
            }
            titleTypographyProps={{variant: 'h6'}}
            title={`${quantity} ${categoryTree?.data?.findCategoryWithId(parseInt(categoryTreeId))?.data || categoryTreeId}`}
        />
        <CardContent>
            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Owner: {owner?.data?.name} {owner?.data?.surname}
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{fontSize: '12px', fontWeight: 'bold'}}>
                Location: {`${locationName}`}
            </Typography>
        </CardContent>
    </Card>;
}
export const cards = {
    "Resource": ResourceCard,
    "Request": RequestCard,
    "Facility": AnnotationCard
}