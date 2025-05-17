package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleValidationExceptions(NotValidException ex)
    {
        var error = new ErrorMessageResponse("Bad Valid",ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNotFoundExceptions(NotFoundException ex)
    {
        var error = new ErrorMessageResponse("Not Found",ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}