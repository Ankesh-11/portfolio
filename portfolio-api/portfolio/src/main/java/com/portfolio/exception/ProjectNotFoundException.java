package com.portfolio.exception;

public class ProjectNotFoundException extends Exception{
    private static final long serialVersionUID = 5575740293295343349L;

    public ProjectNotFoundException(String s) {
        super(s);
    }
}
