package com.a1.disasterresponse.service;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Map;

import com.a1.disasterresponse.model.GeoJsData;

import com.a1.disasterresponse.model.IpList;

import java.util.List;

import com.a1.disasterresponse.repository.IpListRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GeoJsService {
    @Autowired
    private IpListRepository ipListRepository;

    private static final String GEOJS_API_URL = "https://get.geojs.io/v1/ip/geo/%s.json";
    private final OkHttpClient httpClient = new OkHttpClient();

    public GeoJsData getLocationData(String ipAddress) throws Exception {
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
        Map<String, Object> responseMap = gson.fromJson(responseBody, type);
        String ip_address = (String) responseMap.get("ip");
        String country = (String) responseMap.get("country");
        String region = (String) responseMap.get("region");
        String city = (String) responseMap.get("city");
        String latitude = String.valueOf(responseMap.get("latitude"));
        String longitude = String.valueOf(responseMap.get("longitude"));
        String timezone = (String) responseMap.get("timezone");
        String organization = (String) responseMap.get("organization");
        
    
        GeoJsData geoJsData = new GeoJsData(ip_address, country, region, city, latitude, longitude, timezone, organization);
    
        return geoJsData;
    }
    public List<IpList> getIpList() {
        return ipListRepository.findAll();
    }
    
    public String saveIp(String ip) {
        if(ipListRepository.getByIp(ip) == null) {
            IpList newIp = new IpList(ip);
            ipListRepository.save(newIp);
            return "CREATED";
        }
        return "ALREADY_EXIST";
    }
}