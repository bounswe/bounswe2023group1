import React, { useCallback, useState, useEffect } from 'react';
import { Box, Card, CardContent, CardHeader, Divider, TextField } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import UnstyledSelectRichOptions from './Countries';


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

const AccountProfileDetails = ({ userInfo, userProfileData, setUserProfileData }) => {
    const [values, setValues] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        state: '',
        country: '',
    });

    useEffect(() => {
        if (userInfo) {
            setValues({
                firstName: userInfo.name || '',
                lastName: userInfo.surname || '',
                email: userInfo.email || '',
                phone: userInfo.phone || '',
                state: userInfo.state || '',
                country: userInfo.country || '',
            });
        }
    }, [userInfo]);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setValues(prevValues => ({
            ...prevValues,
            [name]: value
        }));
        setUserProfileData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };



    const handleSubmit = useCallback((event) => {
        event.preventDefault();
    }, []);

    return (
        <ThemeProvider theme={customTheme}>
            <form autoComplete="off" noValidate onSubmit={handleSubmit}>
                <Card style={{ width: '100%', maxWidth: '400px', height: '99.2%' }}>
                    <CardHeader />
                    <CardContent sx={{ pt: 0 }}>
                        <Box sx={{ m: -1.5 }}>
                            <TextField
                                fullWidth
                                style={{ marginBottom: '16px' }}
                                label="First name"
                                name="firstName"
                                onChange={handleChange}
                                required
                                value={values.firstName}
                            />
                            <TextField
                                fullWidth
                                style={{ marginBottom: '16px' }}
                                label="Last name"
                                name="lastName"
                                onChange={handleChange}
                                required
                                value={values.lastName}
                            />
                            <TextField
                                fullWidth
                                style={{ marginBottom: '16px' }}
                                label="Email Address"
                                name="email"
                                onChange={handleChange}
                                required
                                value={values.email}
                            />
                            <TextField
                                fullWidth
                                style={{ marginBottom: '16px' }}
                                label="Phone Number"
                                name="phone"
                                onChange={handleChange}
                                type="number"
                                value={values.phone}
                            />
                            <UnstyledSelectRichOptions
                                name="country"
                                value={values.country}
                                onChange={handleChange}
                                label="Country"
                                required
                            />
                            <TextField
                                fullWidth
                                style={{ marginBottom: '16px' }}
                                label="Select State"
                                name="state"
                                onChange={handleChange}
                                required
                                select
                                SelectProps={{ native: true }}
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
                    <Divider />
                </Card>
            </form>
        </ThemeProvider>
    );
}

export default AccountProfileDetails;