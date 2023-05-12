package com.a1.disasterresponse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.a1.disasterresponse.model.BookmarkedDestination;
import com.a1.disasterresponse.repository.BookmarkedDestinationRepository;

public class DirectionsServiceTest {
	
	private DirectionsService directionsService;
	private BookmarkedDestinationRepository bookmarkedDestinationRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		bookmarkedDestinationRepository = Mockito.mock(BookmarkedDestinationRepository.class);
		directionsService = new DirectionsService(bookmarkedDestinationRepository);
	}
	
	@Test
	void testGetBookmarkedDestinations_whenValidRequest_thenShouldReturnListOfBookmarkedDestinations() {
		//Data for testing
		BookmarkedDestination bookmarkedDestination1 = new BookmarkedDestination(1L, "test-destination-1");
		BookmarkedDestination bookmarkedDestination2 = new BookmarkedDestination(2L, "test-destination-2");
		List<BookmarkedDestination> destinations = List.of(bookmarkedDestination1, bookmarkedDestination2);
		
		//Test Case
		Mockito.when(bookmarkedDestinationRepository.findAll()).thenReturn(destinations);
		
		//Verify
		List<BookmarkedDestination> result = directionsService.getBookmarkedDestinations();
		
		assertEquals(result, destinations);
		
		Mockito.verify(bookmarkedDestinationRepository).findAll();
	}
	
	@Test
	void testGetBookmarkedDestinations_whenNoBookmarkedDestinationExist_thenShouldReturnEmptyList() {
		//Data for testing
		
		//Test Case
		Mockito.when(bookmarkedDestinationRepository.findAll()).thenReturn(List.of());
		
		//Verify
		List<BookmarkedDestination> result = directionsService.getBookmarkedDestinations();
		
		assertEquals(result, List.of());
		
		Mockito.verify(bookmarkedDestinationRepository).findAll();
	}
	
	@Test
	void testSaveDestination_whenCalledWithValidRequest_thenShouldReturnStringCreated() {
		//Data for testing
		String destination = "bogazici";
		BookmarkedDestination dest = new BookmarkedDestination(destination);
		BookmarkedDestination savedDest = new BookmarkedDestination(1L, destination);
		
		//Test Case
		Mockito.when(bookmarkedDestinationRepository.getByAddress(destination)).thenReturn(null);
		Mockito.when(bookmarkedDestinationRepository.save(dest)).thenReturn(savedDest);
		
		//Verify
		String result = directionsService.saveDestination(destination);
		Mockito.verify(bookmarkedDestinationRepository).getByAddress(destination);
		Mockito.verify(bookmarkedDestinationRepository).save(dest);
		
		assertEquals("CREATED", result);
	}
	
	@Test
	void testSaveDestination_whenCalledWithAlreadyExistingDestination_thenShouldReturnStringAlreadyExist() {
		//Data for testing
		String destination = "bogazici";
		BookmarkedDestination savedDest = new BookmarkedDestination(1L, destination);
		
		//Test Case
		Mockito.when(bookmarkedDestinationRepository.getByAddress(destination)).thenReturn(savedDest);
		
		//Verify
		String result = directionsService.saveDestination(destination);
		
		Mockito.verify(bookmarkedDestinationRepository).getByAddress(destination);
		
		assertEquals("ALREADY_EXIST", result);
	}
}
