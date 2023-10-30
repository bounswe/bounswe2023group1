import React, {useCallback, useState} from 'react';
import {Box, Card, CardContent, CardHeader, Divider, TextField} from '@mui/material';
import {createTheme, ThemeProvider} from '@mui/material/styles';


const states = [
    {
        value: 'istanbul',
        label: 'Istanbul',
    },
    {
        value: 'izmir',
        label: 'Izmir',
    },
    {
        value: 'van',
        label: 'Van',
    },
    {
        value: 'ankara',
        label: 'Ankara',
    },
];

const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

function AccountProfileDetails() {
    const [values, setValues] = useState({
        firstName: 'Sude',
        lastName: 'Demir',
        email: 'sude@gmail.com',
        phone: '05324054856',
        state: 'Izmir',
        country: 'Turkey',
    });

    const handleChange = useCallback((event) => {
        setValues((prevState) => ({
            ...prevState,
            [event.target.name]: event.target.value,
        }));
    }, []);

    const handleSubmit = useCallback((event) => {
        event.preventDefault();
    }, []);

    return (
        <ThemeProvider theme={customTheme}>
            <form autoComplete="off" noValidate onSubmit={handleSubmit}>
                <Card style={{width: '100%', maxWidth: '400px'}}>
                    <CardHeader/>
                    <CardContent sx={{pt: 0}}>
                        <Box sx={{m: -1.5}}>
                            <TextField
                                fullWidth
                                style={{marginBottom: '16px'}}
                                label="First name"
                                name="firstName"
                                onChange={handleChange}
                                required
                                value={values.firstName}
                            />
                            <TextField
                                fullWidth
                                style={{marginBottom: '16px'}}
                                label="Last name"
                                name="lastName"
                                onChange={handleChange}
                                required
                                value={values.lastName}
                            />
                            <TextField
                                fullWidth
                                style={{marginBottom: '16px'}}
                                label="Email Address"
                                name="email"
                                onChange={handleChange}
                                required
                                value={values.email}
                            />
                            <TextField
                                fullWidth
                                style={{marginBottom: '16px'}}
                                label="Phone Number"
                                name="phone"
                                onChange={handleChange}
                                type="number"
                                value={values.phone}
                            />
                            <TextField
                                fullWidth
                                style={{marginBottom: '16px'}}
                                label="Country"
                                name="country"
                                onChange={handleChange}
                                required
                                value={values.country}
                            />
                            <TextField
                                fullWidth
                                style={{marginBottom: '16px'}}
                                label="Select State"
                                name="state"
                                onChange={handleChange}
                                required
                                select
                                SelectProps={{native: true}}
                                value={values.state}
                            >
                                {states.map((option) => (
                                    <option key={option.value} value={option.value}>
                                        {option.label}
                                    </option>
                                ))}
                            </TextField>
                        </Box>
                    </CardContent>
                    <Divider/>
                </Card>
            </form>
        </ThemeProvider>
    );
}

export default AccountProfileDetails;