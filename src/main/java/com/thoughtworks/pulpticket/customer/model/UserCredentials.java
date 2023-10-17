package com.thoughtworks.pulpticket.customer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public class UserCredentials {

    @JsonProperty
    @Pattern(regexp = ("[a-zA-Z$_][a-zA-Z0-9$_@]*"), message = "Please enter valid username")
    private String username;

    @JsonProperty
    @Pattern(regexp = ("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,64}$"), message = "Password should have at least 1 lowercase, 1 uppercase, 1 digit and 1 special character. It should be of minimum of 8 and maximum of 64 characters")
    private String password;

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserCredentials() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}