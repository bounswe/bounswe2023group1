import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import disasterImage from '../disaster.png';
import Container from '@mui/material/Container';
import {useNavigate} from 'react-router-dom';
import DisasterMap from "../components/DisasterMap";
import { useState, useEffect } from 'react';
import {Card, CardActions, CardContent, CardHeader, Collapse, IconButton} from "@mui/material";
import {type_colors} from "../Colors";
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import styled from "styled-components";
import axios from 'axios';
import Geocode from 'react-geocode';

const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

function Copyright(props) {
    return (
      <div style={{ position: 'fixed', bottom: 0, width: '100%' }}>
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
          {'Copyright © '}
          <Link color="inherit" href="https://github.com/bounswe/bounswe2023group1">
            <span style={{ fontWeight: 'bold' }}>ResQ</span>
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

const RequestCard = ({request: {requester, urgency, needs, status, longitude, latitude }}) => {
    const [expanded, setExpanded] = useState(false);
    const [locationName, setLocationName] = useState('');
    const [cityName, setCityName] = useState('');

    useEffect(() => {
        const reverseGeocode = async () => {
        try {
            const response = await axios.get(
            `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=AIzaSyCehlfJwJ-V_xOWZ9JK3s0rcjkV2ga0DVg`
            );

            const cityName = response.data.results[0]?.formatted_address || 'Unknown';
            setLocationName(cityName);
            setCityName(cityName);
        } catch (error) {
            console.error('Error fetching location name:', error);
        }
        };

        reverseGeocode();
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
            <Typography variant="body1" sx={{ fontSize: '16px', fontWeight: 'bold' }}>
                Urgency: <span style={{ color: 'red', fontWeight: 'bold' }}>{urgency}</span> | Status: <span style={{ color: 'red' , fontWeight: 'bold'}}>{status}</span>
            </Typography>

            <Typography variant="body2" color="text.primary" sx={{ fontSize: '12px', fontWeight: 'bold' }}>
                Made by: {requester.name} {requester.surname}
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{ fontSize: '12px', fontWeight: 'bold' }}>
                Location: {`(${cityName})`}
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


const ResourceCard = ({request: {owner, urgency, resources, status, longitude, latitude}}) => {
    const [expanded, setExpanded] = useState(false);
    const [locationName, setLocationName] = useState('');
    const [cityName, setCityName] = useState('');

    useEffect(() => {
        const reverseGeocode = async () => {
        try {
            const response = await axios.get(
            `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=AIzaSyCehlfJwJ-V_xOWZ9JK3s0rcjkV2ga0DVg`
            );

            const cityName = response.data.results[0]?.formatted_address || 'Unknown';
            setLocationName(cityName);
            setCityName(cityName);
        } catch (error) {
            console.error('Error fetching location name:', error);
        }
        };

        reverseGeocode();
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
            <Typography variant="body1" sx={{ fontSize: '16px', fontWeight: 'bold' }}>
                Urgency: <span style={{ color: 'red', fontWeight: 'bold' }}>{urgency}</span> | Status: <span style={{ color: 'blue' , fontWeight: 'bold'}}>{status}</span>
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{ fontSize: '12px', fontWeight: 'bold' }}>
                Owner: {owner.name} {owner.surname}
            </Typography>
            <Typography variant="body2" color="text.primary" sx={{ fontSize: '12px', fontWeight: 'bold' }}>
                Location: {`(${cityName})`}
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

    const navigate = useNavigate();
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

                <Copyright sx={{ mt: 5 }} />
            </Container>
        </ThemeProvider>
    );
}




