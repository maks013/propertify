package com.propertify.user.exception;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(){
        super("Invalid format of email");
    }
}
