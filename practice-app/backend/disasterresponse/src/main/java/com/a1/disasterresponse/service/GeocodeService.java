package com.a1.disasterresponse.service;

import com.a1.disasterresponse.model.GeocodeData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;



@Service
public class GeocodeService {

    private final GeoApiContext geoApiContext;

    public GeocodeService(@Value("${google.api.key}") String apiKey) {
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public GeocodeData geocodeAddress(String address) throws Exception {
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
        if (results.length > 0) {
            return new GeocodeData(results[0].geometry.location.lat, results[0].geometry.location.lng, address);
        }
        return null;
    }

    public GeocodeData reverseGeocodeCoordinates(double lat, double lng) throws Exception {
        GeocodingResult[] results = GeocodingApi.reverseGeocode(geoApiContext, lat, lng).await();
        if (results.length > 0) {
            return new GeocodeData(lat, lng, results[0].formattedAddress);
        }
        return null;
    }
}