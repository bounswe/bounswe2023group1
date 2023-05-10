package com.a1.disasterresponse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
