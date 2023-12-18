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
    'Hygiene Products',
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
    const theme = useTheme();
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

    const { data: categoryTreeData, isLoading: isCategoryTreeLoading } = useQuery({
        queryKey: 'categoryTree',
        queryFn: getCategoryTree
    });

    const [isMaterialResourceChecked, setIsMaterialResourceChecked] = useState(false);
    const [isHumanResourceChecked, setIsHumanResourceChecked] = useState(false);

    const [selectedResource, setSelectedResource] = useState('');

    useEffect(() => {
        if (!isMaterialResourceChecked && !isHumanResourceChecked) {
            setSelectedResource('');
        }
        // Update the resourceData only if a resource is selected
        if (selectedResource) {
            const updatedResourceData = { ...resourceData };
            if (isMaterialResourceChecked) {
                updatedResourceData.materialResource = selectedResource;
                delete updatedResourceData.humanResource;
            } else if (isHumanResourceChecked) {
                updatedResourceData.humanResource = selectedResource;
                delete updatedResourceData.materialResource;
            }
            setResourceData(updatedResourceData);
        }
    }, [isMaterialResourceChecked, isHumanResourceChecked, selectedResource, setResourceData, resourceData]);

    const handleResourceChange = (event) => {
        const { value } = event.target;
        setSelectedResource(value);
    };

    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Resource Type
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={
                            <Checkbox
                                color="primary"
                                checked={isMaterialResourceChecked}
                                onChange={(e) => {
                                    setIsMaterialResourceChecked(e.target.checked);
                                    setIsHumanResourceChecked(false);
                                }}
                            />
                        }
                        label="Material Resource"
                    />
                    <FormControlLabel
                        control={
                            <Checkbox
                                color="primary"
                                checked={isHumanResourceChecked}
                                onChange={(e) => {
                                    setIsHumanResourceChecked(e.target.checked);
                                    setIsMaterialResourceChecked(false);
                                }}
                            />
                        }
                        label="Human Resource"
                    />
                </Grid>
                <Grid item xs={12}>
                    {isMaterialResourceChecked && (
                        <FormControl fullWidth sx={{ m: 1, mt: 3 }}>
                            <InputLabel id="material-resource-select-label">Material Resource</InputLabel>
                            <Select
                                labelId="material-resource-select-label"
                                id="material-resource-select"
                                value={selectedResource}
                                onChange={handleResourceChange}
                                input={<OutlinedInput label="Material Resource" />}
                                MenuProps={MenuProps}
                            >
                                {!isCategoryTreeLoading && categoryTreeData.map((materialResource) => (
                                    <MenuItem
                                        key={materialResource.id}
                                        value={materialResource.id}
                                    >
                                        {materialResource.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    )}
                    {isHumanResourceChecked && (
                        <FormControl fullWidth sx={{ m: 1, mt: 3 }}>
                            <InputLabel id="human-resource-select-label">Human Resource</InputLabel>
                            <Select
                                labelId="human-resource-select-label"
                                id="human-resource-select"
                                value={selectedResource}
                                onChange={handleResourceChange}
                                input={<OutlinedInput label="Human Resource" />}
                                MenuProps={MenuProps}
                            >
                                {humanResources.map((humanResource) => (
                                    <MenuItem
                                        key={humanResource}
                                        value={humanResource}
                                    >
                                        {humanResource}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    )}
                </Grid>
            </Grid>
        </React.Fragment>
    );
}
