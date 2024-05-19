package com.propertify.user.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryUserRepository implements UserRepository {

    private Map<Long, User> inMemoryRepo = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        long id = (long) inMemoryRepo.size() + 1L;
        user.setId(id);
        inMemoryRepo.put(id, user);
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        return inMemoryRepo.values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return inMemoryRepo.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(inMemoryRepo.get(id));
    }

    @Override
    public void enableAppUser(Long userId) {
        User user = inMemoryRepo.get(userId);
        user.enableUser();
    }
}
