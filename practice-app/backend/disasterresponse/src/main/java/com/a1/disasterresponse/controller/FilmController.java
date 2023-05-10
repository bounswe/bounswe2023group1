package com.a1.disasterresponse.controller;

import Response.ImdbResponse.Movie;
import com.a1.disasterresponse.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/film")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/id")
    public ResponseEntity<List<Movie>> getFilmId(@RequestParam String query, @RequestParam int limit) {
        try {
            List<Movie> movies = filmService.getFilmId(query, limit);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
