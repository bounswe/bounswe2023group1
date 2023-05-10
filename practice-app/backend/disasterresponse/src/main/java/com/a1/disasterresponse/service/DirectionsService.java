package com.a1.disasterresponse.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a1.disasterresponse.model.BookmarkedDestination;
import com.a1.disasterresponse.repository.BookmarkedDestinationRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class DirectionsService {
	
	@Autowired
	private BookmarkedDestinationRepository bookmarkedDestinationRepository;

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

	public List<BookmarkedDestination> getBookmarkedDestinations() {
		return bookmarkedDestinationRepository.findAll();
	}

	public String saveDestination(String destination) {
		if(bookmarkedDestinationRepository.getByAddress(destination) == null) {
			BookmarkedDestination dest = new BookmarkedDestination(destination);
			bookmarkedDestinationRepository.save(dest);
			return "CREATED";
		}
		return "ALREADY_EXIST";
	}
	
}
