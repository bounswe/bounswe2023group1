import React, { useState } from 'react';
import { Typography, Grid, FormControlLabel, Checkbox, TextField } from '@mui/material';
import { useResource } from './ResourceContext';

export default function ResourceDetail2() {
    const { updateResourceData } = useResource();
    const [isMaterialResourceChecked, setIsMaterialResourceChecked] = useState(false);
    const [isHumanResourceChecked, setIsHumanResourceChecked] = useState(false);

    const [materialQuantity, setMaterialQuantity] = useState('');
    const [materialDescription, setMaterialDescription] = useState('');

    const [humanQuantity, setHumanQuantity] = useState('');
    const [humanDescription, setHumanDescription] = useState('');

    const handleMaterialCheckboxChange = (event) => {
        setIsMaterialResourceChecked(event.target.checked);
    };

    const handleHumanCheckboxChange = (event) => {
        setIsHumanResourceChecked(event.target.checked);
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
        </React.Fragment>
    );
}
