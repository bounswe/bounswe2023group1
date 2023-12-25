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

    const { data: categoryTreeRoot, isLoading } = useQuery({
        queryKey: ['categoryTree'],
        queryFn: getCategoryTree
    });

    useEffect(() => {
        const description = {
            resourceType: isMaterialResourceChecked ? 'material' : isHumanResourceChecked ? 'human' : '',
            resourceId: selectedResource?.id || ''
        };
        setResourceData({ ...resourceData, ...description });
    }, [isMaterialResourceChecked, isHumanResourceChecked, selectedResource, setResourceData]);

    const getCategories = (isHuman) => {
        if (!categoryTreeRoot) return [];

        // Function to recursively get all subcategories of a given category
        const getSubcategories = (node) => {
            let categories = [];
            if (node.children) {
                node.children.forEach(child => {
                    categories.push(child);
                    categories = categories.concat(getSubcategories(child));
                });
            }
            return categories;
        };

        // Find the 'Human Resource' category node
        const humanResourceNode = categoryTreeRoot.children.find(node => node.data === 'Human Resource');

        if (isHuman && humanResourceNode) {
            // Get all subcategories of 'Human Resource'
            return getSubcategories(humanResourceNode).map(cat => ({ label: cat.data, id: cat.id }));
        } else if (!isHuman) {
            // Get all categories except those under 'Human Resource'
            return categoryTreeRoot.children.filter(node => node.data !== 'Human Resource' && node !== humanResourceNode)
                .flatMap(node => getSubcategories(node))
                .map(cat => ({ label: cat.data, id: cat.id }));
        } else {
            return [];
        }
    };

    const materialOptions = getCategories(false);
    const humanOptions = getCategories(true);


    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Resource Quantity and Description
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
                    {isLoading && <Typography>Loading categories...</Typography>}
                    {!isLoading && (isMaterialResourceChecked || isHumanResourceChecked) && (
                        <FormControl fullWidth sx={{ m: 1, mt: 3 }}>
                            <Autocomplete
                                disablePortal
                                id={isMaterialResourceChecked ? "material-resource-autocomplete" : "human-resource-autocomplete"}
                                options={isMaterialResourceChecked ? materialOptions : humanOptions}
                                getOptionLabel={(option) => option.label}
                                value={selectedResource}
                                onChange={(event, newValue) => {
                                    setSelectedResource(newValue);
                                }}
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
