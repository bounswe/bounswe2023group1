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
import AppService from '../AppService';
import { setToken } from '../AuthService';


function Copyright(props) {
  return (
    <div style={{ position: 'fixed', bottom: 0, width: '100%' }}>
      <Typography variant="body2" color="textSecondary" align="center" {...props}>
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

export default function SignIn() {
  const navigate = useNavigate();

  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [signInClicked, setSignInClicked] = React.useState(false);
  const [rememberMeClicked, setRememberMeClicked] = React.useState(false);


  const handleSignUpClick = () => {
    navigate('/signup');
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
  
    if (password.includes(' ') || password.length < 8) {
      alert("Password must be at least 8 characters and cannot contain empty characters!");
    } else {
        if (signInClicked) {
        const loginUserRequest = {
          email,
        };

        try {
          console.log('Sending sign-in request:', loginUserRequest);
          const response = await AppService.signin(loginUserRequest);
          console.log('Sign-in response:', response);

          if (response && response.data && response.data.token) {
            const { token } = response.data;
            setToken(token);
            navigate('/userroles');
          } else {
            alert('Signin failed. Please check your credentials.');
          }
        } catch (error) {
          console.error('Signin error:', error);
          if (error.response) {
            console.error('Response Data:', error.response.data);
          }
          alert('Signin failed. Please check your credentials.');
        }
      }
    }
  };
  

  return (
    <ThemeProvider theme={customTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 1,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
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

          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%' }}>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email"
                name="email"
                autoComplete="email"
                autoFocus
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <FormControlLabel
                control={
                <Checkbox
                  value={rememberMeClicked}
                  onChange={() => setRememberMeClicked(!rememberMeClicked)}
                  color= "error"
                />}
                label="Remember me"
              />

              <Button
                type="submit"
                fullWidth
                variant="contained"
                onClick={() => setSignInClicked(true)} 
                sx={{ mt: 3, mb: 2 }}
              >
                Sign In
              </Button>
              <Grid container>
                <Grid item xs>
                  <Link href="#" variant="body2" sx={{ color: 'red', textDecoration: 'underline' }}>
                    Forgot password?
                  </Link>
                </Grid>
                <Grid item>
                  <Link onClick={handleSignUpClick} variant="body2" sx={{ color: 'red', textDecoration: 'underline' }}>
                    {"Don't have an account? Sign Up"}
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </div>
        </Box>
      </Container>
      <Copyright sx={{ mt: 5 }} />
    </ThemeProvider>
  );
}


