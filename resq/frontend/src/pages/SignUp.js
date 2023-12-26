import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import disasterImage from "../disaster.png";
import { useNavigate } from 'react-router-dom';
import { signup } from "../AppService";
import Popover from '@mui/material/Popover';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';


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

const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

export default function SignUp() {
    const [popoverAnchorEl, setPopoverAnchorEl] = React.useState(null);

    const handlePopoverOpen = (event) => {
        setPopoverAnchorEl(event.currentTarget);
    };

    const handlePopoverClose = () => {
        setPopoverAnchorEl(null);
    };

    const isPopoverOpen = Boolean(popoverAnchorEl);

    const navigate = useNavigate();
    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [firstName, setFirstName] = React.useState('');
    const [lastName, setLastName] = React.useState('');
    const [signUpClicked, setSignUpClicked] = React.useState(false);

    async function signUp() {
        const registerUserRequest = { email, password, name: firstName, surname: lastName };

        try {
            const response = await signup(registerUserRequest);
            if (response.data) {
                console.log(response.data);
            }
            return response;
        } catch (error) {
            return error.response;
        }
    }


    const handleSignInClick = () => {
        navigate(`/signin?email=${email}`);
    };


    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!signUpClicked) {
            alert('Please accept the terms and conditions.');
            return;
        }

        if (password.includes(' ') || password.length < 8) {
            alert("Password must be at least 8 characters and cannot contain empty characters!");
            return;
        }

        const registerUserRequest = {
            email,
            password,
            firstName,
            lastName,
        };

        const response = await signUp(registerUserRequest);

        if (response?.status === 200) {
            console.log(response.data);
            alert('APPROVED');
            navigate('/signin');
        } else if (response?.status === 400) {
            // Check for specific bad request error related to user already signed up
            alert('You have already signed up. Please sign in.');
        } else {
            // Handle other errors
            alert('Signup failed. Please try again.');
        }
    }


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
                    <Avatar sx={{ width: 80, height: 80, marginBottom: '10px' }}>
                        <img
                            src={disasterImage}
                            alt="Disaster"
                            style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                        />
                    </Avatar>
                    <Typography component="h5" variant="h5" sx={{ color: 'red', fontWeight: 'bold', margin: '0' }}>
                        ResQ
                    </Typography>

                    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%' }}>
                        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
                            <Grid container spacing={2}>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        autoComplete="given-name"
                                        name="firstName"
                                        required
                                        fullWidth
                                        id="firstName"
                                        label="First Name"
                                        autoFocus
                                        value={firstName}
                                        onChange={(e) => setFirstName(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        required
                                        fullWidth
                                        id="lastName"
                                        label="Last Name"
                                        name="lastName"
                                        autoComplete="family-name"
                                        value={lastName}
                                        onChange={(e) => setLastName(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        fullWidth
                                        id="email"
                                        label="Email"
                                        name="email"
                                        autoComplete="email"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        fullWidth
                                        name="password"
                                        label="Password"
                                        type="password"
                                        id="password"
                                        autoComplete="new-password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <FormControlLabel
                                        control={<Checkbox checked={signUpClicked}
                                            onChange={() => setSignUpClicked(!signUpClicked)}
                                            color="error" />}
                                        label={
                                            <Typography variant="body2" color="text.secondary">
                                                By signing up, you agree to our{" "}
                                                <span
                                                    style={{ color: "red", textDecoration: "underline", cursor: "pointer" }}
                                                    onClick={handlePopoverOpen}
                                                >
                                                    Terms, Privacy Policy, and Cookies Policy
                                                </span>
                                                .
                                            </Typography>

                                        }
                                    />
                                </Grid>
                            </Grid>
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                onClick={(e) => {
                                    e.preventDefault();
                                    setSignUpClicked(true);
                                    handleSubmit(e);
                                }}
                                sx={{ mt: 3, mb: 2 }}
                            >
                                Sign Up
                            </Button>
                            <Grid container justifyContent="flex-end">
                                <Grid item>
                                    <Link onClick={handleSignInClick} variant="body2">
                                        Already have an account? Sign in
                                    </Link>
                                </Grid>
                            </Grid>
                        </Box>
                        <Copyright sx={{ mt: 8 }} />
                    </div>
                </Box>
                {/* Existing JSX */}
                <Popover
                    open={isPopoverOpen}
                    anchorEl={popoverAnchorEl}
                    onClose={handlePopoverClose}
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'center',
                    }}
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'center',
                    }}
                >
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>
                        <ListItem>
                            <ListItemText primary="Introduction" secondary="This Privacy Policy details the commitment of RESQ to protect the privacy of its users in accordance with the KVKK." />
                        </ListItem>
                        Privacy Policy of RESQ
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>
                        1. Introduction
                        This Privacy Policy details the commitment of RESQ to protect the privacy of its users in accordance with the KVKK. It outlines our practices concerning the collection, use, and safeguarding of personal data.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        2. Scope and Consent
                        This policy applies to all personal data processed by RESQ through its services. By using our services, you consent to the collection, processing, and use of your personal data as described herein.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        3. Data Controller
                        RESQ is the Data Controller responsible for the processing of your personal data. Contact details: resq.org.tr
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        4. Types of Data Collected
                        We collect various types of personal data including, but not limited to:
                        * Identification Data: Name, email address, phone number, etc.
                        * Location Data: Geographical location data for service delivery.
                        * Usage Data: Information on how the service is accessed and used.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        5. Legal Basis for Processing
                        The processing of your data is based on the following legal grounds:
                        * Consent: Your explicit consent for specific purposes.
                        * Contractual Necessity: Processing necessary for the performance of a contract.
                        * Legal Obligation: Processing necessary for compliance with our legal obligations.
                        * Legitimate Interests: Processing necessary for our legitimate interests or those of a third party, provided your interests and fundamental rights do not override those interests.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        6. Purpose of Data Processing
                        Your data is processed for purposes including:
                        * Delivering and improving our services.
                        * Personalizing user experience.
                        * Communicating effectively with users.
                        * Fulfilling our legal and contractual obligations.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        7. Data Protection Measures
                        We employ advanced security measures to protect your personal data against unauthorized access, alteration, and destruction. This includes encryption, access controls, and secure data storage.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        8. User Rights
                        Under KVKK, you have the right to:
                        * Access and obtain a copy of your data.
                        * Request rectification or erasure.
                        * Limit or object to the processing of your data.
                        * Data portability.
                        * Withdraw consent at any time, without affecting the lawfulness of processing based on consent before its withdrawal.

                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        9. Data Retention Policy
                        We retain personal data only for as long as necessary for the purposes for which it is processed, unless otherwise required by law.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        10. International Data Transfers
                        For data transferred outside the Turkey, we ensure adequate protection measures are in place as required by KVKK.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        11. Amendments to the Policy
                        This Privacy Policy may be updated periodically. We will notify you of significant changes by posting the new policy on our platform or through other means.
                    </List>
                    <List dense sx={{ width: '100%', maxWidth: 600, bgcolor: 'background.paper' }}>

                        12. Contact Us
                        For any questions or concerns about our privacy practices or your data rights, please contact us at: resq.org.tr

                    </List>
                </Popover>

            </Container>
        </ThemeProvider >
    );
}

