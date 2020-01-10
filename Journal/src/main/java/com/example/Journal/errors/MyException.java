package com.example.Journal.errors;
import org.springframework.http.HttpStatus;

public class MyException extends Exception {

    private HttpStatus errorCode;

    public MyException(HttpStatus errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

}