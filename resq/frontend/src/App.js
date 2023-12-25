import React, {useEffect, useRef, useState} from 'react';
import {BrowserRouter as Router, Routes, Route, useNavigate} from "react-router-dom";
import {Navbar, Container, Nav} from 'react-bootstrap';
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import Account from "./pages/Account";
import RoleRequest from "./pages/RoleRequest";
import LogoutIcon from '@mui/icons-material/Logout';
import NotificationsIcon from '@mui/icons-material/Notifications';
import Request from "./components/Request/RequestCreation";
import Resource from "./components/Resource/ResourceCreation";
import {LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import axios from "axios";
import VictimMapPage from "./pages/VictimMapPage";
import FacilitatorMapPage from './pages/FacilitatorMapPage';
import { Badge } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Notifications from "./pages/Notifications";
import { getUserInfo } from './AppService';
import TaskSelectPage from "./pages/TaskSelectPage";
import CreateActionPage from "./pages/CreateActionPage";

const SmallRedCircle = () =>
    <svg
        xmlns="http://www.w3.org/2000/svg"
        width="20"
        height="20"
        viewBox="0 0 20 20"
    >
        <circle cx="10" cy="10" r="8" fill="red"/>
    </svg>

const queryClient = new QueryClient()

function SignOut({setToken, setRole}) {
    const navigate = useNavigate();
    const signOut = () => {
        setToken(null)
        setRole("");
        navigate('/');
    }

    return <Nav.Link key={"signout"} href={"#"} onClick={signOut}
                     style={{"marginLeft": "auto"}}>
        <LogoutIcon/>
    </Nav.Link>
}

function App() {
    const [token, _setToken] = useState(localStorage.getItem("token"))
    const [uid, _setUid] = useState(parseInt(localStorage.getItem("uid")) || -1)
    const [roles, setRoles] = useState([])
    const [height, setHeight] = useState(window.innerHeight);

    const updateDimensions = () => {
        setHeight(window.innerHeight);
    }
    useEffect(() => {
        window.addEventListener("resize", updateDimensions);
        return () => window.removeEventListener("resize", updateDimensions);
    }, []);

    useEffect(() => {
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        if (token) {
            const userId = localStorage.getItem("userId");
            getUserInfo(userId).then(userInfo => {
                setRoles(userInfo.roles);
            }).catch(error => console.error('Error fetching user info:', error));
        }
    }, [token]);



    const [notifications, setNotifications] = useState([
        {
            title: "Ongoing fire on Cami Sk.",
            desc: "A fire has been reported on Rumeli Hisarı Mh, Cami Sk, Sarıyer/İstanbul.",
            read: false
        }, {
            title: "New Soup Kitchen Near You",
            desc: "A soup kitchen has opened on Etiler, Hisar Üstü Nispetiye Cd No:1, Beşiktaş/İstanbul.",
            read: true
        }
    ])
    const setToken = t => {
        localStorage.setItem("token", t || "")
        _setToken(t)
    }
    const setUid = t => {
        localStorage.setItem("uid", (t || -1) + "")
        _setUid(t)
    }

    const navLinks = [
        { path: '/', label: <strong>Victim Map</strong>, component: VictimMapPage, icon: <SmallRedCircle />, roles: ['VICTIM', 'ADMIN', 'RESPONDER', 'FACILITATOR'] },
        { path: '/tasks', label: <strong>View Tasks</strong>, component: TaskSelectPage, icon: <SmallRedCircle />, roles: ['RESPONDER', 'ADMIN'] },
        { path: '/createtasks', label: <strong>Create Tasks</strong>, component: CreateActionPage, icon: <SmallRedCircle />, roles: ['COORDINATOR', 'ADMIN'] },
        { path: '/facilitatormap', label: <strong>Facilitator Map</strong>, component: FacilitatorMapPage, icon: <SmallRedCircle />, roles: ['FACILITATOR', 'ADMIN'] },
    ];

    const filteredNavLinks = navLinks.filter(link => !roles.every(role=>!link.roles.includes(role)));


    const ref = useRef(null)

    const theme = createTheme({
        palette: {
            primary: {
                main: "#ff0000"
            },
            secondary: {
                main: '#00f0ff'
            }
        }
    });

    return (
        <QueryClientProvider client={queryClient}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <ThemeProvider theme={theme}>
                    <Router>
                        <div>
                            <Navbar bg="light" variant="light" expand="lg">
                                <Container ref={ref}>
                                    <Navbar.Brand href="/" style={{color: 'red', fontWeight: 'bold'}}>
                                        <SmallRedCircle/>
                                        ResQ
                                    </Navbar.Brand>
                                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                                    <Navbar.Collapse id="basic-navbar-nav">
                                        <Nav className="me-auto">
                                            {filteredNavLinks.map(({ path, label, icon }) => (
                                                <Nav.Link key={path} href={path}>{icon}{label}</Nav.Link>
                                            ))}
                                        </Nav>
                                        <Nav className="ml-auto">
                                            {token ?
                                                <>
                                                    <Nav.Link key={"/account"} href={"/account"}
                                                              style={{"marginLeft": "auto"}}>
                                                        <SmallRedCircle/>
                                                        <strong>Account</strong>
                                                    </Nav.Link>
                                                    <Nav.Link key={"/requestcreation"} href={"/requestcreation"}
                                                              style={{"marginLeft": "auto"}}>
                                                        <SmallRedCircle/>
                                                        <strong>Create Request</strong>
                                                    </Nav.Link>
                                                    <Nav.Link key={"/resourcecreation"} href={"/resourcecreation"}
                                                              style={{"marginLeft": "auto"}}>
                                                        <SmallRedCircle/>
                                                        <strong>Create Resource</strong>
                                                    </Nav.Link>
                                                    <Nav.Link key={"/notifications"} href={"/notifications"}
                                                              style={{"marginLeft": "auto"}}>
                                                        <Badge badgeContent={notifications.filter(n => !n?.read).length}
                                                               color={"primary"}>
                                                            <NotificationsIcon/>
                                                        </Badge>
                                                    </Nav.Link>
                                                    <SignOut setToken={setToken} setRole={setRoles}/>
                                                </> :
                                                <>
                                                    <Nav.Link key={'/signin'} href={'/signin'}
                                                              style={{"marginLeft": "auto"}}>
                                                        <strong>Sign In</strong>
                                                    </Nav.Link>
                                                    <Nav.Link key={'/signup'} href={'/signup'}
                                                              style={{"marginLeft": "auto"}}>
                                                        <strong>Sign Up</strong>
                                                    </Nav.Link>
                                                </>
                                            }
                                        </Nav>
                                    </Navbar.Collapse>
                                </Container>
                            </Navbar>

                            <main style={{height: `${height - 57}px`}}>
                                <Routes>
                                    {filteredNavLinks.map(({ path, component }) => (
                                        <Route key={path} path={path}
                                            element={React.createElement(component, { token, setToken, role: roles, setRole: setRoles, uid })} />
                                    ))}
                                    <Route path="/rolerequest" state={{token, setToken}}
                                           element={React.createElement(RoleRequest, {token, setToken})}/>
                                    {
                                        token ? <>
                                                <Route path="/account" state={{token, setToken}}
                                                       element={React.createElement(Account, {token, setToken})}/>
                                                <Route path="/requestcreation" state={{token, setToken}}
                                                       element={React.createElement(Request, {token, setToken})}/>
                                                <Route path="/notifications" state={{token, setToken}}
                                                       element={React.createElement(Notifications, {
                                                           token,
                                                           notifications,
                                                           setNotifications
                                                       })}/>
                                                <Route path="/resourcecreation" state={{token, setToken}}
                                                       element={React.createElement(Resource, {token, setToken})}/>
                                            </>
                                            : <>
                                                <Route path="/signin" state={{token, setToken}}
                                                       element={React.createElement(SignIn, {token, setToken, setUid})}/>
                                                <Route path="/signup" state={{token, setToken}}
                                                       element={React.createElement(SignUp, {token, setToken})}/>
                                            </>
                                    }
                                </Routes>
                            </main>
                        </div>
                    </Router>
                </ThemeProvider>
            </LocalizationProvider>
        </QueryClientProvider>);
}

export default App;