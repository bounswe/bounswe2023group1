package com.a1.disasterresponse.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import com.a1.disasterresponse.model.*;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
@Service
public class WeatherService {

    private static final String BASE_URL = "https://weatherapi-com.p.rapidapi.com/current.json";
    @Value("${weather.api.key}")
    private String apiKey;
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public WeatherData getWeather(double lat, double lon) throws IOException {
        String url = String.format("%s?q=%f,%f", BASE_URL, lat, lon);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String body = response.body().string();
            JsonNode rootNode = mapper.readTree(body);

            JsonNode current = rootNode.get("current");
            if (current == null) {
                throw new IOException("No weather data available");
            }
            double temperature = current.get("temp_c").asDouble();
            String conditionText = current.get("condition").get("text").asText();
            String conditionIcon = "https:" + current.get("condition").get("icon").asText();
    
            return new WeatherData(temperature, conditionText, conditionIcon);
        }
    }
}
