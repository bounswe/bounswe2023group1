import React, { useState } from 'react';
import { Typography, Grid, FormControlLabel, Checkbox, TextField, Chip, Box, FormControl, InputLabel, Select, MenuItem, OutlinedInput, FormHelperText } from '@mui/material';
import { createTheme } from '@mui/material/styles';

const illnesses = [
    'Diabetes',
    'Asthma',
    'Heart Disease',
    'Hypertension',
    'Arthritis',
    'Depression',
    'Chronic Kidney Disease',
    'Epilepsy',
    'Migraine',
    'Thyroid Disorders',
    'Chronic Obstructive Pulmonary Disease (COPD)',
    'Cancer',
    'Anemia',
    'Allergies',
    'Gastrointestinal Disorders',
    'Autoimmune Diseases',
    'Skin Conditions (e.g., Psoriasis, Eczema)',
    'Mental Health Disorders',
    'Neurological Disorders',
];


const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

function getStyles(name, selectedNames, customTheme) {
    return {
        fontWeight:
            selectedNames.indexOf(name) === -1
                ? customTheme.typography.fontWeightRegular
                : customTheme.typography.fontWeightMedium,
    };
}

export default function RequestDetails() {
    const [selectedIllnesses, setSelectedIllnesses] = useState([]);
    const [illnessCounts, setIllnessCounts] = useState({});
    const [isShelterChecked, setIsShelterChecked] = useState(false);
    const [isHeaterChecked, setIsHeaterChecked] = useState(false);
    const [isMedicineChecked, setIsMedicineChecked] = useState(false);

    const handleIllnessChange = (event) => {
        const newSelectedIllnesses = event.target.value;
        setSelectedIllnesses(newSelectedIllnesses);

        // Initialize counts for new illnesses
        const updatedIllnessCounts = { ...illnessCounts };
        newSelectedIllnesses.forEach(illness => {
            if (!updatedIllnessCounts[illness]) {
                updatedIllnessCounts[illness] = 0;
            }
        });
        setIllnessCounts(updatedIllnessCounts);
    };

    // Function to handle changes in illness counts
    const handleIllnessCountChange = (illness, count) => {
        setIllnessCounts({ ...illnessCounts, [illness]: count });
    };


    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Request Details
            </Typography>

            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox checked={isShelterChecked} onChange={(e) => setIsShelterChecked(e.target.checked)} color="primary" />}
                        label="Shelter"
                    />
                </Grid>

                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox checked={isHeaterChecked} onChange={(e) => setIsHeaterChecked(e.target.checked)} color="primary" />}
                        label="Heater"
                    />
                </Grid>

                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox checked={isMedicineChecked} onChange={(e) => setIsMedicineChecked(e.target.checked)} color="primary" />}
                        label="Medicine"
                    />
                    {isMedicineChecked && (
                        <Box>
                            <FormControl sx={{ m: 1, width: 300 }}>
                                <InputLabel id="demo-multiple-chip-label">Medical Conditions</InputLabel>
                                <Select
                                    labelId="demo-multiple-chip-label"
                                    id="demo-multiple-chip"
                                    multiple
                                    value={selectedIllnesses}
                                    onChange={handleIllnessChange}
                                    input={<OutlinedInput id="select-multiple-chip" label="Medical Conditions" />}
                                    renderValue={(selected) => (
                                        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                            {selected.map((illness) => (
                                                <Chip key={illness} label={illness} />
                                            ))}
                                        </Box>
                                    )}
                                    MenuProps={{ PaperProps: { style: { maxHeight: 48 * 4.5 + 8, width: 250 } } }}
                                >
                                    {illnesses.map((illness) => (
                                        <MenuItem key={illness} value={illness} style={getStyles(illness, selectedIllnesses, customTheme)}>
                                            {illness}
                                        </MenuItem>
                                    ))}
                                </Select>
                                <FormHelperText style={{ color: 'red' }}>Multiple selection is allowed</FormHelperText>
                            </FormControl>
                            {selectedIllnesses.map((illness) => (
                                <Box key={illness} sx={{ display: 'flex', alignItems: 'center', mt: 2 }}>
                                    <Typography sx={{ mr: 2 }}>{illness} Count:</Typography>
                                    <TextField
                                        type="number"
                                        value={illnessCounts[illness]}
                                        onChange={(e) => handleIllnessCountChange(illness, e.target.value)}
                                        size="small"
                                    />
                                </Box>
                            ))}
                        </Box>
                    )}
                </Grid>
            </Grid>
        </React.Fragment >
    );
}
