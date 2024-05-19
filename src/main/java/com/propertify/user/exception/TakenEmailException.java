package com.propertify.user.exception;

public class TakenEmailException extends RuntimeException {
    public TakenEmailException(){
        super("This email is already taken");
    }
}
