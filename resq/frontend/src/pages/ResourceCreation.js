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
import ResourceAddress from '../components/ResourceAddress';
import ResourceDetail1 from '../components/ResourceDetail1';
import ResourceDetail2 from '../components/ResourceDetail2';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { ResourceProvider } from '../components/ResourceContext';
import { useResource } from './ResourceContext';
import axios from 'axios';


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


const steps = ['Resource delivery address', 'Type of Resource', 'Resource Details'];

function getStepContent(step) {
    switch (step) {
        case 0:
            return <ResourceAddress />;
        case 1:
            return <ResourceDetail1 />;
        case 2:
            return <ResourceDetail2 />;
        default:
            throw new Error('Unknown step');
    }
}

export default function Resource() {
    const [activeStep, setActiveStep] = React.useState(0);
    const { resourceData } = useResource();

    const handleSubmit = () => {
        const url = 'http://api.resq.org.tr/resq/api/v1/need/createNeed';
        const userId = localStorage.getItem('userId');

        axios.post(`${url}?userId=${userId}`, resourceData, {
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => {
                if (response.status === 200) {
                    console.log('Resource created successfully:', response.data);
                    alert('Resource created successfully!');
                } else {
                    console.error('Error creating resource:', response.statusText);
                }
            })
            .catch(error => {
                console.error('Error creating resource:', error);
            });
    };


    const handleNext = () => {
        if (activeStep === steps.length - 1) {
            handleSubmit();
        } else {
            setActiveStep(activeStep + 1);
        }
    };


    const handleBack = () => {
        setActiveStep(activeStep - 1);
    };


    return (
        <ResourceProvider>
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
                                        Resource is on its way!
                                    </Typography>
                                    <Typography variant="subtitle1">
                                        Resource is saved with the details.
                                    </Typography>
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
        </ResourceProvider>
    );
}