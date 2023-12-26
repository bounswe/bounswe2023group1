import React, { useState } from 'react';
import { CssBaseline, AppBar, Box, Container, Toolbar, Paper, Stepper, Step, StepLabel, Button, Link, Typography, createTheme, ThemeProvider } from '@mui/material';
import RequestAddress from './RequestAddress';
import RequestDetails1 from './RequestDetails1';
import RequestDetails2 from './RequestDetails2';
import RequestDetails3 from './RequestDetails3';
import RequestReview from './RequestReview';
import { NeedProvider } from './NeedContext';
import { createNeed } from '../../AppService';
import { NeedContext } from './NeedContext';
import { useContext } from 'react';

function Copyright(props) {
    return (
        <div style={{ position: 'fixed', bottom: 0, width: '100%' }}>
            <Typography variant="body2" color="text.secondary" align="center" {...props}>
                {'Copyright © '}
                <Link color="inherit" href="https://github.com/bounswe/bounswe2023group1">ResQ</Link>{' '}
                {new Date().getFullYear()}.
            </Typography>
        </div>
    );
}

const customTheme = createTheme({
    palette: { primary: { main: '#FF0000' } },
});

export default function Request() {
    const [activeStep, setActiveStep] = useState(0);
    const { needs, resetNeeds } = useContext(NeedContext);
    const [needData, setNeedData] = useState({ senderId: parseInt(localStorage.getItem('userId')), gender: "MALE" });
    const steps = ['Request address', 'Recurrent Needs', 'Clothing Needs', 'Further Needs'];

    function getStepContent(step) {
        switch (step) {
            case 0: return <RequestAddress />;
            case 1: return <RequestDetails1 />;
            case 2: return <RequestDetails2 />;
            case 3: return <RequestDetails3 />;
            default: throw new Error('Unknown step');
        }
    }

    const handleNext = async () => {
        if (activeStep === steps.length - 1) {
            try {
                const commonData = { latitude: needData.latitude, longitude: needData.longitude };

                if (needData.water) {
                    const waterRequestData = { ...commonData, description: "water", categoryTreeId: needData.waterCategoryTreeId, quantity: needData.totalPeople, size: needData.totalPeople.toString(), isRecurrent: true };
                    console.log("Creating water request:", waterRequestData);
                    await createNeed(localStorage.getItem('userId'), waterRequestData);
                } else {
                    console.log("Water request not sent. Data:", { water: needData.water });
                }


                if (needData.food) {
                    const foodRequestData = { ...commonData, description: "food", categoryTreeId: needData.foodCategoryTreeId, quantity: needData.totalPeople, size: needData.totalPeople.toString(), isRecurrent: true };
                    console.log("Creating food request:", foodRequestData);
                    await createNeed(localStorage.getItem('userId'), foodRequestData);
                } else {
                    console.log("Food request not sent. Data:", { food: needData.food });
                }


                Object.entries(needData.clothingQuantities || {}).forEach(async ([category, quantity]) => {
                    if (parseInt(quantity, 10) > 0) {
                        const clothingRequestData = { ...commonData, description: "clothing", categoryTreeId: 39, quantity: parseInt(quantity, 10), size: category, isRecurrent: false };
                        console.log(`Creating clothing request for ${category}:`, clothingRequestData);
                        await createNeed(localStorage.getItem('userId'), clothingRequestData);
                    }
                });

                if (needData.shelter) {
                    const shelterRequestData = { ...commonData, description: "shelter", categoryTreeId: 44, quantity: 1, size: "M", isRecurrent: false };
                    console.log("Creating shelter request:", shelterRequestData);
                    await createNeed(localStorage.getItem('userId'), shelterRequestData);
                } else {
                    console.log("Shelter request not sent. Shelter:", { shelter: needData.shelter });
                }


                if (needData.heater) {
                    const heaterRequestData = { ...commonData, description: "heater", categoryTreeId: 46, quantity: 1, size: "M", isRecurrent: false };
                    console.log("Creating heater request:", heaterRequestData);
                    await createNeed(localStorage.getItem('userId'), heaterRequestData);
                } else {
                    console.log("Heater request not sent. Data:", { heater: needData.heater });
                }


                needData.selectedIllnesses?.forEach(async (illness) => {
                    const quantity = needData.illnessCounts[illness] || 0;
                    if (quantity > 0) {
                        const medicineRequestData = { ...commonData, description: "medicine", categoryTreeId: 79, quantity: parseInt(quantity, 10), size: illness, isRecurrent: true };
                        console.log(`Creating medicine request for ${illness}:`, medicineRequestData);
                        await createNeed(localStorage.getItem('userId'), medicineRequestData);
                    } else {
                        console.log("Medicine request not sent. Medicine:", { medicine: needData.medicine });
                    }

                });
                for (const need of needs) {
                    await createNeed(localStorage.getItem('userId'), need);
                }
                console.log("All requests have been created");
                alert('Requests created successfully!');
                resetNeeds();
            } catch (error) {
                console.error('Error while creating requests:', error);
                alert('Failed to create requests.');
            }
        } else {
            setActiveStep(activeStep + 1);
        }
    };

    const handleBack = () => setActiveStep(activeStep - 1);

    return (
        <NeedProvider>
            <ThemeProvider theme={customTheme}>
                <CssBaseline />
                <AppBar position="absolute" color="default" elevation={0} sx={{ position: 'relative', borderBottom: (t) => `1px solid ${t.palette.divider}` }} />
                <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
                    <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
                        <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5 }}>
                            {steps.map((label) => (<Step key={label}><StepLabel>{label}</StepLabel></Step>))}
                        </Stepper>
                        {activeStep === steps.length ? (
                            <React.Fragment>
                                <Typography variant="h5" gutterBottom>Your request is saved.</Typography>
                                <Typography variant="subtitle1">Your request has been successfully created. We will process it promptly. You can track the status of your request on your profile page.</Typography>
                            </React.Fragment>
                        ) : (
                            <React.Fragment>
                                {getStepContent(activeStep)}
                                <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                                    {activeStep !== 0 && (<Button onClick={handleBack} sx={{ mt: 3, ml: 1 }}>Back</Button>)}
                                    <Button variant="contained" onClick={handleNext} sx={{ mt: 3, ml: 1 }}>{activeStep === steps.length - 1 ? 'Proceed' : 'Next'}</Button>
                                </Box>
                            </React.Fragment>
                        )}
                    </Paper>
                    <Copyright />
                </Container>
            </ThemeProvider>
        </NeedProvider>
    );
}
