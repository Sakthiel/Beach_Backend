package com.thoughtworks.pulpticket.customer.exceptions;

public class PhoneNumberAlreadyExistException extends RuntimeException{
    public PhoneNumberAlreadyExistException(String message){
        super(message);
    }
}