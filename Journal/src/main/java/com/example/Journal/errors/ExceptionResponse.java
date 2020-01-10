package com.example.Journal.errors;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

    private HttpStatus errorCode;
    private String errorMessage;

    public ExceptionResponse() {}

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}