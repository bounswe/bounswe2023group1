package com.a1.disasterresponse.controller;
import com.a1.disasterresponse.service.GeocodeService;

import com.google.maps.model.LatLng;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeocodeController {

    private final GeocodeService geocodeService;

    public GeocodeController(GeocodeService geocodeService) {
        this.geocodeService = geocodeService;
    }

    @GetMapping("/geocode")
    public ResponseEntity<Map<String, Object>> geocode(@RequestParam("address") String address) {
        try {
            LatLng coordinates = geocodeService.geocodeAddress(address);
            if (coordinates != null) {
                return ResponseEntity.ok(Map.of("latitude", coordinates.lat, "longitude", coordinates.lng));
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
            String address = geocodeService.reverseGeocodeCoordinates(lat, lng);
            if (address != null) {
                return ResponseEntity.ok(Map.of("address", address));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Unable to reverse geocode coordinates"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}

