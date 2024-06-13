package com.propertify.preference.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryPreferenceRepository implements PreferenceRepository {

    private Map<Long, Preference> inMemoryRepo = new ConcurrentHashMap<>();

    @Override
    public Preference save(Preference preference) {
        long id = inMemoryRepo.size() + 1;
        preference.setId(id);
        inMemoryRepo.put(id, preference);
        return preference;
    }

    @Override
    public Optional<Preference> findByUserId(Long id) {
        return inMemoryRepo.values()
                .stream()
                .filter(preference -> preference.getUserId().equals(id))
                .findFirst();
    }

    @Override
    public List<Preference> findAll() {
        return inMemoryRepo.values().stream().toList();
    }

    void addPreferences(Preference preference) {
        inMemoryRepo.put(preference.getId(), preference);
    }

}
