package com.thoughtworks.pulpticket.handlers;

import com.thoughtworks.pulpticket.customer.exceptions.PhoneNumberAlreadyExistException;
import com.thoughtworks.pulpticket.customer.exceptions.UsernameAlreadyExistException;
import com.thoughtworks.pulpticket.movie.exceptions.EmptyMovieListException;
import com.thoughtworks.pulpticket.movie.exceptions.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

import static java.util.Collections.singletonList;
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmptyMovieListException.class)
    public ResponseEntity<String> handleEmptyMovieListException(EmptyMovieListException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<String> handleMovieNotFoundException(MovieNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PhoneNumberAlreadyExistException.class)
    public ResponseEntity<String> handlePhoneNumberAlreadyExistException(PhoneNumberAlreadyExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<String> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
