package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.Movie;
import com.a1.disasterresponse.model.WatchedItem;
import com.a1.disasterresponse.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService filmService) {
        this.movieService = filmService;
    }

    @GetMapping("/getMovies")
    public ResponseEntity<List<Movie>> getFilmId(@RequestParam String query, @RequestParam int limit) {
        try {
            List<Movie> movies = movieService.getMovies(query, limit);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/rateMovie")
    public ResponseEntity<Long> rateMovie(@RequestBody WatchedItem watchedItem) {
        Long id = movieService.saveWatchedItem(watchedItem);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/getAverageRating")
    public ResponseEntity<Double> getOverallRating(@RequestParam String title) {
        double rating = movieService.getAverageRating(title);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

//    @GetMapping("/getWatchedItems")
//    public ResponseEntity<List<WatchedItem>> getWatchedItems() {
//        List<WatchedItem> watchedItems = movieService.getAllWatchedItems();
//        return new ResponseEntity<>(watchedItems, HttpStatus.OK);
//    }

}
