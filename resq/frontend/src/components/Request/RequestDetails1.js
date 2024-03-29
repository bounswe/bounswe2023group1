import * as React from 'react';
import { useState } from 'react';
import { styled } from '@mui/material/styles';
import NativeSelect from '@mui/material/NativeSelect';
import InputBase from '@mui/material/InputBase';
import { Typography, Grid, FormControlLabel, Checkbox, TextField, Chip, Box, FormControl, InputLabel, Select, MenuItem, OutlinedInput } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { createTheme } from '@mui/material/styles';
import { FormHelperText } from '@mui/material';
import { NeedContext } from './NeedContext';
import { useContext } from 'react';


const illnesses = [
    'Diabetes',
    'Gluten Allergy',
    'Lactose Intolerance',
    'Nut Allergy',
    'Vegan',
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

function CustomizedSelects() {
    const [adults, setAdults] = useState('');
    const [children, setChildren] = useState('');
    const [baby, setBaby] = useState('');
    const [totalPeople, setTotalPeople] = useState('');

    const handleTotalPeopleChange = (event) => {
        const newValue = Math.max(0, event.target.value); // Prevents values less than 0
        setTotalPeople(newValue);
    };

    const handleAdultsChange = (event) => {
        const newValue = Math.max(0, event.target.value); // Prevents values less than 0
        setAdults(newValue);
    };

    const handleChildrenChange = (event) => {
        const newValue = Math.max(0, event.target.value); // Prevents values less than 0
        setChildren(newValue);
    };

    const handleBabyChange = (event) => {
        const newValue = Math.max(0, event.target.value); // Prevents values less than 0
        setBaby(newValue);
    };


    return (
        <Box display="flex" flexDirection="row" justifyContent="center" alignItems="center">
            <Box display="flex" flexDirection="row" justifyContent="center" alignItems="center">
                <FormControl sx={{ m: 1 }} variant="standard">
                    <TextField
                        id="total-people"
                        label="Total People"
                        value={totalPeople}
                        onChange={handleTotalPeopleChange}
                        type="number"
                        margin="normal"
                        variant="outlined"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </FormControl>
                <FormControl sx={{ m: 1 }} variant="standard">
                    <TextField
                        id="adults-count"
                        label="Adults"
                        value={adults}
                        onChange={handleAdultsChange}
                        type="number"
                        margin="normal"
                        variant="outlined"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </FormControl>
                <FormControl sx={{ m: 1 }} variant="standard">
                    <TextField
                        id="children-count"
                        label="Children"
                        value={children}
                        onChange={handleChildrenChange}
                        type="number"
                        margin="normal"
                        variant="outlined"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </FormControl>
                <FormControl sx={{ m: 1 }} variant="standard">
                    <TextField
                        id="baby-count"
                        label="Baby"
                        value={baby}
                        onChange={handleBabyChange}
                        type="number"
                        margin="normal"
                        variant="outlined"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </FormControl>
            </Box>
        </Box>
    );
}

export default function RequestDetails({ setNeedData }) {

    const [otherRestrictions, setOtherRestrictions] = useState('');
    const [isWaterChecked, setIsWaterChecked] = useState(true);
    const [isFoodChecked, setIsFoodChecked] = useState(true);
    const [selectedIllnesses, setSelectedIllnesses] = useState([]);

    const { updateState } = useContext(NeedContext)

    const handleWaterCheck = (event) => {
        setIsWaterChecked(event.target.checked);
        updateState({ water: event.target.checked });
    };

    const handleFoodCheck = (event) => {
        setIsFoodChecked(event.target.checked);
        updateState({ food: event.target.checked });
    };

    const handleIllnessChange = (event) => {
        const illnesses = event.target.value;
        setSelectedIllnesses(illnesses);
        updateState({ illnesses: illnesses });
    };


    const handleOtherRestrictionsChange = (event) => {
        setOtherRestrictions(event.target.value);
    };


    // Menu properties
    const ITEM_HEIGHT = 48;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
        PaperProps: {
            style: {
                maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
                width: 250,
            },
        },
    };


    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Request Details
            </Typography>
            <Typography variant="h8" color="primary" font="bold" gutterBottom style={{ fontStyle: 'italic', fontWeight: 'bold' }}>
                *Reccurrent Needs*
            </Typography>
            <CustomizedSelects />

            <Grid container spacing={3}>
                <Grid item xs={12} >
                    <FormControlLabel
                        control={<Checkbox checked={isWaterChecked} onChange={handleWaterCheck} color="primary" />}
                        label="Water"
                    />
                </Grid>
                {/* Food Checkbox and Chip */}
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox checked={isFoodChecked} onChange={handleFoodCheck} color="primary" />}
                        label="Food"
                    />
                    {isFoodChecked && (
                        <FormControl sx={{ position: 'relative', top: '-10px', width: 300 }}>
                            <InputLabel id="demo-multiple-chip-label">Dietary Restrictions</InputLabel>
                            <Select
                                labelId="demo-multiple-chip-label"
                                id="demo-multiple-chip"
                                multiple
                                value={selectedIllnesses}
                                onChange={handleIllnessChange}
                                input={<OutlinedInput id="select-multiple-chip" label="Dietary Restrictions" />}
                                renderValue={(selected) => (
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                        {selected.map((value) => (
                                            <Chip key={value} label={value} />
                                        ))}
                                    </Box>
                                )}
                                MenuProps={MenuProps}
                            >
                                {illnesses.map((illness) => (
                                    <MenuItem
                                        key={illness}
                                        value={illness}

                                        style={getStyles(illness, selectedIllnesses, customTheme)}
                                    >
                                        {illness}
                                    </MenuItem>
                                ))}
                            </Select>
                            <FormHelperText style={{ color: 'red' }}>Multiple selection is allowed</FormHelperText>
                        </FormControl>
                    )}
                </Grid>
            </Grid>
        </React.Fragment >
    );
}