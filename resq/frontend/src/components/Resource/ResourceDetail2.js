import React, { useState } from 'react';
import { Typography, Grid, TextField } from '@mui/material';
import { useResource } from './ResourceContext';
import { useContext } from 'react';
import { ResourceContext } from './ResourceContext';

export default function ResourceDetail2() {
    const { resourceData, setResourceData } = useContext(ResourceContext);

    const { updateResourceData } = useResource();

    const [materialQuantity, setMaterialQuantity] = useState('');
    const [materialDescription, setMaterialDescription] = useState('');

    const handleQuantityChange = (quantityValue) => {
        updateResourceData({
            ...resourceData,
            quantity: parseInt(quantityValue),
            description: materialDescription,
        });
    };

    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Resource Type
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <TextField
                        label="Resource Quantity"
                        variant="outlined"
                        fullWidth
                        value={materialQuantity}
                        onChange={(event) => {
                            setMaterialQuantity(event.target.value);
                            handleQuantityChange(event.target.value);
                        }}
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Resource Description"
                        variant="outlined"
                        fullWidth
                        value={materialDescription}
                        onChange={(event) => setMaterialDescription(event.target.value)}
                    />
                </Grid>
            </Grid>
        </React.Fragment>
    );
}
