package com.propertify.confirmationtoken.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

interface ConfirmationTokenRepository {

    ConfirmationToken save(ConfirmationToken confirmationToken);

    List<ConfirmationToken> findAll();

    void deleteById(Long id);

    Optional<ConfirmationToken> findByToken(String token);

    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
