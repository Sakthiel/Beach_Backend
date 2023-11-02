package com.thoughtworks.pulpticket.show.controller;

import com.thoughtworks.pulpticket.show.ShowService;
import com.thoughtworks.pulpticket.show.repository.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController

public class ShowController {
    private ShowService showService;
    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/shows")
    public ResponseEntity<List<Show>>fetchAll(){
        return new ResponseEntity<>(showService.fetchAll() , HttpStatus.OK);
    }

    @GetMapping("/shows/{movieId}/{date}")
    public ResponseEntity<List<Show>>fetchShows(@PathVariable String movieId , @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        List<Show> showList = showService.fetchShowsByDateAndMovie(movieId , date);
        return new ResponseEntity<>(showList , HttpStatus.OK);
    }

}
