import React from 'react';
import AccountProfileDetails from './AccountProfileDetails';
import AccountProfile from './AccountProfile';
import { Grid, Card, Button, CardActions, Divider } from '@mui/material'; // Correct import paths
import { createTheme, ThemeProvider } from '@mui/material/styles';
import disasterImage from './disaster.png';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import Link from '@mui/material/Link';



function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://github.com/bounswe/bounswe2023group1">
        ResQ
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

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
            <Button variant="contained" color="primary">Save Details</Button>
          </CardActions>
          <Copyright sx={{ mt: 5 }} />
        </Card>
      </Container>
    </ThemeProvider>
  );
}

export default Account;
