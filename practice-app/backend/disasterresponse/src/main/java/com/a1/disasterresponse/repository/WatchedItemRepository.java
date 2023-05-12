package com.a1.disasterresponse.repository;


import com.a1.disasterresponse.model.WatchedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedItemRepository extends JpaRepository<WatchedItem, Long> {

    @Query("SELECT AVG(w.rating) FROM WatchedItem w WHERE w.title = ?1")
    Double getAverageRating(String title);
}

