package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.a1.disasterresponse.model.*;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherData> getTemperature(@RequestParam double lat, @RequestParam double lon) {
        try {
            WeatherData weatherData = weatherService.getWeather(lat, lon);
            return new ResponseEntity<>(weatherData, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/givefeedback")
    public ResponseEntity<Void> submitFeedback(@RequestBody Feedback feedback) {
        // Save the feedback in the database
        weatherService.saveFeedback(feedback);
        // You can return an appropriate response, such as 201 (CREATED)
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping("/getfeedback")
    public ResponseEntity<List<Feedback>> getFeedback() {
        // Get all the feedback from the database
        List<Feedback> feedback = weatherService.getAllFeedbacks();
        // You can return an appropriate response, such as 200 (OK)
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }
}

