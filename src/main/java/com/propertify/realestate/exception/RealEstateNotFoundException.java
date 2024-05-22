package com.propertify.realestate.exception;

public class RealEstateNotFoundException extends RuntimeException {
    public RealEstateNotFoundException() {
        super("Real estate not found");
    }
}
