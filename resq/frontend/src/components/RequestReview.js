import React from 'react';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Grid from '@mui/material/Grid';

const needs = [
  { name: 'Food', desc: '5 people - 1 diabetic' },
  { name: 'Water', desc: '5 people - 1 cow' },
  // Only the last 'desc' will be used; if multiple descriptions are needed, consider using an array or separate objects
  { name: 'Clothing', desc: '3 Women - 2L 1M, 1 Man - 1L, 1 Child - 15 yrs girl' },
  { name: 'Medicine', desc: 'Diabetic - 1' },
  { name: 'Shelter', desc: '5 people' },
  { name: 'Animal Feed', desc: '1 cow' },
];

const addresses = ['Kurttepe Caddesi', 'Cukurova', 'Adana', '01170', 'Turkey'];

const recurrentNeeds = [
  { name: 'Food', detail: 'max 4 days' },
  { name: 'Water', detail: 'max 2 days' },
  { name: 'Medicine', detail: 'max 2 weeks' }, // Corrected 'week' to 'weeks'
];

export default function Review() {
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
        Request Details
      </Typography>
      <List disablePadding>
        {needs.map((need) => (
          <ListItem key={need.name} sx={{ py: 1, px: 0 }}>
            <ListItemText primary={need.name} secondary={need.desc} />
            {/* Include any additional information like 'price' here if applicable */}
          </ListItem>
        ))}
      </List>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
          <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>
            Address
          </Typography>
          <Typography gutterBottom>{addresses.join(', ')}</Typography>
        </Grid>
        <Grid item container direction="column" xs={12} sm={6}>
          <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>
            Recurrent Needs
          </Typography>
          <Grid container>
            {recurrentNeeds.map((recurrentNeed) => (
              <React.Fragment key={recurrentNeed.name}>
                <Grid item xs={6}>
                  <Typography gutterBottom>{recurrentNeed.name}</Typography>
                </Grid>
                <Grid item xs={6}>
                  <Typography gutterBottom>{recurrentNeed.detail}</Typography>
                </Grid>
              </React.Fragment>
            ))}
          </Grid>
        </Grid>
      </Grid>
    </React.Fragment >
  );
}