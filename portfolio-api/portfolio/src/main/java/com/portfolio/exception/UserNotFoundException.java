package com.portfolio.exception;

public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 5575740293295343349L;

    public UserNotFoundException(String s) {
        super(s);
    }
}
