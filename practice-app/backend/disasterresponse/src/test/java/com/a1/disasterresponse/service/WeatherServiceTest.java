package com.a1.disasterresponse.service;

import com.a1.disasterresponse.model.Feedback;
import com.a1.disasterresponse.model.WeatherData;
import com.a1.disasterresponse.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WeatherServiceTest {
    @Mock
    private FeedbackRepository feedbackRepository;

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherService(feedbackRepository);
    }

    @Test
    void testGetWeather() throws IOException {
        // Mock response data
        String responseBody = "{\"current_weather\": {\"temperature\": 20.5, \"windspeed\": 10.2, \"winddirection\": 180.0}}";

        OkHttpClient mockClient = mock(OkHttpClient.class);
        Response mockResponse = mock(Response.class);

        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.body()).thenReturn(okhttp3.ResponseBody.create(responseBody, null));
        when(mockClient.newCall(any(Request.class))).thenReturn(mock(Call.class));
        when(mockClient.newCall(any(Request.class)).execute()).thenReturn(mockResponse);


        // Perform the test
        WeatherData weatherData = weatherService.getWeather(37.7749, -122.4194);

        // Verify the result
        assertNotNull(weatherData);
        assertEquals(20.5, weatherData.getTemperature());
        assertEquals(10.2, weatherData.getWindSpeed());
        assertEquals(180.0, weatherData.getWindDirection());
    }

    @Test
    void testGetWeatherThrowsIOException() throws IOException {
        OkHttpClient mockClient = mock(OkHttpClient.class);
        Response mockResponse = mock(Response.class);

        when(mockResponse.isSuccessful()).thenReturn(false);
        when(mockClient.newCall(any(Request.class))).thenReturn(mock(Call.class));
        when(mockClient.newCall(any(Request.class)).execute()).thenReturn(mockResponse);


        // Perform the test and verify that IOException is thrown
        assertThrows(IOException.class, () -> weatherService.getWeather(37.7749, -122.4194));
    }

    @Test
    void testSaveFeedback() {
        Feedback feedback = new Feedback();
        weatherService.saveFeedback(feedback);

        // Verify that the save method is called once on the feedbackRepository
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void testGetAllFeedbacks() {
        // Mock the feedback list
        List<Feedback> feedbackList = new ArrayList<>();
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        feedbackList.add(feedback1);
        feedbackList.add(feedback2);

        when(feedbackRepository.findAll()).thenReturn(feedbackList);

        // Perform the test
        List<Feedback> result = weatherService.getAllFeedbacks();

        // Verify the result
        assertEquals(feedbackList, result);
        assertEquals(2, result.size());
    }
}
