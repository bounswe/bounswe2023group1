import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import {Card, CardContent, CardHeader} from "@mui/material";


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

const NotificationCard = ({notif, onClick}) => <Card
    sx={{marginBottom: "24px", backgroundColor: notif.read ? "lightgrey" : "white"}}
    onClick={onClick}
>
    <CardHeader title={notif.title}/>
    <CardContent>
        <Typography variant="body2" color="text.primary" sx={{fontSize: '16px'}}>
            {notif.desc}
        </Typography>
    </CardContent>
</Card>

// noinspection JSUnusedLocalSymbols
export default function Notifications({token, notifications, setNotifications}) {

    const handleNotifRead = (i) => {
        const new_notifs = JSON.parse(JSON.stringify(notifications))
        new_notifs[i].read = true
        setNotifications(new_notifs)
    }

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
                    {
                        notifications.map((notif, i) => <NotificationCard notif={notif} onClick={() => notif.read || handleNotifRead(i)}/>)
                    }

                </Box>
            </Container>
            <Copyright sx={{mt: 5}}/>
        </ThemeProvider>
    );
}

