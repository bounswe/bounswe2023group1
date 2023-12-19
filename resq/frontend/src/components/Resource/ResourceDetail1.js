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

    const { data: categoryTreeData, isLoading: isLoadingCategoryTree } = useQuery({
        queryKey: ['categoryTree'],
        queryFn: getCategoryTree
    });

    const [isMaterialResourceChecked, setIsMaterialResourceChecked] = useState(false);
    const [isHumanResourceChecked, setIsHumanResourceChecked] = useState(false);

    const [selectedResource, setSelectedResource] = useState('');

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
                        control={
                            <Checkbox
                                color="primary"
                                checked={isMaterialResourceChecked}
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
                        control={
                            <Checkbox
                                color="primary"
                                checked={isHumanResourceChecked}
                                onChange={(e) => {
                                    setIsHumanResourceChecked(e.target.checked);
                                    setIsMaterialResourceChecked(!e.target.checked);
                                }}
                            />
                        }
                        label="Human Resource"
                    />
                    {isHumanResourceChecked && (
                        <FormControl fullWidth sx={{ m: 1, mt: 3 }}>
                            <InputLabel id="human-resource-select-label">Human Resource</InputLabel>
                            <Select
                                labelId="human-resource-select-label"
                                value={selectedResource}
                                onChange={handleResourceChange}
                                input={<OutlinedInput label="Human Resource" />}
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
            </Grid>
        </React.Fragment>
    );
}
