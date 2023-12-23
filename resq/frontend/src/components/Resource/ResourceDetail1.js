import React, { useState, useEffect } from 'react';
import {
    Typography,
    Grid,
    FormControlLabel,
    Checkbox,
    FormControl,
    Autocomplete,
    TextField
} from '@mui/material';
import { useQuery } from "@tanstack/react-query";
import { getCategoryTree } from "../../AppService";

export default function ResourceDetails1({ resourceData, setResourceData }) {
    const [isMaterialResourceChecked, setIsMaterialResourceChecked] = useState(false);
    const [isHumanResourceChecked, setIsHumanResourceChecked] = useState(false);
    const [selectedResource, setSelectedResource] = useState(null);

    const { data: categoryTreeData, isLoading } = useQuery({
        queryKey: ['categoryTree'],
        queryFn: getCategoryTree
    });

    useEffect(() => {
        const description = {
            resourceType: isMaterialResourceChecked ? 'material' : 'human',
            resourceId: selectedResource?.id || ''
        };
        setResourceData({ ...resourceData, ...description });
    }, [isMaterialResourceChecked, isHumanResourceChecked, selectedResource, setResourceData, resourceData]);

    const filterCategoryItems = (type) => {
        return categoryTreeData?.filter(cat => cat.type === type)
            .map(cat => ({ label: cat.data, id: cat.id }))
            .sort((a, b) => a.label.localeCompare(b.label)) || [];
    };

    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Resource Type
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="primary" checked={isMaterialResourceChecked}
                            onChange={(e) => {
                                setIsMaterialResourceChecked(e.target.checked);
                                setIsHumanResourceChecked(!e.target.checked);
                                setSelectedResource(null);
                            }} />}
                        label="Material Resource"
                    />
                    <FormControlLabel
                        control={<Checkbox color="primary" checked={isHumanResourceChecked}
                            onChange={(e) => {
                                setIsHumanResourceChecked(e.target.checked);
                                setIsMaterialResourceChecked(!e.target.checked);
                                setSelectedResource(null);
                            }} />}
                        label="Human Resource"
                    />
                </Grid>
                <Grid item xs={12}>
                    {(isMaterialResourceChecked || isHumanResourceChecked) && (
                        <FormControl sx={{ m: 1, width: 300, mt: 3 }}>
                            <Autocomplete
                                disablePortal
                                id="resource-category-combo-box"
                                options={filterCategoryItems(isMaterialResourceChecked ? 'material' : 'human')}
                                value={selectedResource}
                                onChange={(event, newValue) => {
                                    setSelectedResource(newValue);
                                }}
                                sx={{ width: 300 }}
                                renderInput={(params) => (
                                    <TextField {...params} label={isMaterialResourceChecked ? "Material Resource" : "Human Resource"} />
                                )}
                            />
                        </FormControl>
                    )}
                </Grid>
            </Grid>
        </React.Fragment>
    );
}