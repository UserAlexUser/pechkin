package com.pechkin.exception;


public class EmailAlreadyExistsException extends RuntimeException {

    private static final String ERROR_TEXT = "user with this email already exist";

    public EmailAlreadyExistsException() {
        super(ERROR_TEXT);
    }
}
