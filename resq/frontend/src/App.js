import React from 'react';
import {BrowserRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import {Navbar, Container, Nav} from 'react-bootstrap';
import UserRoles from "./pages/UserRoles";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import MapDemo from "./pages/MapDemo";
import Account from "./pages/Account";
import RoleRequest from "./pages/RoleRequest";
import AppService from "./AppService";


const smallRedCircle = (
    <svg
        xmlns="http://www.w3.org/2000/svg"
        width="20"
        height="20"
        viewBox="0 0 20 20"
    >
        <circle cx="10" cy="10" r="8" fill="red"/>
    </svg>
);

const navLinks = [
    {path: '/signin', label: <strong>Sign In</strong>, component: SignIn, icon: smallRedCircle,},
    {path: '/signup', label: <strong>Sign Up</strong>, component: SignUp, icon: smallRedCircle,},
    {path: '/userroles', label: <strong>User Roles</strong>, component: UserRoles, icon: smallRedCircle},
    {path: '/map', label: <strong>Map Demo</strong>, component: MapDemo, icon: smallRedCircle},
    {path: '/account', label: <strong>Account</strong>, component: Account, icon: smallRedCircle},
];

function App() {
    const appService = new AppService()

    return (
        <Router>
            <div>
                <Navbar bg="light" variant="light" expand="lg">
                    <Container>
                        <Navbar.Brand href="/" style={{color: 'red', fontWeight: 'bold'}}>
                            <smallRedCircle/>
                            ResQ
                        </Navbar.Brand>
                        <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="me-auto" style={{width: "100%"}}>
                                {navLinks.map(({path, label, icon}) => (
                                    <Nav.Link key={path} href={path} style={{"marginLeft": "auto"}}>
                                        {icon}
                                        {label}
                                    </Nav.Link>
                                ))}
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
                <main>
                    <Routes>
                        {navLinks.map(({path, component}) => (
                            <Route key={path} path={path} element={React.createElement(component, {appService})}/>
                        ))}
                        <Route path="/" element={<Navigate to="/signup"/>}/>
                        <Route path="/rolerequest" state={{appService}}
                               element={React.createElement(RoleRequest, {appService})}/>
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;