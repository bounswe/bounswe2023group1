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

export default function RequestDetails2() {
    const [isClothingChecked, setIsClothingChecked] = useState(false);

    // State for each TextField and corresponding Chips
    const [inputValue1, setInputValue1] = useState('');
    const [selectedValues1, setSelectedValues1] = useState([]);

    const [inputValue2, setInputValue2] = useState('');
    const [selectedValues2, setSelectedValues2] = useState([]);

    const [inputValue3, setInputValue3] = useState('');
    const [selectedValues3, setSelectedValues3] = useState([]);

    const [inputValue4, setInputValue4] = useState('');
    const [selectedValues4, setSelectedValues4] = useState([]);

    const [inputValue5, setInputValue5] = useState('');
    const [selectedValues5, setSelectedValues5] = useState([]);

    const [inputValue6, setInputValue6] = useState('');
    const [selectedValues6, setSelectedValues6] = useState([]);

    const [inputValue7, setInputValue7] = useState('');
    const [selectedValues7, setSelectedValues7] = useState([]);

    const [inputValue8, setInputValue8] = useState('');
    const [selectedValues8, setSelectedValues8] = useState([]);

    const [inputValue9, setInputValue9] = useState('');
    const [selectedValues9, setSelectedValues9] = useState([]);

    const [inputValue10, setInputValue10] = useState('');
    const [selectedValues10, setSelectedValues10] = useState([]);

    const [inputValue11, setInputValue11] = useState('');
    const [selectedValues11, setSelectedValues11] = useState([]);

    const [inputValue12, setInputValue12] = useState('');
    const [selectedValues12, setSelectedValues12] = useState([]);

    const [inputValue13, setInputValue13] = useState('');
    const [selectedValues13, setSelectedValues13] = useState([]);

    const [inputValue14, setInputValue14] = useState('');
    const [selectedValues14, setSelectedValues14] = useState([]);

    const [inputValue15, setInputValue15] = useState('');
    const [selectedValues15, setSelectedValues15] = useState([]);

    const [inputValue16, setInputValue16] = useState('');
    const [selectedValues16, setSelectedValues16] = useState([]);

    const handleInputChange1 = (event) => {
        setInputValue1(event.target.value);
    };

    const handleKeyPress1 = (event) => {
        if (event.key === 'Enter' && inputValue1) {
            const newValue = inputValue1 + ' XL';
            if (!selectedValues1.includes(newValue)) {
                setSelectedValues1([...selectedValues1, newValue]);
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
            const newValue = inputValue2 + ' L';
            if (!selectedValues2.includes(newValue)) {
                setSelectedValues2([...selectedValues2, newValue]);
            }
            setInputValue2('');
        }
    };

    const handleDelete2 = (value) => {
        setSelectedValues2(selectedValues2.filter(item => item !== value));
    };

    const handleInputChange3 = (event) => {
        setInputValue3(event.target.value);
    };

    const handleKeyPress3 = (event) => {
        const newValue = inputValue3 + ' M';
        if (event.key === 'Enter' && inputValue3) {
            if (!selectedValues3.includes(newValue)) {
                setSelectedValues3([...selectedValues3, newValue]);
            }
            setInputValue3('');
        }
    };

    const handleDelete3 = (value) => {
        setSelectedValues3(selectedValues3.filter(item => item !== value));
    };

    const handleInputChange4 = (event) => {
        setInputValue4(event.target.value);
    };

    const handleKeyPress4 = (event) => {
        const newValue = inputValue4 + ' S';
        if (event.key === 'Enter' && inputValue4) {
            if (!selectedValues4.includes(newValue)) {
                setSelectedValues4([...selectedValues4, newValue]);
            }
            setInputValue4('');
        }
    };

    const handleDelete4 = (value) => {
        setSelectedValues4(selectedValues4.filter(item => item !== value));
    };

    const handleInputChange5 = (event) => {
        setInputValue5(event.target.value);
    };

    const handleKeyPress5 = (event) => {
        const newValue = inputValue5 + ' XL';
        if (event.key === 'Enter' && inputValue5) {
            if (!selectedValues5.includes(newValue)) {
                setSelectedValues5([...selectedValues5, newValue]);
            }
            setInputValue5('');
        }
    };

    const handleDelete5 = (value) => {
        setSelectedValues5(selectedValues5.filter(item => item !== value));
    };

    const handleInputChange6 = (event) => {
        setInputValue6(event.target.value);
    };

    const handleKeyPress6 = (event) => {
        const newValue = inputValue6 + ' L';
        if (event.key === 'Enter' && inputValue6) {
            if (!selectedValues6.includes(newValue)) {
                setSelectedValues6([...selectedValues6, newValue]);
            }
            setInputValue6('');
        }
    };

    const handleDelete6 = (value) => {
        setSelectedValues6(selectedValues6.filter(item => item !== value));
    };

    const handleInputChange7 = (event) => {
        setInputValue7(event.target.value);
    };

    const handleKeyPress7 = (event) => {
        const newValue = inputValue7 + ' M';
        if (event.key === 'Enter' && inputValue7) {
            if (!selectedValues7.includes(newValue)) {
                setSelectedValues7([...selectedValues7, newValue]);
            }
            setInputValue7('');
        }
    };

    const handleDelete7 = (value) => {
        setSelectedValues7(selectedValues7.filter(item => item !== value));
    };

    const handleInputChange8 = (event) => {
        setInputValue8(event.target.value);
    };

    const handleKeyPress8 = (event) => {
        const newValue = inputValue8 + ' S';
        if (event.key === 'Enter' && inputValue8) {
            if (!selectedValues8.includes(newValue)) {
                setSelectedValues8([...selectedValues8, newValue]);
            }
            setInputValue8('');
        }
    };

    const handleDelete8 = (value) => {
        setSelectedValues8(selectedValues8.filter(item => item !== value));
    };

    const handleInputChange9 = (event) => {
        setInputValue9(event.target.value);
    };

    const handleKeyPress9 = (event) => {
        if (event.key === 'Enter' && inputValue1) {
            const newValue = inputValue9 + ' Girl';
            if (!selectedValues9.includes(newValue)) {
                setSelectedValues9([...selectedValues9, newValue]);
            }
            setInputValue9('');
        }
    };

    const handleDelete9 = (value) => {
        setSelectedValues9(selectedValues9.filter(item => item !== value));
    };

    const handleInputChange10 = (event) => {
        setInputValue10(event.target.value);
    };

    const handleKeyPress10 = (event) => {
        if (event.key === 'Enter' && inputValue10) {
            const newValue = inputValue10 + ' Girl';
            if (!selectedValues10.includes(newValue)) {
                setSelectedValues10([...selectedValues10, newValue]);
            }
            setInputValue10('');
        }
    };

    const handleDelete10 = (value) => {
        setSelectedValues10(selectedValues2.filter(item => item !== value));
    };

    const handleInputChange11 = (event) => {
        setInputValue11(event.target.value);
    };

    const handleKeyPress11 = (event) => {
        const newValue = inputValue11 + ' Girl';
        if (event.key === 'Enter' && inputValue11) {
            if (!selectedValues11.includes(newValue)) {
                setSelectedValues11([...selectedValues11, newValue]);
            }
            setInputValue11('');
        }
    };

    const handleDelete11 = (value) => {
        setSelectedValues11(selectedValues11.filter(item => item !== value));
    };

    const handleInputChange12 = (event) => {
        setInputValue12(event.target.value);
    };

    const handleKeyPress12 = (event) => {
        const newValue = inputValue12 + ' Boy';
        if (event.key === 'Enter' && inputValue12) {
            if (!selectedValues12.includes(newValue)) {
                setSelectedValues12([...selectedValues12, newValue]);
            }
            setInputValue12('');
        }
    };

    const handleDelete12 = (value) => {
        setSelectedValues12(selectedValues12.filter(item => item !== value));
    };

    const handleInputChange13 = (event) => {
        setInputValue13(event.target.value);
    };

    const handleKeyPress13 = (event) => {
        const newValue = inputValue13 + ' XL';
        if (event.key === 'Enter' && inputValue13) {
            if (!selectedValues13.includes(newValue)) {
                setSelectedValues13([...selectedValues5, newValue]);
            }
            setInputValue13('');
        }
    };

    const handleDelete13 = (value) => {
        setSelectedValues13(selectedValues13.filter(item => item !== value));
    };

    const handleInputChange14 = (event) => {
        setInputValue14(event.target.value);
    };

    const handleKeyPress14 = (event) => {
        const newValue = inputValue14 + ' L';
        if (event.key === 'Enter' && inputValue6) {
            if (!selectedValues14.includes(newValue)) {
                setSelectedValues14([...selectedValues14, newValue]);
            }
            setInputValue14('');
        }
    };

    const handleDelete14 = (value) => {
        setSelectedValues14(selectedValues14.filter(item => item !== value));
    };

    const handleInputChange15 = (event) => {
        setInputValue15(event.target.value);
    };

    const handleKeyPress15 = (event) => {
        const newValue = inputValue15 + ' M';
        if (event.key === 'Enter' && inputValue15) {
            if (!selectedValues15.includes(newValue)) {
                setSelectedValues15([...selectedValues15, newValue]);
            }
            setInputValue15('');
        }
    };

    const handleDelete15 = (value) => {
        setSelectedValues15(selectedValues15.filter(item => item !== value));
    };

    const handleInputChange16 = (event) => {
        setInputValue16(event.target.value);
    };

    const handleKeyPress16 = (event) => {
        const newValue = inputValue16 + ' S';
        if (event.key === 'Enter' && inputValue16) {
            if (!selectedValues16.includes(newValue)) {
                setSelectedValues8([...selectedValues16, newValue]);
            }
            setInputValue16('');
        }
    };

    const handleDelete16 = (value) => {
        setSelectedValues16(selectedValues16.filter(item => item !== value));
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
                            <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 1 }}>
                                <FormHelperText style={{ color: 'red' }}>Press Enter</FormHelperText>
                            </Box>
                            <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, overflowX: 'auto' }}>
                                <Typography variant="subtitle1" sx={{ color: 'red', mr: 2 }}>
                                    Woman
                                </Typography>
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Woman - XL"
                                        value={inputValue1}
                                        onChange={handleInputChange1}
                                        onKeyPress={handleKeyPress1}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">XL</InputAdornment>,
                                        }}
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
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">L</InputAdornment>,
                                        }}
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
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">M</InputAdornment>,
                                        }}
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
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">S</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues4.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete4(value)} />
                                        ))}
                                    </Box>
                                </Box>
                            </Box>
                            <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, overflowX: 'auto' }}>
                                <Typography variant="subtitle1" sx={{ color: 'red', mr: 5 }}>
                                    Man
                                </Typography>
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Man - XL"
                                        value={inputValue5}
                                        onChange={handleInputChange5}
                                        onKeyPress={handleKeyPress5}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">XL</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues5.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete5(value)} />
                                        ))}
                                    </Box>
                                </Box>

                                {/* Box 2 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Man - L"
                                        value={inputValue6}
                                        onChange={handleInputChange6}
                                        onKeyPress={handleKeyPress6}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">L</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues6.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete6(value)} />
                                        ))}
                                    </Box>
                                </Box>
                                {/* Box 3 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Man - M"
                                        value={inputValue7}
                                        onChange={handleInputChange7}
                                        onKeyPress={handleKeyPress7}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">M</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues7.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete7(value)} />
                                        ))}
                                    </Box>
                                </Box>

                                {/* Box 4 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Man - S"
                                        value={inputValue8}
                                        onChange={handleInputChange8}
                                        onKeyPress={handleKeyPress8}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">S</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues8.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete8(value)} />
                                        ))}
                                    </Box>
                                </Box>
                            </Box>
                            <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, overflowX: 'auto' }}>
                                <Typography variant="subtitle1" sx={{ color: 'red', mr: 1 }}>
                                    Children - Girl
                                </Typography>
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Girl - 3/6"
                                        value={inputValue9}
                                        onChange={handleInputChange9}
                                        onKeyPress={handleKeyPress9}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">Girl</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues9.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete9(value)} />
                                        ))}
                                    </Box>
                                </Box>

                                {/* Box 2 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Girl - 6/9"
                                        value={inputValue10}
                                        onChange={handleInputChange10}
                                        onKeyPress={handleKeyPress10}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">Girl</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues10.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete10(value)} />
                                        ))}
                                    </Box>
                                </Box>
                                {/* Box 3 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Girl - 9/12"
                                        value={inputValue11}
                                        onChange={handleInputChange11}
                                        onKeyPress={handleKeyPress11}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">Girl</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues11.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete11(value)} />
                                        ))}
                                    </Box>
                                </Box>

                            </Box>
                            <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, overflowX: 'auto' }}>
                                <Typography variant="subtitle1" sx={{ color: 'red', mr: 1 }}>
                                    Children - Boy
                                </Typography>
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Boy - 3/6"
                                        value={inputValue12}
                                        onChange={handleInputChange12}
                                        onKeyPress={handleKeyPress12}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">Boy</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues12.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete12(value)} />
                                        ))}
                                    </Box>
                                </Box>

                                {/* Box 2 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Boy - 6/9"
                                        value={inputValue13}
                                        onChange={handleInputChange13}
                                        onKeyPress={handleKeyPress13}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">Boy</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues13.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete13(value)} />
                                        ))}
                                    </Box>
                                </Box>
                                {/* Box 3 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Boy - 9/12"
                                        value={inputValue14}
                                        onChange={handleInputChange14}
                                        onKeyPress={handleKeyPress14}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">Boy</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues14.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete14(value)} />
                                        ))}
                                    </Box>
                                </Box>
                            </Box>
                            <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, overflowX: 'auto' }}>
                                <Typography variant="subtitle1" sx={{ color: 'red', mr: 4 }}>
                                    Baby
                                </Typography>
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Baby - 0/2"
                                        value={inputValue15}
                                        onChange={handleInputChange15}
                                        onKeyPress={handleKeyPress15}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">0/2</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues15.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete15(value)} />
                                        ))}
                                    </Box>
                                </Box>

                                {/* Box 2 */}
                                <Box sx={{ minWidth: '15%' }}>
                                    <TextField
                                        label="Baby - 2/4"
                                        value={inputValue16}
                                        onChange={handleInputChange16}
                                        onKeyPress={handleKeyPress16}
                                        margin="normal"
                                        variant="outlined"
                                        fullWidth
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start">2/4</InputAdornment>,
                                        }}
                                    />
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                        {selectedValues16.map((value, index) => (
                                            <Chip key={index} label={value} onDelete={() => handleDelete16(value)} />
                                        ))}
                                    </Box>
                                </Box>
                            </Box>
                        </>
                    )}
                </Grid>
            </Grid>
        </React.Fragment >
    );
}
