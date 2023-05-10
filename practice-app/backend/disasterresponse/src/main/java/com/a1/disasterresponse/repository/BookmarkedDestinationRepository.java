package com.a1.disasterresponse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a1.disasterresponse.model.BookmarkedDestination;

public interface BookmarkedDestinationRepository extends JpaRepository<BookmarkedDestination, Long> {

	BookmarkedDestination getByAddress(String destination);
	
}
