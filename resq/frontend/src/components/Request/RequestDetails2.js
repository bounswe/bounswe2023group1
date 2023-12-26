import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Typography, Grid, FormControlLabel, Checkbox, TextField, Chip, Box, FormControl, InputLabel, Select, MenuItem, OutlinedInput } from '@mui/material';
import { createTheme } from '@mui/material/styles';
import { FormHelperText } from '@mui/material';
import { InputAdornment } from '@mui/material';


const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

export default function RequestDetails2({ setNeedData }) {
    const [isClothingChecked, setIsClothingChecked] = useState(false);

    const [clothingQuantities, setClothingQuantities] = useState({});

    const clothingCategories = [
        'WomanXL', 'WomanL', 'WomanM', 'WomanS',
        'ManXL', 'ManL', 'ManM', 'ManS',
        'Girl-4/6', 'Girl-6/9', 'Girl-9/12',
        'Boy-4/6', 'Boy-6/9', 'Boy-9/12',
        'Baby-0/2', 'Baby-2/4'
    ];

    useEffect(() => {
        setNeedData(prevData => ({
            ...prevData,
            clothingQuantities: clothingQuantities
        }));
    }, [clothingQuantities, setNeedData]);

    const handleClothingQuantityChange = (category, event) => {
        const value = event.target.value.replace(/^0+/, '') || '0';
        setClothingQuantities(prevQuantities => ({
            ...prevQuantities,
            [category]: value
        }));
    };



    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>Request Details</Typography>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="clothing" checked={isClothingChecked} onChange={(e) => setIsClothingChecked(e.target.checked)} />}
                        label="Clothing"
                    />
                    {isClothingChecked && (
                        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                            {clothingCategories.map((category) => (
                                <Box key={category} sx={{ display: 'flex', alignItems: 'center' }}>
                                    <Typography sx={{ mr: 2 }}>{category}:</Typography>
                                    <TextField
                                        type="number"
                                        value={clothingQuantities[category] || 0}
                                        onChange={(e) => handleClothingQuantityChange(category, e)}
                                        size="small"
                                    />
                                </Box>
                            ))}
                        </Box>
                    )}
                </Grid>
            </Grid>
        </React.Fragment>
    );
}
