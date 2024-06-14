package com.propertify.confirmationtoken.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(){
        super("Token expired");
    }
}
