import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import CssBaseline from '@mui/material/CssBaseline';
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import {useState} from "react";
import {Card, CardActions, CardContent, CardHeader, Collapse, IconButton} from "@mui/material";
import {type_colors} from "../Colors";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import styled from "styled-components";


const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

function Copyright(props) {
    return (
        <div style={{position: 'fixed', bottom: 0, width: '100%'}}>
            <Typography variant="body2" color="text.secondary" align="center" {...props}>
                {'Copyright © '}
                <Link color="inherit" href="https://github.com/bounswe/bounswe2023group1">
                    <span style={{fontWeight: 'bold'}}>ResQ</span>
                </Link>{' '}
                {new Date().getFullYear()}
                {'.'}
            </Typography>
        </div>
    );
}


const ExpandMore = styled(IconButton)`
  transform: ${({expand}) => !expand ? 'rotate(0deg)' : 'rotate(180deg)'};
  margin-left: auto;
  transition: transform 150ms;
`

const OffsetActions = styled(CardActions)`
  transform: translate(-8px, -28px);
  height: 0;
  padding: 0;
`

const RequestCard = ({request: {requester, urgency, needs, status}}) => {
    const [expanded, setExpanded] = useState(false);

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



const ResourceCard = ({request: {owner, urgency, resources, status}}) => {
    const [expanded, setExpanded] = useState(false);

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
            <Typography variant="body1" sx={{ fontSize: '16px', fontWeight: 'bold' }}>
                Urgency: <span style={{ color: 'red', fontWeight: 'bold' }}>{urgency}</span> | Status: <span style={{ color: 'blue' , fontWeight: 'bold'}}>{status}</span>
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{ fontSize: '12px', fontWeight: 'bold' }}>
                Owner: {owner.name} {owner.surname}
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

const cards = {
    "Resource": ResourceCard,
    "Request": RequestCard
}

const mock_markers = [
    {
        type: "Request",
        latitude: 37.08,
        longitude: 31.05,
        requester: {
            name: "Müslüm",
            surname: "Ertürk"
        },
        urgency: "HIGH",
        needs: [
            {
                name: "Canned food",
                description: "Preferably a variety of different foodstuffs.",
                quantity: 3
            },
            {
                name: "Diapers",
                description: "Preferably individually packed.",
                quantity: 2
            }
        ],
        status: "TODO"
    },
    {
        type: "Request",
        latitude: 41.1,
        longitude: 29.15,
        requester: {
            name: "Ali",
            surname: "Er"
        },
        urgency: "LOW",
        needs: [
            {
                name: "Power banks",
                description: "Power banks for the staff, preferably with cables included.",
                quantity: 30
            },
        ],
        status: "IN_PROGRESS"
    },
    {
        type: "Resource",
        latitude: 41.1,
        longitude: 29.04,
        owner: {
            name: "Te",
            surname: "St"
        },
        urgency: "HIGH",
        resources: [
            {
                name: "Bottled Water",
                description: "1.5 liters bottles",
                quantity: 300,
            },
        ],
        status: "READY"
    },
]


export default function MapDemo() {

    const [selectedPoint, setSelectedPoint] = useState(null)
    const SelectedCard = selectedPoint && cards[selectedPoint.type]
    // noinspection JSValidateTypes
    return (
        <ThemeProvider theme={customTheme}>
            <Container component="main" maxWidth="100%">
                <CssBaseline/>
                <Box sx={{display: "flex", flexDirection: "row", flexWrap: 'nowrap', margin: "12px"}}>
                    {selectedPoint && <>
                        <Box sx={{flexBasis: "33%", flexShrink: 0}}>
                            <SelectedCard request={selectedPoint}/>
                        </Box>
                        <Box sx={{width: "36px"}}/>
                    </>
                    }
                    <Box sx={{flexBasis: "66%", flexGrow: 100}}>
                        <DisasterMap markers={mock_markers} onPointSelected={setSelectedPoint}/>
                    </Box>
                </Box>

                <Copyright sx={{mt: 5}}/>
            </Container>
        </ThemeProvider>
    );
}


