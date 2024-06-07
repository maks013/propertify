package com.propertify.preference.domain;

import com.propertify.preference.dto.PreferenceDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
class Preference {

    private Long id;
    private PriceRange priceRange;
    private MetricAreaRange metricAreaRange;
    private String cityName;
    private Long userId;

    PreferenceDto toDto() {
        return PreferenceDto.builder()
                .priceRange(priceRange.toDto())
                .metricAreaRange(metricAreaRange.toDto())
                .cityName(cityName)
                .build();
    }
}
