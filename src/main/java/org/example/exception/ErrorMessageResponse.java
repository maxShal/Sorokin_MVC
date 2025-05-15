package org.example.exception;


import org.springframework.http.HttpStatus;

public class ErrorMessageResponse {
    private String message;
    private String ex;

    public ErrorMessageResponse(String message, String ex) {
        this.message = message;
        this.ex = ex;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }
}
