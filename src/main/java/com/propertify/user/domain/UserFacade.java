package com.propertify.user.domain;

import com.propertify.user.dto.RegistrationRequest;
import com.propertify.user.dto.UserDto;
import com.propertify.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import static com.propertify.user.domain.User.enabledForNewUser;

@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserValidationService validationService;

    public UserDto registerUser(RegistrationRequest registrationRequest) {

        validationService.verifyAvailability(registrationRequest.email());

        User user = User.builder()
                .email(registrationRequest.email())
                .password(registrationRequest.password())
                .enabled(enabledForNewUser())
                .build();

        return userRepository.save(user).toDto();
    }

    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDto getUserById(Long id){
        return userRepository.findById(id)
                .map(User::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public void enableAppUser(Long id) {
        userRepository.enableAppUser(id);
    }
}
