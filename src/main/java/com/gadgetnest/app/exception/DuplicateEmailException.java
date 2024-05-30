package com.gadgetnest.app.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String msg){
        super(msg);
    }
}
