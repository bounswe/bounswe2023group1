import React, {useState, useEffect} from 'react';
import {
    Typography,
    Grid,
    FormControlLabel,
    Checkbox,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    OutlinedInput,
    Autocomplete, TextField
} from '@mui/material';
import {createTheme} from '@mui/material/styles';
import {Theme, useTheme} from '@mui/material/styles';
import {useResource} from './ResourceContext';
import {useContext} from 'react';
import {ResourceContext} from './ResourceContext';
import {useQuery} from "@tanstack/react-query";
import {getCategoryTree} from "../../AppService";

const humanResources = [
    'Doctor',
    'Nurse',
    'Translator',
    'Rescue Team',
    'Lorry Driver',
    'Food Service',
    'District Responsible',
];

export default function ResourceDetails1({resourceData, setResourceData}) {

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

    const categoryTree = useQuery({queryKey: ['categoryTree'], queryFn: getCategoryTree})

    const [isMaterialResourceChecked, setIsMaterialResourceChecked] = useState(false);
    const [isHumanResourceChecked, setIsHumanResourceChecked] = useState(false);

    const [selectedMaterialValue, setSelectedMaterial] = useState(null);
    const [selectedHumanValues, setSelectedHumanValues] = useState([]);

    const getStyles = (item, selectedItems, theme) => {
        return {
            fontWeight:
                selectedItems.indexOf(item) === -1
                    ? theme.typography.fontWeightRegular
                    : theme.typography.fontWeightMedium,
        };
    };

    const handleHumanChange = (event) => {
        setSelectedHumanValues(event.target.value);
    };

    const theme = useTheme();

    useEffect(() => {
        const description = {
            categoryTreeId: (isMaterialResourceChecked && selectedMaterialValue?.id) || '',
            //human: isHumanResourceChecked ? selectedHumanValues.join(', ') : '',
        };
        setResourceData({...resourceData, ...description});
    }, [isMaterialResourceChecked, selectedMaterialValue, setResourceData, resourceData]);

    const comboBoxItems = (categoryTree.data?.getLeafCategories() || [])
        .map(cat => ({label: cat.data, id: cat.id}))
        .sort((a, b) => {
            if (a.label === b.label) return 0;
            return a.label > b.label ? 1 : -1;
        });

    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Resource Type
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="materialresource" checked={isMaterialResourceChecked}
                                           onChange={(e) => setIsMaterialResourceChecked(e.target.checked)}/>}
                        label="Material Resource"
                    />
                    {isMaterialResourceChecked && (
                        <>
                            <FormControl sx={{m: 1, width: 300, mt: 3}}>
                                <Autocomplete
                                    disablePortal
                                    id="combo-box-demo"
                                    options={comboBoxItems}
                                    value={selectedMaterialValue}
                                    onChange={(event, newValue) => {
                                        setSelectedMaterial(newValue);
                                    }}
                                    sx={{width: 300}}
                                    renderInput={(params) => <TextField {...params} label="Movie"/>}
                                />
                            </FormControl>
                        </>
                    )}
                </Grid>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="humanresource" checked={isHumanResourceChecked}
                                           onChange={(e) => setIsHumanResourceChecked(e.target.checked)}/>}
                        label="Human Resource"
                    />
                    {isHumanResourceChecked && (
                        <>
                            <FormControl sx={{m: 1, width: 300, mt: 3}}>
                                <Select
                                    multiple
                                    displayEmpty
                                    value={selectedHumanValues}
                                    onChange={handleHumanChange}
                                    input={<OutlinedInput/>}
                                    renderValue={(selected) => {
                                        if (selected.length === 0) {
                                            return <em>Choose human resource type</em>;
                                        }
                                        return selected.join(', ');
                                    }}
                                    MenuProps={MenuProps}
                                    inputProps={{'aria-label': 'Without label'}}
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
