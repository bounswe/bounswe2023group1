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
import {Copyright} from "../components/Copyright";
import DisasterMap from "../components/DisasterMap";
import {useState} from "react";


const defaultTheme = createTheme();


export default function SignIn() {

    const navigate = useNavigate();
    const [selectedPoint, setSelectedPoint] = useState(null)

    return (
        <ThemeProvider theme={defaultTheme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <DisasterMap onPointSelected={setSelectedPoint}/>
                <Copyright sx={{mt: 8, mb: 4}}/>
            </Container>
        </ThemeProvider>
    );
}


