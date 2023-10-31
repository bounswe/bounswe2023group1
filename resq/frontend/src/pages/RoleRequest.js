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
import { useState } from 'react';
import ImageUploadComponent from '../components/ImageUpload'
import {useNavigate} from 'react-router-dom';


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


function RoleRequest() {
    const [dragging, setDragging] = useState(false);
    const [droppedImage, setDroppedImage] = useState('');
    const [uploadedId, setUploadedId] = useState('');
    const navigate = useNavigate();
    const [confirmation, setConfirmation] = useState(false);

    const handleConfirm = () => {
      if (droppedImage) {
        alert('APPROVED!');
        navigate('/signin');
      }
    };
    
    
    const handleDragOver = (e) => {
        e.preventDefault();
        setDragging(true);
    };
    
    const handleDragLeave = () => {
        setDragging(false);
    };
    
    const handleDrop = (e) => {
        e.preventDefault();
        setDragging(false);
        const files = e.dataTransfer.files;
        if (files.length > 0) {
        const file = files[0];
        const imageUrl = URL.createObjectURL(file);
        setDroppedImage(imageUrl);
        }
    };
    
    const handleUploadId = (e) => {
        const file = e.target.files[0];
        if (file) {
        try {
            const imageUrl = URL.createObjectURL(file);
            setDroppedImage(imageUrl);
        } catch (error) {
            console.error("Failed to create object URL: " + error.message);
        }
        };
    }

    const customTheme = createTheme({
        palette: {
          primary: {
            main: '#FF0000',
          },
        },
      });
  
      return (
        <ThemeProvider theme={customTheme}>
          <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '1rem' }}>
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
              <ImageUploadComponent
                dragging={dragging}
                droppedImage={droppedImage}
                handleDragOver={handleDragOver}
                handleDragLeave={handleDragLeave}
                handleDrop={handleDrop}
                handleUploadId={handleUploadId}
              />
              <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: '12px' }}>
                <Button
                  variant="contained"
                  onClick={handleConfirm}
                >
                  Confirm
                </Button>
              </div>
              <Copyright sx={{mt: 8}}/>
            </div>
          </Container>
        </ThemeProvider>
      );
    }
    
  export default RoleRequest;
    

  