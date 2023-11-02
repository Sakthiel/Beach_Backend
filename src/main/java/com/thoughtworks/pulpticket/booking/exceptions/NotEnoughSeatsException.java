package com.thoughtworks.pulpticket.booking.exceptions;

public class NotEnoughSeatsException extends RuntimeException {
    public NotEnoughSeatsException(String message) {
        super(message);
    }
}
