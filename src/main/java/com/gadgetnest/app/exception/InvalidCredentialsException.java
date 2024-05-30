package com.gadgetnest.app.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String msg){
        super(msg);
    }
}
