package com.raghav.assignment2student.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}