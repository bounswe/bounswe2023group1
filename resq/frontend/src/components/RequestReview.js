import React from 'react';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Grid from '@mui/material/Grid';

const primaryNeeds = [
  { name: 'Food', desc: '5 people - Vegetarian, Gluten-Free' },
  { name: 'Water', desc: 'Daily requirement: 15 liters' },
  { name: 'Shelter', desc: 'Tent for 5 people' },
];

const secondaryNeeds = [
  { name: 'Clothing', desc: '3 Women - 2L, 1M; 2 Men - 1L, 1M; 2 Children' },
  { name: 'Medicine', desc: 'Diabetes, Asthma, Heart Disease' },
  { name: 'Heater', desc: '1 Heater needed' },
];

const address = {
  street: 'Kurttepe Caddesi',
  city: 'Cukurova',
  region: 'Adana',
  zip: '01170',
  country: 'Turkey'
};

const recurrentNeeds = [
  { name: 'Food', detail: '4 days' },
  { name: 'Water', detail: '2 days' },
  { name: 'Medicine', detail: '2 weeks' },
];

export default function Review() {
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
        Request Details
      </Typography>
      <Grid container spacing={1}>
        <Grid item xs={12}>
          <Typography variant="subtitle2" gutterBottom>
            Primary Needs
          </Typography>
          {primaryNeeds.map((need, index) => (
            <Typography key={index} variant="body2" gutterBottom>
              {need.name}: {need.desc}
            </Typography>
          ))}
        </Grid>
        <Grid item xs={12}>
          <Typography variant="subtitle2" gutterBottom>
            Secondary Needs
          </Typography>
          {secondaryNeeds.map((need, index) => (
            <Typography key={index} variant="body2" gutterBottom>
              {need.name}: {need.desc}
            </Typography>
          ))}
        </Grid>
      </Grid>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
          <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>
            Address
          </Typography>
          <Typography gutterBottom>{`${address.street}, ${address.city}, ${address.region}, ${address.zip}, ${address.country}`}</Typography>
        </Grid>
        <Grid item container direction="column" xs={12} sm={6}>
          <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>
            Recurrent Needs
          </Typography>
          {recurrentNeeds.map((recurrentNeed, index) => (
            <Typography key={index} gutterBottom>
              {recurrentNeed.name}: {recurrentNeed.detail}
            </Typography>
          ))}
        </Grid>
      </Grid>
    </React.Fragment>
  );
}
