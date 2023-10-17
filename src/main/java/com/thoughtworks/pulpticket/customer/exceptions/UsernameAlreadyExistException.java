package com.thoughtworks.pulpticket.customer.exceptions;

public class UsernameAlreadyExistException extends RuntimeException{
    public UsernameAlreadyExistException(String msg)
    {
        super(msg);
    }
}