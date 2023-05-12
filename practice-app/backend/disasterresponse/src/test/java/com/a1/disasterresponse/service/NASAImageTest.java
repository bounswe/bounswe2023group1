package com.a1.disasterresponse.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.a1.disasterresponse.model.NasaData;
import com.a1.disasterresponse.repository.NasaDataRepository;
import com.a1.disasterresponse.service.NASAImageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class NASAImageTest {

	@Mock
	private NasaDataRepository nasaDataRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private NASAImageService nasaImageService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSearchImages() {
		// Mock response
		String responseBody = "Sample response body";
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		// Perform the test
		String result = nasaImageService.searchImages("moon");

		// Verify the result
		assertEquals(responseBody, result);
	}

	@Test
	public void testSaveNasaData() {
		// Create a sample NasaData object
		NasaData nasaData = new NasaData();
		nasaData.setId(1L);

		// Perform the test
		nasaImageService.saveNasaData(nasaData);

		// Verify that the save method is called once on the nasaDataRepository
		verify(nasaDataRepository, times(1)).save(nasaData);
	}
}

