import React from 'react';
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {Navbar, Container, Nav} from 'react-bootstrap';
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import MapDemo from "./pages/MapDemo";

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
    {path: '/signin', label: 'Sign In', component: SignIn, icon: smallRedCircle,},
    {path: '/signup', label: 'Sign Up', component: SignUp, icon: smallRedCircle,},
    {path: '/map', label: "Map Demo", component: MapDemo, icon: smallRedCircle}
];


function App() {
    return (
        <Router>
            <div>
                <Navbar bg="light" variant="light" expand="lg">
                    <Container>
                        <Navbar.Brand href="/">
                            ResQ</Navbar.Brand>
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
                            <Route key={path} path={path} element={React.createElement(component)}/>
                        ))}
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;