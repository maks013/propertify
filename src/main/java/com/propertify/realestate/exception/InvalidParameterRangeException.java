package com.propertify.realestate.exception;

public class InvalidParameterRangeException extends RuntimeException {
    public InvalidParameterRangeException() {
        super("Invalid range parameters: start and end must not be null, " +
                "must be non-negative, and start must be less than or equal to end.");
    }
}
