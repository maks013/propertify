package com.propertify.user.domain;

import com.propertify.user.dto.RegistrationRequest;
import com.propertify.user.dto.UserDto;
import com.propertify.user.exception.InvalidEmailFormatException;
import com.propertify.user.exception.TakenEmailException;
import com.propertify.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserFacadeTest {

    private InMemoryUserRepository inMemRepo = new InMemoryUserRepository();

    public UserFacade userFacade = new UserFacade(inMemRepo, new UserDataValidator(inMemRepo));

    private final User USER1 = new User(1L, "email1@example.com",
            "password1", true);
    private final User USER2 = new User(2L, "email2@example.com",
            "password2", true);

    public void setUp() {
        inMemRepo.addUser(USER1);
        inMemRepo.addUser(USER2);
    }

    @Test
    void should_throw_exception_when_user_try_to_register_with_invalid_email_format() {

        // given
        RegistrationRequest userToSaveWithInvalidEmail = RegistrationRequest.builder()
                .email("example")
                .password("password")
                .build();

        // when
        // then
        assertThrows(InvalidEmailFormatException.class, () -> userFacade.registerUser(userToSaveWithInvalidEmail));
    }

    @Test
    void should_throw_exception_when_user_try_to_register_with_existing_email() {

        // given
        RegistrationRequest userToSave = RegistrationRequest.builder()
                .email("example@mail.com")
                .password("password")
                .build();

        RegistrationRequest userWithTheSameEmail = RegistrationRequest.builder()
                .email("example@mail.com")
                .password("password")
                .build();

        // when
        userFacade.registerUser(userToSave);

        // then
        assertThrows(TakenEmailException.class, () -> userFacade.registerUser(userWithTheSameEmail));
    }

    @Test
    void should_register_user_successfully() {

        // given
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("new@mail.com")
                .password("password")
                .build();

        // when
        UserDto registeredUser = userFacade.registerUser(registrationRequest);

        // then
        assertAll(
                () -> assertEquals(registrationRequest.email(), registeredUser.email())
        );
    }

    @Test
    void should_throw_userNotFound_when_user_with_that_email_not_existing() {

        // given
        String notExistingEmail = "notExistingExample@mail.com";

        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserByEmail(notExistingEmail));
    }

    @Test
    void should_find_user_by_email() {

        // given
        final String email = "new@mail.com";
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email(email)
                .password("password")
                .build();

        // when
        userFacade.registerUser(registrationRequest);
        UserDto userDto = userFacade.getUserByEmail(email);

        // then
        assertAll(
                () -> assertEquals(registrationRequest.email(), userDto.email()),
                () -> assertEquals(1L, userDto.id())
        );
    }

    @Test
    void should_throw_userNotFound_when_user_with_that_id_not_existing() {

        // given
        Long notExistingId = 9999L;

        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserById(notExistingId));
    }

    @Test
    void should_find_user_by_id() {

        // given
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("new@email.com")
                .password("password")
                .build();

        // when
        userFacade.registerUser(registrationRequest);
        UserDto userDto = userFacade.getUserById(1L);

        // then
        assertAll(
                () -> assertEquals(registrationRequest.email(), userDto.email()),
                () -> assertEquals(1L, userDto.id())
        );
    }

    @Test
    void should_enable_user() {

        // given
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("user@mail.com")
                .password("password")
                .build();

        // when
        UserDto registeredUser = userFacade.registerUser(registrationRequest);

        userFacade.enableAppUser(registeredUser.id());

        // then
        assertTrue(userFacade.getUserByEmail(registeredUser.email()).enabled());
    }

}
