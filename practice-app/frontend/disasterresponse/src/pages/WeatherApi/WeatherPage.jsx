import React, { useState } from "react";
import axios from "axios";
import "./WeatherPage.css";

const WeatherPage = () => {
  const [address, setAddress] = useState("");
  const [temperature, setTemperature] = useState("");
  const [windspeed, setWindspeed] = useState("");
  const [winddirection, setWinddirection] = useState("");

  const getLatLon = () => {
    axios
      .get(`/geocode?address=${encodeURIComponent(address)}`)
      .then((res) => {
        const latitude = res.data.latitude;
        const longitude = res.data.longitude;
        getWeatherData(latitude, longitude);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const getWeatherData = async (latitude, longitude) => {
    try {
      const response = await axios.get("/weather", {
        params: {
          lat: latitude,
          lon: longitude,
        },
      });
      setTemperature(response.data.temperature);
      setWindspeed(response.data.windSpeed);
      setWinddirection(response.data.windDirection);
    } catch (error) {
      console.error(error);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    getLatLon();
  };

  return (
    <div className="weather-page">
      <h1>Weather Page</h1>
      <form className="weather-form" onSubmit={handleSubmit}>
        <label>
          Address:
          <input
            type="text"
            value={address}
            onChange={(event) => setAddress(event.target.value)}
          />
        </label>
        <button type="submit">Get Weather</button>
      </form>
      {temperature && (
        <div className="weather-info">
          <h2>Temperature:</h2>
          <p>{temperature}</p>
          <h2>Wind Speed:</h2>
          <p>{windspeed}</p>
          <h2>Wind Direction:</h2>
          <p>{winddirection}</p>
        </div>
      )}
    </div>
  );
};

export default WeatherPage;
