package com.thoughtworks.pulpticket.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsernameAvailableResponse {

    @JsonProperty
    private boolean isUsernameAvailable;

    public UsernameAvailableResponse(boolean isUsernameAvailable) {
        this.isUsernameAvailable = isUsernameAvailable;
    }
}
