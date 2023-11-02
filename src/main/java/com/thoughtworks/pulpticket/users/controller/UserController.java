package com.thoughtworks.pulpticket.users.controller;

import com.thoughtworks.pulpticket.users.UserPrincipalService;
import com.thoughtworks.pulpticket.users.model.UsernameAvailableResponse;
import com.thoughtworks.pulpticket.users.repository.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserPrincipalService userPrincipalService;

    @GetMapping("/login")
    Map<String, Object> login(Principal principal) {
        String username = principal.getName();
        User user = userPrincipalService.findUserByUsername(username);

        Map<String, Object> userDetails = new HashMap<>();

        userDetails.put("username", user.getUsername());
        return userDetails;
    }

    @GetMapping("/username")
    public ResponseEntity<UsernameAvailableResponse> isUsernameAvailable(@Valid @RequestParam(name = "username") String username) {
        boolean isUsernameAvailable = userPrincipalService.isUsernameAvailable(username);
        UsernameAvailableResponse usernameAvailableResponse = new UsernameAvailableResponse(isUsernameAvailable);
        return new ResponseEntity<>(usernameAvailableResponse , HttpStatus.OK);
    }
}