package com.propertify.confirmationtoken.domain;

import com.propertify.confirmationtoken.exception.TokenAlreadyConfirmedException;
import com.propertify.confirmationtoken.exception.TokenExpiredException;
import com.propertify.confirmationtoken.exception.TokenNotFoundException;
import com.propertify.user.domain.UserFacadeTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ConfirmationTokenFacadeTest {

    private final UserFacadeTest userFacadeTest = new UserFacadeTest();
    private final InMemoryConfirmationTokenRepository confirmationTokenRepository = new InMemoryConfirmationTokenRepository();
    private static DateTimeProvider dateTimeProvider = new DateTimeProvider();

    private final ConfirmationTokenFacade confirmationTokenFacade = new ConfirmationTokenFacade(userFacadeTest.userFacade, confirmationTokenRepository, dateTimeProvider);

    private void setDateTimeProvider(DateTimeProvider dateTimeProvider) {
        ConfirmationTokenFacadeTest.dateTimeProvider = dateTimeProvider;
    }

    private final ConfirmationToken token1 = new ConfirmationToken(1L, "111",
            LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), null, 1L);
    private final ConfirmationToken token2 = new ConfirmationToken(2L, "222",
            LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), LocalDateTime.now().plusMinutes(10), 2L);


    @BeforeEach
    void setUp() {
        userFacadeTest.setUp();
        confirmationTokenRepository.addConfirmationToken(token1);
        confirmationTokenRepository.addConfirmationToken(token2);
    }

    @Test
    void should_find_all_tokens() {

        // given
        final int size = confirmationTokenFacade.readAllTokens().size();

        // when
        // then
        assertEquals(2, size);
    }

    @Test
    void should_throw_exception_while_confirming_token_when_it_is_confirmed() {

        // given
        final String token = "111";

        // when
        confirmationTokenFacade.confirmToken(token);

        // then
        assertThrows(TokenAlreadyConfirmedException.class, () -> confirmationTokenFacade.confirmToken(token));
    }

    @Test
    void should_delete_token_successfully() {

        // given
        final String token = confirmationTokenFacade.createTokenLink("email1@example.com");

        // when
        confirmationTokenFacade.deleteToken(1L);

        // then
        assertThrows(TokenNotFoundException.class, () -> confirmationTokenFacade.confirmToken(token));
    }

    @Test
    void should_create_token_link_successfully() {

        // given
        final String email = "email1@example.com";
        final String expectedLink = "http://localhost:8080/confirmation-token/confirm?token=";

        // when
        String tokenLink = confirmationTokenFacade.createTokenLink(email);
        // then
        assertTrue(tokenLink.startsWith(expectedLink));
    }

    @Test
    void should_confirm_token_successfully() {

        // given
        // when
        String result = confirmationTokenFacade.confirmToken("111");

        // then
        assertEquals("confirmed", result);
    }

    @Test
    void should_throw_token_expired_exception_when_confirming_token_when_is_expired() {

        // given
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiredDateTime = currentDateTime.minusMinutes(30);

        // and
        DateTimeProvider mockedDateTimeProvider = Mockito.mock(DateTimeProvider.class);
        when(mockedDateTimeProvider.getCurrentDateTime()).thenReturn(currentDateTime);

        setDateTimeProvider(mockedDateTimeProvider);

        // when
        ConfirmationToken expiredToken = ConfirmationToken.builder()
                .token("expiredToken")
                .createdAt(currentDateTime.minusHours(1))
                .expiresAt(expiredDateTime)
                .userId(3L)
                .build();

        confirmationTokenRepository.save(expiredToken);

        // then
        assertThrows(TokenExpiredException.class, () -> confirmationTokenFacade.confirmToken("expiredToken"));
    }

}
