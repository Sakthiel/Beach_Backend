package com.thoughtworks.pulpticket.movie.controller;

import com.thoughtworks.pulpticket.movie.MovieService;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Movie>> fetchAllMovies(){
        List<Movie> movieList = movieService.getAllMovies();

        return new ResponseEntity<>(movieList , HttpStatus.OK);
    }
}
