import React, { useState } from 'react';
import "./GeoJsPage.css"

function Geolocation() {
  const [ipAddress, setIpAddress] = useState('');
  const [locationData, setLocationData] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitted(true);
    setLocationData(null); // clear locationData
    try {
      const response = await fetch(`/api/get_info_from_ip?ip=${ipAddress}`);
      const data = await response.json()
      if (response.ok) {
        setLocationData(data);
        setErrorMessage(null);
      } else {
        setErrorMessage(data["error"]);
      }
    } catch (error) {
      setErrorMessage('An error occurred. Please try again later with proper input.');
    }
  };

  return (
    <div className="GeoJsDiv">
        <h1>GeoJs API</h1>
        <form className="ipForm" onSubmit={handleSubmit}>
          <label>
            IP Address:
            <input type="text" placeholder="Please enter a valid IP address." value={ipAddress} onChange={(e) => setIpAddress(e.target.value)} />
          </label>
          <button className="ipForm button" type="submit">Get Location Data</button>
        </form>
        {submitted && errorMessage && (
          <div className="error">{errorMessage}</div>
        )}
        {locationData && (
          <div className = "tableInfo">
            <h2>IP Information</h2>
            <table className="locationData">
              <thead>
                <tr>
                  <th>Parameter</th>
                  <th>Value</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>IP Address</td>
                  <td>{locationData.ipAddress}</td>
                </tr>
                <tr>
                  <td>City</td>
                  <td>{locationData.city}</td>
                </tr>
                <tr>
                  <td>Region</td>
                  <td>{locationData.region}</td>
                </tr>
                <tr>
                  <td>Country</td>
                  <td>{locationData.country}</td>
                </tr>
                <tr>
                  <td>Latitude</td>
                  <td>{locationData.latitude}</td>
                </tr>
                <tr>
                  <td>Longitude</td>
                  <td>{locationData.longitude}</td>
                </tr>
                <tr>
                  <td>Timezone</td>
                  <td>{locationData.timezone}</td>
                </tr>
                <tr>
                  <td>Organization</td>
                  <td>{locationData.organization}</td>
                </tr>
              </tbody>
            </table>
          </div>
        )}
      </div>
  );
}

export default Geolocation;
