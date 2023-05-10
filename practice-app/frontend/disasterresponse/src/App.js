import React from 'react';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Navbar, Container, Nav } from 'react-bootstrap';
import { FaHome, FaMap, FaDirections, FaCloud } from 'react-icons/fa';
import DirectionsPage from "./pages/DirectionsApi/DirectionsPage";
<<<<<<< Updated upstream
import WeatherApiPage from './pages/WeatherApi/WeatherPage';
const navLinks = [
    { path: '/googleDirectionsPage', label: 'Google Directions Api', component: DirectionsPage, icon: <FaDirections /> },
    { path: '/weatherApiPage', label: 'Weather Api', component: WeatherApiPage, icon: <FaCloud /> },

=======
import GoogleGeocodePage from './pages/GoogleGeocodeApi/GoogleGeocodePage';


const navLinks = [
    { path: '/googleDirectionsPage', label: 'Google Directions Api', component: DirectionsPage, icon: <FaDirections /> },
    { path: '/googleGeocodePage', label: ' Google Geocode API', component: GoogleGeocodePage, icon: <FaMap /> },
>>>>>>> Stashed changes
    // add the rest of APIs
];

function App() {
  return (
    <Router>
      <div>
        <Navbar bg="light" variant="light" expand="lg">
        <Container>
            <Navbar.Brand href="/"><FaHome /> Disaster Response</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
              <Nav className="me-auto">
                {navLinks.map(({ path, label, icon }) => (
                  <Nav.Link key={path} href={path}>
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
            {navLinks.map(({ path, component }) => (
              <Route key={path} path={path} element={React.createElement(component)} />
            ))}
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
