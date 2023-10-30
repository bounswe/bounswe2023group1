import React from 'react';
import AccountProfileDetails from './AccountProfileDetails';
import AccountProfile from './AccountProfile';
import { Grid, Card, Button, CardActions, Divider } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import disasterImage from './disaster.png';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import Link from '@mui/material/Link';
import PropTypes from 'prop-types';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import PersonIcon from '@mui/icons-material/Person';
import AddIcon from '@mui/icons-material/Add';
import { red } from '@mui/material/colors';

function Copyright(props) {
  return (
    <div style={{ position: 'fixed', bottom: 0, width: '100%' }}>
      <Typography variant="body2" color="text.secondary" align="center" {...props}>
        {'Copyright © '}
        <Link color="inherit" href="https://github.com/bounswe/bounswe2023group1">
          <span style={{ fontWeight: 'bold' }}>ResQ</span>
        </Link>{' '}
        {new Date().getFullYear()}
        {'.'}
      </Typography>
    </div>
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
      <div style={{ height: '100vh', overflow: 'hidden' }}>
        <CssBaseline />
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            padding: '20px', 
          }}
        >
          <Avatar sx={{ width: 80, height: 80, marginBottom: '10px' }}>
            <img
              src={disasterImage}
              alt="Disaster"
              style={{ width: '100%', height: '100%', objectFit: 'cover' }}
            />
          </Avatar>
          <Typography component="h5" variant="h5" sx={{ color: 'red', fontWeight: 'bold', margin: '0' }}>
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
          <CardActions sx={{ justifyContent: 'space-between' }}>
            <Button variant="contained">Request for a Role</Button>
            <Button variant="contained" color="primary">Save Details</Button>
          </CardActions>
        </Box>
      </div>
      <Copyright sx={{ mt: 5 }} />
    </ThemeProvider>
  );
}

export default Account;

