package com.propertify.confirmationtoken.exception;

public class TokenAlreadyConfirmedException extends RuntimeException {
    public TokenAlreadyConfirmedException(){
        super("Token already confirmed");
    }
}
