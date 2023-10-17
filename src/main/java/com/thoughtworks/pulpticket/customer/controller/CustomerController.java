package com.thoughtworks.pulpticket.customer.controller;

import com.thoughtworks.pulpticket.customer.CustomerService;
import com.thoughtworks.pulpticket.customer.model.CustomerRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @CrossOrigin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        customerService.create(customerRequest);
        return new ResponseEntity<>("Customer Created Successfully" , HttpStatus.CREATED);
    }
}
