package com.propertify.user.domain;

import java.util.Optional;

interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    void enableAppUser(Long userId);
}
