package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.GeocodeData;
import com.a1.disasterresponse.service.GeocodeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GeocodeController {

    private final GeocodeService geocodeService;

    public GeocodeController(GeocodeService geocodeService) {
        this.geocodeService = geocodeService;
    }

    @GetMapping("/geocode")
    public ResponseEntity<Map<String, Object>> geocode(@RequestParam("address") String address) {
        try {
            GeocodeData geocodeData = geocodeService.geocodeAddress(address);
            if (geocodeData != null) {
                return ResponseEntity.ok(Map.of("latitude", geocodeData.getLatitude(), "longitude", geocodeData.getLongitude(), "address", geocodeData.getAddress()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Unable to geocode address"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/reverse_geocode")
    public ResponseEntity<Map<String, Object>> reverseGeocode(@RequestParam("latitude") Double lat, @RequestParam("longitude") Double lng) {
        try {
            GeocodeData geocodeData = geocodeService.reverseGeocodeCoordinates(lat, lng);
            if (geocodeData != null) {
                return ResponseEntity.ok(Map.of("latitude", geocodeData.getLatitude(), "longitude", geocodeData.getLongitude(), "address", geocodeData.getAddress()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Unable to reverse geocode coordinates"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
