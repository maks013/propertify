package com.propertify.user.domain;

interface UserValidationService {

    void verifyAvailability(String email);

    void isEmailAvailable(String email);

    void validateEmailFormat(String email);
}
