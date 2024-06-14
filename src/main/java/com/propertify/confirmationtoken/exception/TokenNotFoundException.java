package com.propertify.confirmationtoken.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Token not found exception");
    }
}
