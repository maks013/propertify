package com.propertify.user.domain;

import com.propertify.user.exception.InvalidEmailFormatException;
import com.propertify.user.exception.TakenEmailException;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
class UserDataValidator implements UserValidationService {

    private final UserRepository userRepository;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";

    @Override
    public void verifyAvailability(String email) {
        validateEmailFormat(email);
        isEmailAvailable(email);
    }

    @Override
    public void isEmailAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new TakenEmailException();
        }
    }

    @Override
    public void validateEmailFormat(String email) {
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailFormatException();
        }
    }
}
