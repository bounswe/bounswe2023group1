package com.a1.disasterresponse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a1.disasterresponse.model.BookmarkedDestination;
import com.a1.disasterresponse.service.DirectionsService;

@RestController
public class DirectionsController {
	
	private final DirectionsService directionsService;
	
	public DirectionsController(DirectionsService directionsService) {
		this.directionsService = directionsService;
	}


	@GetMapping("/getDirection")
	public ResponseEntity<String> getDirection(@RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<String>(directionsService.getDirectionBetweenAddresses(from, to) ,HttpStatus.OK);
	}
	
	@GetMapping("/getBookmarkedDestinations")
	public ResponseEntity<List<BookmarkedDestination>> getBookmarkedDestinations() {
		return new ResponseEntity<List<BookmarkedDestination>>(directionsService.getBookmarkedDestinations() ,HttpStatus.OK);
	}	
	
	@PostMapping("/saveDestination")
	public ResponseEntity<String> saveDestination(@RequestParam String destination) {
		return ResponseEntity.ok(directionsService.saveDestination(destination));
	}
}
