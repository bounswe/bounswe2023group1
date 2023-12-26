import React, { useState } from 'react';
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

export default function RequestDetails2({ setNeedDatas }) {
    const [isClothingChecked, setIsClothingChecked] = useState(false);

    // States for each TextField and corresponding Chips
    const [inputValue, setInputValue] = useState({});
    const [selectedValues, setSelectedValues] = useState({});

    const clothingCategories = [
        'WomanXL', 'WomanL', 'WomanM', 'WomanS',
        'ManXL', 'ManL', 'ManM', 'ManS',
        'Girl-4/6', 'Girl-6/9', 'Girl-9/12',
        'Boy-4/6', 'Boy-6/9', 'Boy_9/12',
        'Baby-0/2', 'Baby-2/4'
    ];

    const updateBackend = (category, values) => {
        setNeedDatas(prevData => ({
            ...prevData,
            clothing: {
                ...prevData.clothing,
                [category]: values
            }
        }));
    };

    const handleInputChange = (category, event) => {
        setInputValue({ ...inputValue, [category]: event.target.value });
    };

    const handleKeyPress = (category, event) => {
        if (event.key === 'Enter' && inputValue[category]) {
            const newValue = [...(selectedValues[category] || []), inputValue[category]];
            setSelectedValues({ ...selectedValues, [category]: newValue });
            updateBackend(category, newValue);
            setInputValue({ ...inputValue, [category]: '' });
        }
    };

    const handleDelete = (category, valueToDelete) => {
        const newValue = selectedValues[category].filter(value => value !== valueToDelete);
        setSelectedValues({ ...selectedValues, [category]: newValue });
        updateBackend(category, newValue);
    };

    const renderClothingCategory = (category, label) => {
        return (
            <Box sx={{ minWidth: '15%' }}>
                <TextField
                    label={label}
                    value={inputValue[category] || ''}
                    onChange={(e) => handleInputChange(category, e)}
                    onKeyPress={(e) => handleKeyPress(category, e)}
                    margin="normal"
                    variant="outlined"
                    fullWidth
                    InputProps={{
                        startAdornment: <InputAdornment position="start">{label.split(' - ')[1]}</InputAdornment>,
                    }}
                />
                <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                    {(selectedValues[category] || []).map((value, index) => (
                        <Chip key={index} label={value} onDelete={() => handleDelete(category, value)} />
                    ))}
                </Box>
            </Box>
        );
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
                        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, overflowX: 'auto' }}>
                            {clothingCategories.map((category, index) => {
                                const label = `${category.split(/(?=[A-Z][^A-Z])/).join(' - ')}`;
                                return (
                                    <Box key={index} sx={{ display: 'flex', flexDirection: 'row', gap: 2 }}>
                                        <Typography variant="subtitle1" sx={{ color: 'red', mr: 2 }}>{label.split(' - ')[0]}</Typography>
                                        {renderClothingCategory(category, label)}
                                    </Box>
                                );
                            })}
                        </Box>
                    )}
                </Grid>
            </Grid>
        </React.Fragment>
    );
}
