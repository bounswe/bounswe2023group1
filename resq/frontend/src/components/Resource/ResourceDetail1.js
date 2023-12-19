import React, { useState, useEffect } from 'react';
import {
    Typography,
    Grid,
    FormControlLabel,
    Checkbox,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    OutlinedInput
} from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { useQuery } from "@tanstack/react-query";
import { getCategoryTree } from "../../AppService";

const materialResources = [
    'Food',
    'Diapers',
    'Hygene Products',
    'Water',
    'Shelter',
    'Tent',
    'Clothing',
];
const humanResources = [
    'Doctor',
    'Nurse',
    'Translator',
    'Rescue Team',
    'Lorry Driver',
    'Food Service',
    'District Responsible',
];

export default function ResourceDetails1({ resourceData, setResourceData }) {

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

    const { data: categoryTreeData, isLoading: isLoadingCategoryTree } = useQuery({
        queryKey: ['categoryTree'],
        queryFn: getCategoryTree
    });
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
        if (!isMaterialResourceChecked && !isHumanResourceChecked) {
            setSelectedResource('');
        }

        const updatedResourceData = { ...resourceData };
        if (isMaterialResourceChecked || isHumanResourceChecked) {
            updatedResourceData.resourceType = selectedResource;
        }
        setResourceData(updatedResourceData);
    }, [isMaterialResourceChecked, isHumanResourceChecked, selectedResource, setResourceData, resourceData]);

    const handleResourceChange = (event) => {
        setSelectedResource(event.target.value);
    };

    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Resource Type
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="materialresource" checked={isMaterialResourceChecked}
                            onChange={(e) => {
                                setIsMaterialResourceChecked(e.target.checked);
                                setIsHumanResourceChecked(!e.target.checked);
                            }}
                        />
                        }
                        label="Material Resource"
                    />
                    {isMaterialResourceChecked && (
                        <FormControl fullWidth sx={{ m: 1, mt: 3 }}>
                            <InputLabel id="material-resource-select-label">Material Resource</InputLabel>
                            <Select
                                labelId="material-resource-select-label"
                                value={selectedResource}
                                onChange={handleResourceChange}
                                input={<OutlinedInput label="Material Resource" />}
                                MenuProps={MenuProps}
                            >
                                {!isLoadingCategoryTree && categoryTreeData?.map((resource) => (
                                    <MenuItem key={resource.id} value={resource.id}>
                                        {resource.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    )}
                </Grid>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" name="humanresource" checked={isHumanResourceChecked}
                            onChange={(e) => setIsHumanResourceChecked(e.target.checked)} />}
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
        </React.Fragment >
    );
}
