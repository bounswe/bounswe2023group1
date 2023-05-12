package com.a1.disasterresponse.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.a1.disasterresponse.model.*;
import com.a1.disasterresponse.repository.FeedbackRepository;

import java.io.IOException;
import java.util.List;

@Service
public class WeatherService {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private final FeedbackRepository feedbackRepository;

    public WeatherService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
    public WeatherData getWeather(double lat, double lon) throws IOException {
        String url = BASE_URL + "?latitude=" + lat + "&longitude=" + lon + "&current_weather=true";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String body = response.body().string();
            JsonNode rootNode = mapper.readTree(body);

            JsonNode currentWeather = rootNode.get("current_weather");
            if (currentWeather == null) {
                throw new IOException("No current weather data available");
            }
            double temperature = currentWeather.get("temperature").asDouble();
            double windSpeed = currentWeather.get("windspeed").asDouble();
            double windDirection = currentWeather.get("winddirection").asDouble();

            return new WeatherData(temperature, windSpeed, windDirection);
        }
    }

    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
    public OkHttpClient getClient() {
        return client;
    }
}
