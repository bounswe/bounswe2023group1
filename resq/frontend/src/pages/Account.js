import React from 'react';
import { useState} from 'react';
import AccountProfileDetails from '../components/AccountProfileDetails';
import AccountProfile from '../components/AccountProfile';
import { Button, CardActions } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import disasterImage from '../disaster.png';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
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
import { grey } from '@mui/material/colors';
import SendIcon from '@mui/icons-material/Send';
import { useNavigate } from 'react-router-dom';

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

SimpleDialog.propTypes = {
  onClose: PropTypes.func.isRequired,
  open: PropTypes.bool.isRequired,
  selectedValue: PropTypes.string.isRequired,
  requestMade: PropTypes.bool, 
};

const customTheme = createTheme({
  palette: {
    primary: {
      main: '#FF0000',
    },
  },
});

const roles = ['Responder', 'Facilitator', 'Coordinator', 'Admin'];

function SimpleDialog(props) {
  const { onClose, selectedValue, open, requestMade } = props;
  const [selectedRole, setSelectedRole] = React.useState(selectedValue);
  const navigate = useNavigate();

  const handleClose = () => {
    onClose(selectedRole);
    navigate('/rolerequest');
    // navigate(`/rolerequest?selectedRole=${selectedRole}`);
  };

  const handleListItemClick = (role) => {
    if (!requestMade) {
      setSelectedRole(role);
    }
  };

  return (
    <Dialog onClose={handleClose} open={open}>
      <DialogTitle style={{ fontWeight: 'bold' }}>Choose desired role</DialogTitle>
      <List sx={{ pt: 0 }}>
        {roles.map((role) => (
          <ListItem disableGutters key={role}>
            <ListItemButton 
              onClick={() => handleListItemClick(role)}
              selected={role === selectedRole}
              disabled={requestMade} 
            >
              <ListItemAvatar>
                <Avatar sx={{ bgcolor: grey[100], color: grey[600] }}>
                  <PersonIcon />
                </Avatar>
              </ListItemAvatar>
              <ListItemText primary={role} />
            </ListItemButton>
          </ListItem>
        ))}
        <ListItem disableGutters>
          <Button 
            variant="contained" 
            autoFocus
            onClick={() => handleClose()}
            endIcon={<SendIcon />}
            style={{ margin: '0 auto' }}
            disabled={requestMade}
          >
          <ListItemText primary="Request" />
          </Button>
        </ListItem>
      </List>
    </Dialog>
  );
}


function Account() {
  const [open, setOpen] = React.useState(false);
  const [selectedValue, setSelectedValue] = React.useState('');
  const [requestMade, setRequestMade] = useState(false);
  const navigate = useNavigate();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (value) => {
    setOpen(false);
    setSelectedValue(value);
    setRequestMade(true);
  };

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
              <Button
                variant="contained"
                onClick={() => {
                navigate('/map');
              }}
              >Save Details</Button>
            <Button variant="contained" onClick={handleClickOpen}>Request for a Role</Button>
          </CardActions>
          <div>
            <SimpleDialog
              selectedValue={selectedValue}
              open={open}
              onClose={handleClose}
              requestMade={requestMade}
            />
          </div>
        </Box>
      </div>
      <Copyright sx={{ mt: 5 }} />
    </ThemeProvider>
  );
}

export default Account;














