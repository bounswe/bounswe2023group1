import React, { useState, useEffect } from "react";
import axios from "axios";
import "./WeatherPage.css";

const WeatherPage = () => {
  const [address, setAddress] = useState("");
  const [temperature, setTemperature] = useState("");
  const [windspeed, setWindspeed] = useState("");
  const [winddirection, setWinddirection] = useState("");
  const [feedbacks, setFeedbacks] = useState([]);
  const [feedbackName, setFeedbackName] = useState("");
  const [feedbackEmail, setFeedbackEmail] = useState("");
  const [feedbackMessage, setFeedbackMessage] = useState("");
  const [showFeedbacks, setShowFeedbacks] = useState(false);

  useEffect(() => {
    fetchFeedbacks();
  }, []);

  const fetchFeedbacks = async () => {
    try {
      const response = await axios.get("/api/getfeedback");
      setFeedbacks(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const getLatLon = () => {
    axios
      .get(`/api/geocode?address=${encodeURIComponent(address)}`)
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
      const response = await axios.get("/api/weather", {
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

  const handleFeedbackSubmit = async (event) => {
    event.preventDefault();
    try {
      await axios.post("/api/givefeedback", {
        name: feedbackName,
        email: feedbackEmail,
        message: feedbackMessage,
      });
      setFeedbackName("");
      setFeedbackEmail("");
      setFeedbackMessage("");
      fetchFeedbacks();
    } catch (error) {
      console.error(error);
    }
  };
  
  const toggleFeedbacksVisibility = () => {
    setShowFeedbacks(!showFeedbacks);
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
      <div className="feedback-section">
        <div className="feedback-form">
          <h2>Feedbacks:</h2>
          <button className="get-feedbacks-button" type="submit" onClick={toggleFeedbacksVisibility}>
            {showFeedbacks ? "Hide Feedbacks" : "See Feedbacks"}
          </button>
          {showFeedbacks && (
            <div className="feedback-box">
              <ul>
                {feedbacks.map((feedback) => (
                  <li key={feedback.id}>
                    <strong>{feedback.name}</strong> ({feedback.email}): {feedback.message}
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>
        <form className="feedback-form" onSubmit={handleFeedbackSubmit}>
          <div className="form-field">
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              id="name"
              value={feedbackName}
              onChange={(event) => setFeedbackName(event.target.value)}
            />
          </div>
          <div className="form-field">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              value={feedbackEmail}
              onChange={(event) => setFeedbackEmail(event.target.value)}
            />
          </div>
          <div className="form-field">
            <label htmlFor="message">Message:</label>
            <textarea
              id="message"
              value={feedbackMessage}
              onChange={(event) => setFeedbackMessage(event.target.value)}
            ></textarea>
          </div>
          <button type="submit">Submit Feedback</button>
        </form>
      </div>
    </div>
  );  
};
export default WeatherPage;

