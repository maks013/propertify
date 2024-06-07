package com.propertify.preference.exception;

public class PreferencesNotFoundException extends RuntimeException {
    public PreferencesNotFoundException(Long userId) {
        super("No preference found for the specified user ID: " + userId);
    }
}

