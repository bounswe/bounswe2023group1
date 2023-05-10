import React, { useState } from "react";
import axios from "axios";

const WeatherApiPage = () => {
  const [latitude, setLatitude] = useState("");
  const [longitude, setLongitude] = useState("");
  const [temperature, setTemperature] = useState("");
  const [conditionText, setConditionText] = useState("");
  const [conditionIcon, setConditionIcon] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.get("/weather", {
        params: {
          lat: latitude,
          lon: longitude,
        },
      });
      setTemperature(response.data.temperature);
      setConditionText(response.data.conditionText);
      setConditionIcon(response.data.conditionIcon);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <h1>Weather API Page</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Latitude:
          <input
            type="text"
            value={latitude}
            onChange={(event) => setLatitude(event.target.value)}
          />
        </label>
        <br />
        <label>
          Longitude:
          <input
            type="text"
            value={longitude}
            onChange={(event) => setLongitude(event.target.value)}
          />
        </label>
        <button type="submit">Get Temperature</button>
      </form>
      {temperature && (
        <div>
          <h2>Temperature:</h2>
          <p>{temperature}</p>
          <h2>Condition:</h2>
          <p>{conditionText}</p>
          <img src={conditionIcon} alt="weather icon" />
        </div>
      )}
    </div>
  );
};

export default WeatherApiPage;