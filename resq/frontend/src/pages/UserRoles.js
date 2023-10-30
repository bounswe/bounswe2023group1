import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import FormControlLabel from '@mui/material/FormControlLabel';
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import disasterImage from '../disaster.png';
import Container from '@mui/material/Container';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import { useNavigate } from 'react-router-dom';


function Copyright(props) {
    return (
        <div style={{position: 'fixed', bottom: 0, width: '100%'}}>
            <Typography variant="body2" color="text.secondary" align="center" {...props}>
                {'Copyright © '}
                <Link color="inherit" href="https://github.com/bounswe/bounswe2023group1">
                    <span style={{fontWeight: 'bold'}}>ResQ</span>
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
    
    const [selectedRole, setSelectedRole] = React.useState('');

    const handleRoleChange = (event) => {
        setSelectedRole(event.target.value);
    };

    const navigate = useNavigate();
    const handleSubmit = (event) => {
        event.preventDefault();
        navigate('/map'); 
       // const data = new FormData(event.currentTarget);
    };

    return (
        <ThemeProvider theme={customTheme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Box
                    sx={{
                        marginTop: 1,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{width: 80, height: 80, marginBottom: '10px'}}>
                        <img
                            src={disasterImage}
                            alt="Disaster"
                            style={{width: '100%', height: '100%', objectFit: 'cover'}}
                        />
                    </Avatar>
                    <Typography component="h5" variant="h5" sx={{color: 'red', fontWeight: 'bold', margin: '0'}}>
                        ResQ
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                        <RadioGroup value={selectedRole} onChange={handleRoleChange}>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    label="Victim"
                                    value="victim"
                                    control={<Radio color="error"/>}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    label="Responder"
                                    value="responder"
                                    control={<Radio color="error"/>}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    label="Facilitator"
                                    value="facilitator"
                                    control={<Radio color="error"/>}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    label="Coordinator"
                                    value="coordinator"
                                    control={<Radio color="error"/>}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    label="Admin"
                                    value="admin"
                                    control={<Radio color="error"/>}
                                />
                            </Grid>
                        </RadioGroup>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2}}
                        >
                            Continue with this user role
                        </Button>
                    </Box>
                </Box>
            </Container>
            <Copyright sx={{mt: 5}}/>
        </ThemeProvider>
    );
}

