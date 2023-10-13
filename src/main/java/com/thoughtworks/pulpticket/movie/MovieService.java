package com.thoughtworks.pulpticket.movie;

import com.thoughtworks.pulpticket.movie.exceptions.EmptyMovieListException;
import com.thoughtworks.pulpticket.movie.exceptions.MovieNotFoundException;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import com.thoughtworks.pulpticket.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    MovieRepository movieRepository;
    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(){
        List<Movie> movieList = movieRepository.findAll();
        if(movieList.isEmpty())
            throw new EmptyMovieListException("No movies found");
        return movieList;
    }

    public Movie getMovieById(String movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.isEmpty())
            throw new MovieNotFoundException("There is no movie by this id.");
        return movie.get();
    }
}
