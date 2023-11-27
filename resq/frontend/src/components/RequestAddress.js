import React, { useState } from 'react';
import { TextField, Button, FormControl, InputLabel, Select, MenuItem, Box } from '@mui/material';
import '@fontsource/inter';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import disasterImage from '../disaster.png';
import Avatar from '@mui/material/Avatar';
import CssBaseline from '@mui/material/CssBaseline';
import Container from '@mui/material/Container';


const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

export default function CreateRequestForm() {
    const [request, setRequest] = useState({
        type: '',
        status: '',
        urgency: '',
        location: '',
        quantity: '',
        description: '',
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setRequest(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log(request);
        //send this data to a backend server
    };

    return (
        <ThemeProvider theme={customTheme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 1,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >

                    <Typography component="h1" variant="h5" sx={{ color: 'red', fontWeight: 'bold', margin: '0' }}>
                        Create Request
                    </Typography>
                    <React.Fragment>
                        <Grid container spacing={3}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    id="firstName"
                                    name="firstName"
                                    label="First name"
                                    fullWidth
                                    autoComplete="given-name"
                                    variant="standard"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    id="lastName"
                                    name="lastName"
                                    label="Last name"
                                    fullWidth
                                    autoComplete="family-name"
                                    variant="standard"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    id="address1"
                                    name="address1"
                                    label="Address line 1"
                                    fullWidth
                                    autoComplete="address1"
                                    variant="standard"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    id="address2"
                                    name="address2"
                                    label="Address line 2"
                                    fullWidth
                                    autoComplete="address2"
                                    variant="standard"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    id="city"
                                    name="city"
                                    label="City"
                                    fullWidth
                                    autoComplete="city"
                                    variant="standard"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    id="state"
                                    name="state"
                                    label="State/Province/Region"
                                    fullWidth
                                    variant="standard"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    id="zip"
                                    name="zip"
                                    label="Zip / Postal code"
                                    fullWidth
                                    autoComplete="shipping postal-code"
                                    variant="standard"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    id="country"
                                    name="country"
                                    label="Country"
                                    fullWidth
                                    autoComplete="shipping country"
                                    variant="standard"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    control={<Checkbox color="error" name="saveAddress" value="yes" />}
                                    label="Use this address for my request"
                                />
                            </Grid>
                        </Grid>
                    </React.Fragment>
                </Box>
            </Container>
        </ThemeProvider>
    );
}

