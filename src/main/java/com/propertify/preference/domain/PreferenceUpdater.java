package com.propertify.preference.domain;

import com.propertify.preference.dto.PreferenceRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PreferenceUpdater {

    private final RangeMapper rangeMapper;

    void updatePreferences(Preference preferenceToUpdate, PreferenceRequestDto preferenceRequestDto) {

        preferenceToUpdate
                .setPriceRange(rangeMapper
                        .priceRangeFromDto(preferenceRequestDto.priceRange()));

        preferenceToUpdate
                .setMetricAreaRange(rangeMapper
                        .metricAreaRangeFromDto(preferenceRequestDto.metricAreaRange()));

        preferenceToUpdate
                .setCityName(PreferencesValuesValidator.validateCityName(preferenceRequestDto.cityName()));

    }
}
