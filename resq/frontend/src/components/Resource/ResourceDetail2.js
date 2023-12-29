import React, { useState, useEffect } from 'react';
import { Typography, Grid, TextField } from '@mui/material';
import { ResourceContext } from './ResourceContext';
import { useContext } from 'react';

export default function ResourceDetail2({ resourceData, setResourceData }) {

    const ITEM_HEIGHT = 48;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
        PaperProps: {
            style: {
                maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP, width: 250,
            },
        },
    };

    const [materialQuantity, setMaterialQuantity] = useState('');
    const [materialDescription, setMaterialDescription] = useState('');

    useEffect(() => {
        setResourceData({ ...resourceData, quantity: materialQuantity });
    }, [materialQuantity, resourceData, setResourceData]);

    return (<React.Fragment>
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
                    onChange={(event) => setMaterialQuantity(event.target.value)}
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
    </React.Fragment>);
}
