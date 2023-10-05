package com.thoughtworks.pulpticket.movie.exceptions;

public class EmptyMovieListException extends RuntimeException{
    public EmptyMovieListException(String message){
        super(message);
    }
}
