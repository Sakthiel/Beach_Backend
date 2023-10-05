package com.thoughtworks.pulpticket.show.controller;

import com.thoughtworks.pulpticket.show.ShowService;
import com.thoughtworks.pulpticket.show.repository.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {
    private ShowService showService;
    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping()
    public ResponseEntity<List<Show>>fetchShows(){
        return new ResponseEntity<>(showService.fetchAll() , HttpStatus.OK);
    }
}
