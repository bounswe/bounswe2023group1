import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Toolbar from '@mui/material/Toolbar';
import Paper from '@mui/material/Paper';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';
import RequestAddress from './RequestAddress';
import RequestDetails1 from './RequestDetails1';
import RequestDetails2 from './RequestDetails2';
import RequestDetails3 from './RequestDetails3';
import RequestReview from './RequestReview';
import { createTheme, ThemeProvider } from '@mui/material/styles';

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


const steps = ['Request address', 'Recurrent Needs', 'Clothing Needs', 'Further Needs', 'Request Review'];

function getStepContent(step) {
    switch (step) {
        case 0:
            return <RequestAddress />;
        case 1:
            return <RequestDetails1 />;
        case 2:
            return <RequestDetails2 />;
        case 3:
            return <RequestDetails3 />;
        case 4:
            return <RequestReview />;
        default:
            throw new Error('Unknown step');
    }
}

export default function Request() {
    const [activeStep, setActiveStep] = React.useState(0);

    const handleNext = () => {
        setActiveStep(activeStep + 1);
    };

    const handleBack = () => {
        setActiveStep(activeStep - 1);
    };

    return (
        <React.Fragment>
            <ThemeProvider theme={customTheme}>
                <CssBaseline />
                <AppBar
                    position="absolute"
                    color="default"
                    elevation={0}
                    sx={{
                        position: 'relative',
                        borderBottom: (t) => `1px solid ${t.palette.divider}`,
                    }}
                >
                </AppBar>
                <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
                    <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
                        <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5 }}>
                            {steps.map((label) => (
                                <Step key={label}>
                                    <StepLabel>{label}</StepLabel>
                                </Step>
                            ))}
                        </Stepper>
                        {activeStep === steps.length ? (
                            <React.Fragment>
                                <Typography variant="h5" gutterBottom>
                                    Your request is saved.
                                </Typography>
                                <Typography variant="subtitle1">
                                    Your request has been successfully created. We will process it promptly. You can track the status of your request on your profile page.                            </Typography>
                            </React.Fragment>
                        ) : (
                            <React.Fragment>
                                {getStepContent(activeStep)}
                                <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                                    {activeStep !== 0 && (
                                        <Button onClick={handleBack} sx={{ mt: 3, ml: 1 }}>
                                            Back
                                        </Button>
                                    )}

                                    <Button
                                        variant="contained"
                                        onClick={handleNext}
                                        sx={{ mt: 3, ml: 1 }}
                                    >
                                        {activeStep === steps.length - 1 ? 'Proceed' : 'Next'}
                                    </Button>
                                </Box>
                            </React.Fragment>
                        )}
                    </Paper>
                    <Copyright />
                </Container>
            </ThemeProvider>
        </React.Fragment>
    );
}