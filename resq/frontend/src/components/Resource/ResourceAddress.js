import React, { useState, useEffect } from 'react';
import { TextField, Button, FormControl, InputLabel, Select, MenuItem, Box } from '@mui/material';
import '@fontsource/inter';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Container from '@mui/material/Container';
import { ResourceContext } from './ResourceContext';
import { useContext } from 'react';

const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

export default function ResourceAddress({ resourceData, setResourceData }) {
    const [address1, setAddress1] = useState("")
    const [address2, setAddress2] = useState("")
    const [city, setCity] = useState("")
    const [state, setState] = useState("")
    const [country, setCountry] = useState("")
    const [nop, setNop] = useState("")


    const handleGeocode = async () => {
        const address = `${address1}, ${address2}, ${city}, ${state}, ${country}`;
        const apiKey = 'AIzaSyCehlfJwJ-V_xOWZ9JK3s0rcjkV2ga0DVg';

        try {
            const response = await fetch(
                `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(
                    address
                )}&key=${apiKey}`
            );

            if (response.ok) {
                const data = await response.json();
                if (data.results && data.results.length > 0) {
                    const location = data.results[0].geometry.location;
                    setResourceData(
                        { ...resourceData, latitude: location.lat, longitude: location.lng }
                    )
                } else {
                    console.error('Geocoding failed: No results found');
                }
            } else {
                console.error('Geocoding request failed');
            }
        } catch (error) {
            console.error('Geocoding error:', error);
        }
    };

    useEffect(() => {
        handleGeocode();
    }, [address1, address2, city, state, country, nop]);

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
                    <Typography
                        component="h1"
                        variant="h5"
                        sx={{ color: 'red', fontWeight: 'bold', margin: '0' }}
                    >
                        Resource Delivery Address
                    </Typography>
                    <React.Fragment>
                        <form>
                            <Grid container spacing={3}>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        id="address1"
                                        name="address1"
                                        label="Address line 1"
                                        fullWidth
                                        autoComplete="shipping address-line1"
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
                                        autoComplete="shipping address-line2"
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
                                        autoComplete="shipping address-level2"
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
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        required
                                        id="nameofplace"
                                        name="nameofplace"
                                        label="Name of the Delivery Place"
                                        fullWidth
                                        autoComplete="delivery place"
                                        variant="standard"
                                        value={nop}
                                        onChange={(e) => setNop(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormControlLabel
                                        control={
                                            <Checkbox
                                                color="error"
                                                name="saveAddress"
                                                value="yes"
                                            />
                                        }
                                        label="I have delivered my resources to the above mentioned address."
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
