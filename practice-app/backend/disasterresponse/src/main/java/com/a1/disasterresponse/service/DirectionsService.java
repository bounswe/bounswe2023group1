package com.a1.disasterresponse.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class DirectionsService {

	public String getDirectionBetweenAddresses(String from, String to) {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url("https://maps.googleapis.com/maps/api/directions/json?origin="+from+"&destination="+to+"&travelMode=driving&key=AIzaSyChUjWhPh659VpN1z-x359ZCJFhiYRrELA")
				  .get()
				  .build();
				Response response;
				try {
					response = client.newCall(request).execute();
					return response.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}
				throw new RuntimeException();
	}
	
}
