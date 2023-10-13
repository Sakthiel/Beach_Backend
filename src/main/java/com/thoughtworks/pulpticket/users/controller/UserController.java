package com.thoughtworks.pulpticket.users.controller;

import com.thoughtworks.pulpticket.users.UserPrincipalService;
import com.thoughtworks.pulpticket.users.repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserPrincipalService userPrincipalService;
    @CrossOrigin
    @GetMapping("/login")
    Map<String, Object> login(Principal principal) {
        String username = principal.getName();
        User user = userPrincipalService.findUserByUsername(username);

        Map<String, Object> userDetails = new HashMap<>();

        userDetails.put("username", user.getUsername());
        return userDetails;
    }

}