package com.propertify.confirmationtoken.domain;

import com.propertify.confirmationtoken.exception.TokenNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryConfirmationTokenRepository implements ConfirmationTokenRepository {

    private Map<Long, ConfirmationToken> inMemoryRepo = new ConcurrentHashMap<>();

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        long id = inMemoryRepo.size() + 1;
        confirmationToken.setId(id);
        inMemoryRepo.put(id, confirmationToken);
        return confirmationToken;
    }

    @Override
    public List<ConfirmationToken> findAll() {
        return inMemoryRepo.values().stream().toList();
    }

    @Override
    public void deleteById(Long id) {
        inMemoryRepo.remove(id);
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        for (ConfirmationToken t : inMemoryRepo.values()) {
            if (t.getToken().equals(token)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateConfirmedAt(String token, LocalDateTime confirmedAt) {
        ConfirmationToken token1 = findByToken(token).orElseThrow(TokenNotFoundException::new);
        token1.setConfirmedAt(confirmedAt);
    }

    public void addConfirmationToken(ConfirmationToken confirmationToken) {
        inMemoryRepo.put(confirmationToken.getId(), confirmationToken);
    }
}
