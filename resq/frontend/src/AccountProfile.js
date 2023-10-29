import React from 'react';
import AccountProfileDetails from './AccountProfileDetails';
import AccountProfile from './AccountProfile';
import { Grid, Box, Button, Card, CardActions, Divider } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import disasterImage from './disaster.png';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import Link from '@mui/material/Link';


const customTheme = createTheme({
  palette: {
    primary: {
      main: '#FF0000',
    },
  },
});

function Account() {
  return (
    <ThemeProvider theme={customTheme}>
      <Container component="main" maxWidth="md">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 1,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ width: 100, height: 100 }}>
            <img
              src={disasterImage}
              alt="Disaster"
              style={{ width: '100%', height: '100%', objectFit: 'cover' }}
            />
          </Avatar>
          <Typography component="h5" variant="h5" sx={{ color: 'red', fontWeight: 'bold' }}>
            ResQ
          </Typography>
          <Box
            sx={{
              display: 'flex',
              width: '100%',
              justifyContent: 'center',
            }}
          >
            <AccountProfile sx={{ margin: '0 10px' }} />
            <AccountProfileDetails sx={{ margin: '0 10px' }} />
          </Box>
        </Box>
        <Card sx={{ width: '100%', maxWidth: '400px' }}>
          <Divider />
          <CardActions sx={{ justifyContent: 'flex-end' }}>
            <Button variant="contained">Save Details</Button>
          </CardActions>
        </Card>
      </Container>
    </ThemeProvider>
  );
}

export default Account;
