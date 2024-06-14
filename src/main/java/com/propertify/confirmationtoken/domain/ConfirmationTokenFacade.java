package com.propertify.confirmationtoken.domain;

import com.propertify.confirmationtoken.dto.ConfirmationTokenDto;
import com.propertify.confirmationtoken.exception.TokenAlreadyConfirmedException;
import com.propertify.confirmationtoken.exception.TokenExpiredException;
import com.propertify.confirmationtoken.exception.TokenNotFoundException;
import com.propertify.user.domain.UserFacade;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConfirmationTokenFacade {

    private final UserFacade userFacade;
    private final ConfirmationTokenRepository tokenRepository;
    private final DateTimeProvider dateTimeProvider;

    private static final String CONFIRMATION_LINK = "http://localhost:8080/confirmation-token/confirm?token=";

    public List<ConfirmationTokenDto> readAllTokens() {
        return tokenRepository.findAll().stream()
                .map(ConfirmationToken::toDto)
                .collect(Collectors.toList());
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(TokenNotFoundException::new);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmedException();
        }

        if (confirmationToken.getExpiresAt().isBefore(dateTimeProvider.getCurrentDateTime())) {
            throw new TokenExpiredException();
        }

        tokenRepository.updateConfirmedAt(token, dateTimeProvider.getCurrentDateTime());

        userFacade.enableAppUser(confirmationToken.getUserId());
        return "confirmed";
    }

    public String createTokenLink(String email) {
        final String confirmationToken = UUID.randomUUID().toString();
        ConfirmationToken token = ConfirmationToken.builder()
                .token(confirmationToken)
                .createdAt(dateTimeProvider.getCurrentDateTime())
                .expiresAt(dateTimeProvider.getCurrentDateTime().plusMinutes(15))
                .userId(userFacade.getUserByEmail(email).id())
                .build();

        tokenRepository.save(token);
        return CONFIRMATION_LINK + confirmationToken;
    }

    public void deleteToken(Long id) {
        tokenRepository.deleteById(id);
    }
}
