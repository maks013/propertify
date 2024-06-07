package com.propertify.preference.domain;

import java.util.Optional;

interface PreferenceRepository {

    Preference save(Preference preference);

    Optional<Preference> findByUserId(Long id);
}
