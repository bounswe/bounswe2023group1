package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.Movie;
import com.a1.disasterresponse.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            List<Movie> movies = movieService.getFilmId(query, limit);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
