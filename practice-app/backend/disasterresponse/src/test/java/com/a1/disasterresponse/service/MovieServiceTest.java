package com.a1.disasterresponse.service;

import com.a1.disasterresponse.model.Movie;
import com.a1.disasterresponse.model.WatchedItem;
import com.a1.disasterresponse.repository.WatchedItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MovieServiceTest {

    @Mock
    private WatchedItemRepository watchedItemRepository;

    private MovieService movieService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(watchedItemRepository);
    }
    @Test
    void getMoviesTest() throws Exception {
        String query = "Inception";
        int limit = 5;
        List<Movie> movies = movieService.getMovies(query, limit);
        assertEquals(5, movies.size());
        assertEquals("Inception", movies.get(0).getTitle());
    }
    @Test
    void saveWatchedItemTest() throws Exception {
        WatchedItem watchedItem = new WatchedItem();
        watchedItem.setTitle("Inception");
        watchedItem.setRating(4);
        Long id = movieService.saveWatchedItem(watchedItem);
        assertNotEquals(null, id);
        verify(watchedItemRepository, times(1)).save(watchedItem);
    }

    @Test
    void getAverageRatingTest() throws Exception {
        String title = "Inception";
        int rating = 3;
        WatchedItem watchedItem = new WatchedItem();
        watchedItem.setTitle(title);
        watchedItem.setRating(rating);
        movieService.saveWatchedItem(watchedItem);
        Double averageRating = movieService.getAverageRating(title);
        assertEquals(averageRating, 3.0);
    }

    @Test
    void testThereIsNoRating() throws Exception {
        String title = "Harry Potter";
        Double averageRating = movieService.getAverageRating(title);
        assertEquals(averageRating, 0.0);
    }
}
