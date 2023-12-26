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
import { NeedProvider as MyNeedProvider } from './NeedContext';
import { useState } from 'react';
import { createNeed } from '../../AppService';

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


export default function Request() {
    const [activeStep, setActiveStep] = React.useState(0);
    const [needData, setNeedData] = useState({
        senderId: parseInt(localStorage.getItem('userId')),
        gender: "MALE"
    });

    const steps = ['Request address', 'Recurrent Needs', 'Clothing Needs', 'Further Needs'];

    function getStepContent(step) {
        switch (step) {
            case 0:
                return <RequestAddress needData={needData} setNeedData={setNeedData} />;
            case 1:
                return <RequestDetails1 needData={needData} setNeedData={setNeedData} />;
            case 2:
                return <RequestDetails2 needData={needData} setNeedData={setNeedData} />;
            case 3:
                return <RequestDetails3 needData={needData} setNeedData={setNeedData} />;
            default:
                throw new Error('Unknown step');
        }
    }

    const handleNext = async () => {
        if (activeStep === steps.length - 1) {
            try {
                const commonData = {
                    latitude: needData.latitude,
                    longitude: needData.longitude,
                };

                // Handling water request
                if (needData.water) {
                    const waterRequestData = {
                        ...commonData,
                        description: "water",
                        categoryTreeId: needData.waterCategoryTreeId,
                        quantity: needData.totalPeople,
                        size: needData.totalPeople.toString(),
                        isRecurrent: true
                    };
                    console.log("Sending water request:", waterRequestData);
                    await createNeed(localStorage.getItem('userId'), waterRequestData);

                }

                // Handling food request
                if (needData.food) {
                    const foodRequestData = {
                        ...commonData,
                        description: "food",
                        categoryTreeId: needData.foodCategoryTreeId,
                        quantity: needData.totalPeople,
                        size: needData.totalPeople.toString(),
                        isRecurrent: true
                    };
                    console.log("Sending food request:", foodRequestData);
                    await createNeed(localStorage.getItem('userId'), foodRequestData);
                }

                // Handling clothing requests
                for (const [category, quantity] of Object.entries(needData.clothingQuantities || {})) {
                    if (parseInt(quantity, 10) > 0) {
                        const clothingRequestData = {
                            ...commonData,
                            description: "clothing",
                            categoryTreeId: 39, // Assuming 39 is the category ID for clothing
                            quantity: parseInt(quantity, 10), // Convert string to integer
                            size: category,
                            isRecurrent: false
                        };
                        console.log(`Sending clothing request for ${category}:`, clothingRequestData);
                        await createNeed(localStorage.getItem('userId'), clothingRequestData);
                    }
                }

                // Handling Shelter request
                if (needData.shelterChecked) {
                    const shelterRequestData = {
                        ...commonData,
                        description: "shelter",
                        categoryTreeId: needData.shelterCategoryTreeId,
                        quantity: 1,
                        size: "M",
                        isRecurrent: false
                    };
                    console.log("Sending shelter request:", shelterRequestData);
                    await createNeed(localStorage.getItem('userId'), shelterRequestData);
                }

                // Handling Heater request
                if (needData.heaterChecked) {
                    const heaterRequestData = {
                        ...commonData,
                        description: "heater",
                        categoryTreeId: needData.heaterCategoryTreeId,
                        quantity: 1,
                        size: "M",
                        isRecurrent: false
                    };
                    console.log("Sending heater request:", heaterRequestData);
                    await createNeed(localStorage.getItem('userId'), heaterRequestData);
                }

                // Handling Medicine requests
                if (needData.medicineChecked) {
                    needData.selectedIllnesses.forEach(async (illness) => {
                        const quantity = needData.illnessCounts[illness] || 0;
                        if (quantity > 0) {
                            const medicineRequestData = {
                                ...commonData,
                                description: "medicine",
                                categoryTreeId: needData.medicineCategoryTreeId,
                                quantity: parseInt(quantity, 10),
                                size: illness,
                                isRecurrent: true
                            };
                            console.log(`Sending medicine request for ${illness}:`, medicineRequestData);
                            await createNeed(localStorage.getItem('userId'), medicineRequestData);
                        }
                    });
                }

                console.log("All createNeed requests have been sent");
                alert('Requests created successfully!');
            } catch (error) {
                console.error('Error while creating requests:', error);
                alert('Failed to create requests.');
            }
        } else {
            setActiveStep(activeStep + 1);
        }
    };




    const handleBack = () => {
        setActiveStep(activeStep - 1);
    };

    return (
        <MyNeedProvider>
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
        </MyNeedProvider>
    );
}