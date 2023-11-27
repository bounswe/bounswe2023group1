import React, {useEffect, useRef, useState} from 'react';
import {BrowserRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import {Navbar, Container, Nav} from 'react-bootstrap';
import UserRoles from "./pages/UserRoles";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import MapPage from "./pages/MapPage";
import Account from "./pages/Account";
import RoleRequest from "./pages/RoleRequest";
import LogoutIcon from '@mui/icons-material/Logout';
import Request from "./pages/RequestCreation";
import {LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import axios from "axios";
import VictimMapPage from "./pages/VictimMapPage";

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

function App() {
    const [token, _setToken] = useState(localStorage.getItem("token"))
    const [role, setRole] = useState("")
    const [height, setHeight] = useState(window.innerHeight);
    const updateDimensions = () => {
        setHeight(window.innerHeight);
    }
    useEffect(() => {
        window.addEventListener("resize", updateDimensions);
        return () => window.removeEventListener("resize", updateDimensions);
    }, []);

    useEffect(() => {
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    }, [token])

    const setToken = t => {
        localStorage.setItem("token", t || "")
        _setToken(t)
    }

    const navLinks = [
        {path: '/map', label: <strong>Map Demo</strong>, component: VictimMapPage, icon: <SmallRedCircle/>},
        token && {
            path: '/userroles',
            label: <strong>User Roles</strong>,
            component: UserRoles,
            icon: <SmallRedCircle/>
        },
        (role === "responder") && {
            path: '/responder',
            label: <strong>Responder Panel</strong>,
            component: <div>Responder Panel</div>,
            icon: <SmallRedCircle/>
        },
    ].filter(l => !!l);

    const signOut = () => {
        setToken(null)
    }

    const ref = useRef(null)

    return (
        <QueryClientProvider client={queryClient}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
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
                                        {navLinks.map(({path, label, icon}) => (
                                            <Nav.Link key={path} href={path}>
                                                {icon}
                                                {label}
                                            </Nav.Link>
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
                                                <Nav.Link key={"signout"} href={"#"} onClick={signOut}
                                                          style={{"marginLeft": "auto"}}>
                                                    <LogoutIcon/>
                                                </Nav.Link>
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
                                {navLinks.map(({path, component}) => (
                                    <Route key={path} path={path}
                                           element={React.createElement(component, {token, setToken, role, setRole})}/>
                                ))}
                                <Route path="/" element={<Navigate to="/map"/>}/>
                                <Route path="/rolerequest" state={{token, setToken}}
                                       element={React.createElement(RoleRequest, {token, setToken})}/>
                                {
                                    token ? <>
                                            <Route path="/account" state={{token, setToken}}
                                                   element={React.createElement(Account, {token, setToken})}/>
                                            <Route path="/requestcreation" state={{token, setToken}}
                                                   element={React.createElement(Request, {token, setToken})}/>
                                        </>
                                        : <>
                                            <Route path="/signin" state={{token, setToken}}
                                                   element={React.createElement(SignIn, {token, setToken})}/>
                                            <Route path="/signup" state={{token, setToken}}
                                                   element={React.createElement(SignUp, {token, setToken})}/>
                                        </>
                                }
                            </Routes>
                        </main>
                    </div>
                </Router>
            </LocalizationProvider>
        </QueryClientProvider>
    );
}

export default App;