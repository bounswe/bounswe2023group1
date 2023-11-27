import React, { useState, useEffect } from 'react';
import { Typography, Grid, FormControlLabel, Checkbox, FormControl, InputLabel, Select, MenuItem, OutlinedInput } from '@mui/material';
import { createTheme } from '@mui/material/styles';
import { Theme, useTheme } from '@mui/material/styles';
import { useResource } from './ResourceContext';
import { useContext } from 'react';
import { ResourceContext } from './ResourceContext';

const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

export default function ResourceDetails1() {
    const { resourceData, updateResourceData } = useContext(ResourceContext);

    const [isMaterialResourceChecked, setIsMaterialResourceChecked] = useState(false);
    const [isHumanResourceChecked, setIsHumanResourceChecked] = useState(false);

    const [selectedMaterialValues, setSelectedMaterialValues] = useState([]);
    const [selectedHumanValues, setSelectedHumanValues] = useState([]);

    const getStyles = (item, selectedItems, theme) => {
        return {
            fontWeight:
                selectedItems.indexOf(item) === -1
                    ? theme.typography.fontWeightRegular
                    : theme.typography.fontWeightMedium,
        };
    };

    const handleMaterialChange = (event) => {
        setSelectedMaterialValues(event.target.value);
    };

    const handleHumanChange = (event) => {
        setSelectedHumanValues(event.target.value);
    };

    const theme = useTheme();

    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Resource Type
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="materialresource" checked={isMaterialResourceChecked} onChange={(e) => setIsMaterialResourceChecked(e.target.checked)} />}
                        label="Material Resource"
                    />
                    {isMaterialResourceChecked && (
                        <>
                            <FormControl sx={{ m: 1, width: 300, mt: 3 }}>
                                <Select
                                    multiple
                                    displayEmpty
                                    value={selectedMaterialValues}
                                    onChange={handleMaterialChange}
                                    input={<OutlinedInput />}
                                    renderValue={(selected) => {
                                        if (selected.length === 0) {
                                            return <em>Choose provided resource</em>;
                                        }
                                        return selected.join(', ');
                                    }}
                                    MenuProps={MenuProps}
                                    inputProps={{ 'aria-label': 'Without label' }}
                                >
                                    <MenuItem disabled value="">
                                        <em>Placeholder</em>
                                    </MenuItem>
                                    {materialneeds.map((materialneed) => (
                                        <MenuItem
                                            key={materialneed}
                                            value={materialneed}
                                            style={getStyles(materialneed, selectedMaterialValues, theme)}
                                        >
                                            {materialneed}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </>
                    )}
                </Grid>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="humanresource" checked={isHumanResourceChecked} onChange={(e) => setIsHumanResourceChecked(e.target.checked)} />}
                        label="Human Resource"
                    />
                    {isHumanResourceChecked && (
                        <>
                            <FormControl sx={{ m: 1, width: 300, mt: 3 }}>
                                <Select
                                    multiple
                                    displayEmpty
                                    value={selectedHumanValues}
                                    onChange={handleHumanChange}
                                    input={<OutlinedInput />}
                                    renderValue={(selected) => {
                                        if (selected.length === 0) {
                                            return <em>Choose human resource type</em>;
                                        }
                                        return selected.join(', ');
                                    }}
                                    MenuProps={MenuProps}
                                    inputProps={{ 'aria-label': 'Without label' }}
                                >
                                    <MenuItem disabled value="">
                                        <em>Choose human resource type</em>
                                    </MenuItem>
                                    {humanResources.map((humanResource) => (
                                        <MenuItem
                                            key={humanResource}
                                            value={humanResource}
                                            style={getStyles(humanResource, selectedHumanValues, theme)}
                                        >
                                            {humanResource}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </>
                    )}
                </Grid>
            </Grid>
        </React.Fragment>
    );
}
