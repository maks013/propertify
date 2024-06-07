package com.propertify.preference.dto;

import lombok.Builder;

@Builder
public record PreferenceRequestDto(Long userId,
                                   PriceRangeDto priceRange,
                                   MetricAreaRangeDto metricAreaRange,
                                   String cityName
) {
}
