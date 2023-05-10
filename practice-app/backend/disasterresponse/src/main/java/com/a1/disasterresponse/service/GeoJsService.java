package com.a1.disasterresponse.service;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Map;

@Service
public class GeoJsService {

    private static final String GEOJS_API_URL = "https://get.geojs.io/v1/ip/geo/%s.json";
    private final OkHttpClient httpClient = new OkHttpClient();

    public Map<String, Object> getLocationData(String ipAddress) throws Exception {
        String url = String.format(GEOJS_API_URL, ipAddress);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new Exception("Failed to get location data from GeoJS API: " + response.code());
        }
        String responseBody = response.body().string();
        Gson gson = new Gson();
        Type type = Map.class.getTypeParameters()[0];
        Map<String, Object> response_map = gson.fromJson(responseBody, type);
        return response_map;
    }
}