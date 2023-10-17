import React from 'react';
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {Navbar, Container, Nav} from 'react-bootstrap';
import {FaHome, FaDoorOpen} from 'react-icons/fa';
import Login from "./pages/Login/Login";

const navLinks = [
    {path: '/login', label: ' Login', component: Login, icon: <FaDoorOpen/>},

    // add the rest of APIs
];

function App() {
    return (
        <Router>
            <div>
                <Navbar bg="light" variant="light" expand="lg">
                    <Container>
                        <Navbar.Brand href="/"><FaHome/> ResQ</Navbar.Brand>
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
