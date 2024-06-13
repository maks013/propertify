package com.propertify.preference.dto;

import lombok.Builder;

@Builder
public record PreferenceDto(PriceRangeDto priceRange,
                            MetricAreaRangeDto metricAreaRange,
                            String cityName,
                            Long userId
) {
}
