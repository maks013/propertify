package com.propertify.preference.domain;

import java.util.List;
import java.util.Optional;

interface PreferenceRepository {

    Preference save(Preference preference);

    Optional<Preference> findByUserId(Long id);

    List<Preference> findAll();
}
