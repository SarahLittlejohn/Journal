
package com.example.Journal.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(MyException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(ex.getErrorCode());
        response.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(response,response.getErrorCode());
    }

}