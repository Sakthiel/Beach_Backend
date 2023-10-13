package com.thoughtworks.pulpticket.movie;

import com.thoughtworks.pulpticket.movie.exceptions.EmptyMovieListException;
import com.thoughtworks.pulpticket.movie.exceptions.MovieNotFoundException;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import com.thoughtworks.pulpticket.movie.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
   private MovieRepository movieRepository;
   private MovieService movieService;
   @BeforeEach
   public void beforeEach(){
       movieRepository = mock(MovieRepository.class);
       movieService = new MovieService(movieRepository);
   }

   @Test
    public void should_return_list_of_movies(){
       List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(
                "tteefdu" ,
                "Bigil" , 100L ,
                "sport" , BigDecimal.valueOf(7.5) ,
                "https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg"));
       when(movieRepository.findAll()).thenReturn(movieList);

       List<Movie> actualList = movieService.getAllMovies();

       verify(movieRepository).findAll();
       assertThat(actualList , is(equalTo(movieList)));

   }

    @Test
    public void should_throw_exception_when_empty_list_found(){
        List<Movie> movieList = new ArrayList<>();
        when(movieRepository.findAll()).thenReturn(movieList);

        assertThrows(EmptyMovieListException.class, () -> {
           movieService.getAllMovies();
        });

    }

    @Test
    public void should_return_movie_by_id(){
       String movieId = "tteefdu" ;
       Movie movie = new Movie("tteefdu" , "Bigil" , 100L , "sport" , BigDecimal.valueOf(7.5) ,"https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg");
       when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

       Movie actualMovie = movieService.getMovieById(movieId);

        verify(movieRepository).findById(movieId);
        assertThat(movie , is(equalTo(actualMovie)));
    }

    @Test
    public void should_throw_exception_when_movie_not_found(){
        when(movieRepository.findById("asaad12")).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> {
            movieService.getMovieById("asaad12");
        });

    }

}
