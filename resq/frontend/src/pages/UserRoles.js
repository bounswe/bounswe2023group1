import * as React from 'react';
import { Link as RouterLink, useHistory } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import disasterImage from '../disaster.png';
import Container from '@mui/material/Container';
import { useNavigate } from 'react-router-dom';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup'; 



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


export default function SignIn(){
  const [selectedRole, setSelectedRole] = React.useState(''); 

  const handleRoleChange = (event) => {
    setSelectedRole(event.target.value); 
  };

  const navigate = useNavigate();

  const handleUserRoles = () => {
    navigate('/userroles'); 
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      userroles: data.get('userroles'),
    });
  };

  return (
    <ThemeProvider theme={customTheme}>
      <Container component="main" maxWidth="xs" >
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
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
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <RadioGroup value={selectedRole} onChange={handleRoleChange}>
          <Grid item xs={12}>
                <FormControlLabel
                label="Victim"
                value="victim"
                control={<Radio color="error" />}
                />
            </Grid>
            <Grid item xs={12}>
                <FormControlLabel
                label="Responder"
                value="responder"
                control={<Radio color="error" />}
                />
            </Grid>
            <Grid item xs={12}>
                <FormControlLabel
                label="Facilitator"
                value="facilitator"
                control={<Radio color="error" />}
                />
            </Grid>
            <Grid item xs={12}>
                <FormControlLabel
                label="Coordinator"
                value="coordinator"
                control={<Radio color="error" />}
                />
            </Grid>
            <Grid item xs={12}>
                <FormControlLabel
                label="Admin"
                value="admin"
                control={<Radio color="error" />}
                />
            </Grid>
            </RadioGroup>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2}}
            >
              Continue with this user role
            </Button>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  );
}


