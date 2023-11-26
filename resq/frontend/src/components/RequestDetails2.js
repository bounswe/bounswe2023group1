import React, { useState } from 'react';
import { styled } from '@mui/material/styles';
import { Typography, Grid, FormControlLabel, Checkbox, TextField, Chip, Box, FormControl, InputLabel, Select, MenuItem, OutlinedInput } from '@mui/material';
import { createTheme } from '@mui/material/styles';
import { FormHelperText } from '@mui/material';

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

export default function RequestDetails2() {
    const [isClothingChecked, setIsClothingChecked] = useState(false);
    const [isShelterChecked, setIsShelterChecked] = useState(false);
    const [isTentChecked, setIsTentChecked] = useState(false);
    const [isMedicationChecked, setIsMedicationChecked] = useState(false);

    // State for each TextField and corresponding Chips
    const [inputValues, setInputValues] = useState(['', '', '', '']);
    const [selectedValues, setSelectedValues] = useState([[], [], [], []]);

    // State for each TextField and corresponding Chips
    const [inputValue1, setInputValue1] = useState('');
    const [selectedValues1, setSelectedValues1] = useState([]);

    const [inputValue2, setInputValue2] = useState('');
    const [selectedValues2, setSelectedValues2] = useState([]);

    const [inputValue3, setInputValue3] = useState('');
    const [selectedValues3, setSelectedValues3] = useState([]);

    const [inputValue4, setInputValue4] = useState('');
    const [selectedValues4, setSelectedValues4] = useState([]);

    const handleInputChange1 = (event) => {
        setInputValue1(event.target.value);
    };

    const handleKeyPress1 = (event) => {
        if (event.key === 'Enter' && inputValue2) {
            if (!selectedValues1.includes(inputValue1)) {
                setSelectedValues1([...selectedValues2, inputValue1]);
            }
            setInputValue1('');
        }
    };

    const handleDelete1 = (value) => {
        setSelectedValues1(selectedValues1.filter(item => item !== value));
    };

    const handleInputChange2 = (event) => {
        setInputValue2(event.target.value);
    };

    const handleKeyPress2 = (event) => {
        if (event.key === 'Enter' && inputValue2) {
            if (!selectedValues2.includes(inputValue2)) {
                setSelectedValues2([...selectedValues2, inputValue2]);
            }
            setInputValue2('');
        }
    };

    const handleDelete2 = (value) => {
        setSelectedValues2(selectedValues2.filter(item => item !== value));
    };

    // Handler for the third text field
    const handleInputChange3 = (event) => {
        setInputValue3(event.target.value);
    };

    const handleKeyPress3 = (event) => {
        if (event.key === 'Enter' && inputValue3) {
            if (!selectedValues3.includes(inputValue3)) {
                setSelectedValues3([...selectedValues3, inputValue3]);
            }
            setInputValue3('');
        }
    };

    const handleDelete3 = (value) => {
        setSelectedValues3(selectedValues3.filter(item => item !== value));
    };

    // Handler for the fourth text field
    const handleInputChange4 = (event) => {
        setInputValue4(event.target.value);
    };

    const handleKeyPress4 = (event) => {
        if (event.key === 'Enter' && inputValue4) {
            if (!selectedValues4.includes(inputValue4)) {
                setSelectedValues4([...selectedValues4, inputValue4]);
            }
            setInputValue4('');
        }
    };

    const handleDelete4 = (value) => {
        setSelectedValues4(selectedValues4.filter(item => item !== value));
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

    // Add your handler functions here

    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Request Details
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="clothing" checked={isClothingChecked} onChange={(e) => setIsClothingChecked(e.target.checked)} />}
                        label="Clothing"
                    />
                    {isClothingChecked && (
                        <>

                            <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, overflowX: 'auto' }}>
                                Woman
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Woman - XL"
                                        value={inputValue1}
                                        onChange={handleInputChange1}
                                        onKeyPress={handleKeyPress1}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        helperText="Press Enter to add values"
                                        FormHelperTextProps={{ style: { color: 'red' } }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues1.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete1(value)} />
                                        ))}
                                    </Box>
                                </Box>

                                {/* Box 2 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Woman - L"
                                        value={inputValue2}
                                        onChange={handleInputChange2}
                                        onKeyPress={handleKeyPress2}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        helperText="Press Enter to add values"
                                        FormHelperTextProps={{ style: { color: 'red' } }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues2.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete2(value)} />
                                        ))}
                                    </Box>
                                </Box>
                                {/* Box 3 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Woman - M"
                                        value={inputValue3}
                                        onChange={handleInputChange3}
                                        onKeyPress={handleKeyPress3}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        helperText="Press Enter to add values"
                                        FormHelperTextProps={{ style: { color: 'red' } }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues3.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete3(value)} />
                                        ))}
                                    </Box>
                                </Box>

                                {/* Box 4 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Woman - S"
                                        value={inputValue4}
                                        onChange={handleInputChange4}
                                        onKeyPress={handleKeyPress4}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        helperText="Press Enter to add values"
                                        FormHelperTextProps={{ style: { color: 'red' } }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues4.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete4(value)} />
                                        ))}
                                    </Box>
                                </Box>
                            </Box>
                        </>
                    )}
                </Grid>
            </Grid>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="clothing" checked={isClothingChecked} onChange={(e) => setIsClothingChecked(e.target.checked)} />}
                        label="Clothing"
                    />
                    <FormControlLabel
                        control={<Checkbox color="primary" name="shelter" checked={isShelterChecked} onChange={(e) => setIsShelterChecked(e.target.checked)} />}
                        label="Shelter"
                    />
                    <FormControlLabel
                        control={<Checkbox color="primary" name="tent" checked={isTentChecked} onChange={(e) => setIsTentChecked(e.target.checked)} />}
                        label="Tent"
                    />
                    <FormControlLabel
                        control={<Checkbox color="primary" name="medication" checked={isMedicationChecked} onChange={(e) => setIsMedicationChecked(e.target.checked)} />}
                        label="Medication"
                    />
                </Grid>
                {/* Your existing code for dietary restrictions */}
            </Grid>
        </React.Fragment >
    );
}
