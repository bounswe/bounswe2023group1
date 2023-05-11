package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.WeatherData;
import com.a1.disasterresponse.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class WeatherControllerTest {

    private WeatherController weatherController;

    @Mock
    private WeatherService weatherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        weatherController = new WeatherController(weatherService);
    }

    @Test
    public void testGetTemperature_Success() throws IOException {
        // Mock weather data
        WeatherData weatherData = new WeatherData(25.5, "Sunny", "sun.png");

        // Mock weather service
        when(weatherService.getWeather(anyDouble(), anyDouble())).thenReturn(weatherData);

        // Call the API
        ResponseEntity<WeatherData> response = weatherController.getTemperature(37.123, -122.456);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weatherData, response.getBody());

        // Verify the weather service was called with the correct parameters
        verify(weatherService, times(1)).getWeather(37.123, -122.456);
    }

    @Test
    public void testGetTemperature_Exception() throws IOException {
        // Mock IOException in weather service
        when(weatherService.getWeather(anyDouble(), anyDouble())).thenThrow(IOException.class);

        // Call the API
        ResponseEntity<WeatherData> response = weatherController.getTemperature(37.123, -122.456);

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verify the weather service was called with the correct parameters
        verify(weatherService, times(1)).getWeather(37.123, -122.456);
    }
}
