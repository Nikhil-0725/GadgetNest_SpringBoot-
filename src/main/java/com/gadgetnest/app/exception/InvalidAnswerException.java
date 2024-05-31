package com.gadgetnest.app.exception;

public class InvalidAnswerException extends RuntimeException {
    public InvalidAnswerException(String msg){
        super(msg);
    }
}
