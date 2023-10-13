package com.thoughtworks.pulpticket.movie.controller;

import com.thoughtworks.pulpticket.movie.MovieService;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class MovieController {
    private MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @CrossOrigin
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> fetchAllMovies(){
        List<Movie> movieList = movieService.getAllMovies();

        return new ResponseEntity<>(movieList , HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Movie> fetchMovie(@PathVariable String movieId){
        Movie movie = movieService.getMovieById(movieId);
        return new ResponseEntity<>(movie , HttpStatus.OK);
    }
}
