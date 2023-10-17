package com.pechkin.exception;

public class UserAlreadyExistException extends Throwable {
    private static final String ERROR_TEXT = "user with this username already exist";

    public UserAlreadyExistException() {
        super(ERROR_TEXT);
    }
}

