import React, {useState, useEffect} from 'react';
import {TextField, Box} from '@mui/material';
import '@fontsource/inter';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Container from '@mui/material/Container';
import axios from "axios";


const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

export default function CreateRequestForm({ needData, setNeedData }) {
    const [address1, setAddress1] = useState("")
    const [address2, setAddress2] = useState("")
    const [city, setCity] = useState("")
    const [state, setState] = useState("")
    const [country, setCountry] = useState("")
    const [zip, setZip] = useState("")
    const [fname, setFname] = useState("")
    const [lname, setLname] = useState("")

    useEffect(() => {
        const handleGeocode = async () => {
            const address = `${address1}, ${address2}, ${city}, ${state}, ${country}`;
            const apiKey = 'AIzaSyAQxkir-6QWOzrdH3MflAd8h_q3G8v2Uqs';

            try {
                const response = await axios.get(
                    `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(
                        address
                    )}&key=${apiKey}`,
                    {
                        headers: {
                            Authorization: null,
                        },
                    }
                );

                const data = response?.data;
                if (data.results && data.results.length > 0) {
                    const location = data.results[0].geometry.location;
                    setNeedData(
                        {...needData, latitude: location.lat, longitude: location.lng}
                    )
                } else {
                    console.error('Geocoding failed: No results found');
                }
            } catch (error) {
                console.error('Geocoding error:', error);
            }
        };
        handleGeocode();
    }, [address1, address2, city, state, country, zip, fname, lname, needData, setNeedData]);

    return (
        <ThemeProvider theme={customTheme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Box
                    sx={{
                        marginTop: 1,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >

                    <Typography component="h1" variant="h5" sx={{color: 'red', fontWeight: 'bold', margin: '0'}}>
                        Create Request
                    </Typography>
                    <React.Fragment>
                        <form>
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
                                        value={fname}
                                        onChange={(e) => setFname(e.target.value)}
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
                                        value={lname}
                                        onChange={(e) => setLname(e.target.value)}
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
                                        value={address1}
                                        onChange={(e) => setAddress1(e.target.value)}
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
                                        value={address2}
                                        onChange={(e) => setAddress2(e.target.value)}
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
                                        value={city}
                                        onChange={(e) => setCity(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        id="state"
                                        name="state"
                                        label="State/Province/Region"
                                        fullWidth
                                        variant="standard"
                                        value={state}
                                        onChange={(e) => setState(e.target.value)}
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
                                        value={zip}
                                        onChange={(e) => setZip(e.target.value)}
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
                                        value={country}
                                        onChange={(e) => setCountry(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormControlLabel
                                        control={<Checkbox color="error" name="saveAddress" value="yes"/>}
                                        label="Use this address for my request"
                                    />
                                </Grid>
                            </Grid>
                        </form>
                    </React.Fragment>
                </Box>
            </Container>
        </ThemeProvider>
    );
}

