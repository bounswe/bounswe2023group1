import React, { useCallback, useState } from 'react';
import {
  Avatar,
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Divider,
  Typography,
  TextField,
  Grid,
} from '@mui/material';

const user = {
  avatar: 'avatar.png',
  city: 'Izmir',
  country: 'Turkey',
  role: 'victim',
  name: 'Sude Demir',
  bloodType: '0 RH +',
  gender: 'Woman',
};

const bloodTypes = [
  {
    value: '0 RH -',
    label: '0 RH -',
    },
  {
    value: '0 RH +',
    label: '0 RH +',
  },
  {
    value: 'A RH -',
    label: 'A RH -',
  },
  {
    value: 'A RH +',
    label: 'A RH +',
  },
  {
    value: 'B RH -',
    label: 'B RH -',
  },
  {
    value: 'B RH +',
    label: 'B RH +',
  },
];

const gender = [
  {
    value: 'Woman',
    label: 'Woman',
  },
  {
    value: 'Man',
    label: 'Man',
  },
  {
    value: 'Genderqueer/Non-Binary',
    label: 'Genderqueer/Non-Binary',
  },
  {
    value: 'Other',
    label: 'Other',
  },
];

const years = Array.from({ length: 100 }, (_, i) => String(new Date().getFullYear() - i));
const months = Array.from({ length: 12 }, (_, i) => String(i + 1).padStart(2, '0'));
const days = Array.from({ length: 31 }, (_, i) => String(i + 1).padStart(2, '0'));

function AccountProfile() {
  const [values, setValues] = useState({
    bloodType: '0 RH +',
    gender: 'Woman',
    year: '1999',
    month: '08',
    day: '17',
});

  const handleChange = useCallback((event) => {
    setValues((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  }, [setValues]);

  return (
    <div>
      <Card sx={{ width: 300, height: 457 }}>
        <CardContent>
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <Avatar src={user.avatar} sx={{ width: 100, height: 100, mb: 2 }} />
          <Typography gutterBottom variant="h5">
            {user.name}
          </Typography>
        </Box>
        </CardContent>
          <Divider />
        <CardActions>
          <TextField
            fullWidth
            sx={{ marginBottom: 2 }}
            label="Select Blood Type"
            name="bloodType"
            onChange={handleChange}
            required
            select
            SelectProps={{ native: true }}
            value={values.bloodType}
          >
            {bloodTypes.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </TextField>
        </CardActions>
        <CardActions>
          <TextField
            fullWidth
            sx={{ marginBottom: 2 }}
            label="Select Gender"
            name="gender"
            onChange={handleChange}
            required
            select
            SelectProps={{ native: true }}
            value={values.gender}
          >
            {gender.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </TextField>
        </CardActions>
        <CardActions>
          <Grid container spacing={2}>
            <Grid item xs={4}> 
              <TextField
                fullWidth
                sx={{ marginBottom: 2 }}
                label="Year"
                name="year"
                onChange={handleChange}
                required
                select
                SelectProps={{ native: true }}
                value={values.year}
              >
                {years.map((option) => (
                  <option key={option} value={option}>
                    {option}
                  </option>
                ))}
              </TextField>
            </Grid>
            <Grid item xs={4}>
              <TextField
                fullWidth
                sx={{ marginBottom: 2 }}
                label="Month"
                name="month"
                onChange={handleChange}
                required
                select
                SelectProps={{ native: true }}
                value={values.month}
              >
                {months.map((option) => (
                  <option key={option} value={option}>
                    {option}
                  </option>
                ))}
              </TextField>
            </Grid>
            <Grid item xs={4}>
              <TextField
                fullWidth
                sx={{ marginBottom: 2 }}
                label="Day"
                name="day"
                onChange={handleChange}
                required
                select
                SelectProps={{ native: true }}
                value={values.day}
              >
                {days.map((option) => (
                  <option key={option} value={option}>
                    {option}
                  </option>
                ))}
              </TextField>
            </Grid>
          </Grid>
          </CardActions>
        </Card>
    </div>
  );
}

export default AccountProfile;
