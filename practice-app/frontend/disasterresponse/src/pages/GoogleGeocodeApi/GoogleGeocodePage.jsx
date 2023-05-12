import './GoogleGeocodePage.css';
import React from 'react'
import { useState } from 'react';
import axios from 'axios';

export default function GoogleGeocodePage() {
    const [lat, setLat] = useState("");
    const [lon, setLon] = useState("");
    const [address, setAddress] = useState("");
    const [inputAddress, setInputAddress] = useState("");
    const [inputLat, setInputLat] = useState("");
    const [inputLon, setInputLon] = useState("");

    const getLatLon = () => {
      axios.get(`/api/geocode?address=${encodeURIComponent(inputAddress)}`).then(res => {
        setLat(res.data.latitude)
        setLon(res.data.longitude)
      }).catch(error => {
        console.log(error)
      })
    }

    const getAddress = () => {
      axios.get(`/api/reverse_geocode?latitude=${inputLat}&longitude=${inputLon}`).then(res => {
        setAddress(res.data.address)
      }).catch(error => {
        console.log(error)
      })
    }

    return (
        <div className="geoDiv">
            <div className="card">
                <h2>Geocoding</h2>
                <form onSubmit={(e) => {e.preventDefault(); getLatLon()}}>
                    <input type="text" placeholder="Enter Address" onChange={e => setInputAddress(e.target.value)} />
                    <button type="submit">Get Coordinates</button>
                </form>
                <div className="latLonDiv">
                    <p>Latitude: {lat}</p>
                    <p>Longitude: {lon}</p>
                </div>
            </div>
            <div className="card">
                <h2>Reverse Geocoding</h2>
                    <form onSubmit={(e) => {e.preventDefault(); getAddress()}}>
                        <input type="text" placeholder="Enter Latitude" onChange={e => setInputLat(e.target.value)} />
                        <input type="text" placeholder="Enter Longitude" onChange={e => setInputLon(e.target.value)} />
                        <button type="submit">Get Address</button>
                    </form>
                <div className="addressDiv">
                    <p>Address: {address}</p>
                </div>
            </div>
        </div>

    );
}
