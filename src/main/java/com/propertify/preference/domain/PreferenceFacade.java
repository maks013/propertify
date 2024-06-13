package com.propertify.preference.domain;

import com.propertify.preference.dto.PreferenceDto;
import com.propertify.preference.dto.PreferenceRequestDto;
import com.propertify.preference.exception.PreferencesNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PreferenceFacade {

    private final PreferenceRepository preferenceRepository;
    private final RangeMapper rangeMapper;
    private final PreferenceUpdater preferenceUpdater;

    public List<PreferenceDto> getAllPreferences() {
        return preferenceRepository.findAll()
                .stream()
                .map(Preference::toDto)
                .toList();
    }

    public PreferenceDto getPreferencesByUserId(Long userId) {
        return preferenceRepository
                .findByUserId(userId)
                .orElseThrow(() -> new PreferencesNotFoundException(userId))
                .toDto();
    }

    public PreferenceDto addNewPreferences(PreferenceRequestDto preferenceRequestDto) {

        final Preference newPreference = Preference.builder()
                .priceRange(rangeMapper.priceRangeFromDto(
                        preferenceRequestDto.priceRange()))
                .metricAreaRange(rangeMapper.metricAreaRangeFromDto(
                        preferenceRequestDto.metricAreaRange()))
                .cityName(PreferencesValuesValidator
                        .validateCityName(preferenceRequestDto.cityName()))
                .build();

        return preferenceRepository.save(newPreference).toDto();
    }

    public PreferenceDto updatePreferences(PreferenceRequestDto preferenceRequestDto) {

        final Preference preferenceToUpdate = preferenceRepository
                .findByUserId(preferenceRequestDto.userId())
                .orElseThrow();

        preferenceUpdater.updatePreferences(preferenceToUpdate, preferenceRequestDto);

        return preferenceRepository.save(preferenceToUpdate).toDto();
    }
}
