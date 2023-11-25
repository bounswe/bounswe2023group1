import * as React from 'react';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';

export default function PaymentForm() {
    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Request Details
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <FormControlLabel
                        control={<Checkbox color="secondary" name="water" value="yes" />}
                        label="Needed Water"
                    />
                </Grid>
                <Grid item xs={12} md={6}>
                    <FormControlLabel
                        control={<Checkbox color="secondary" name="food" value="yes" />}
                        label="Needed Food"
                    />
                </Grid>
                <Grid item xs={12} md={6}>
                    <FormControlLabel
                        control={<Checkbox color="secondary" name="child" value="yes" />}
                        label="Any Child"
                    />
                </Grid>
                <Grid item xs={12} md={6}>
                    <FormControlLabel
                        control={<Checkbox color="secondary" name="illness" value="yes" />}
                        label="Any people with illness"
                    />
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField
                        required
                        id="people count"
                        label="People Count"
                        fullWidth
                        autoComplete="people count"
                        variant="standard"
                    />
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField
                        required
                        id="Additional requests"
                        label="additional requests"
                        fullWidth
                        autoComplete="additional requests"
                        variant="standard"
                    />
                </Grid>
            </Grid>
        </React.Fragment>
    );
}