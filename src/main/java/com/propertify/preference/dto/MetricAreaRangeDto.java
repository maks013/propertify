package com.propertify.preference.dto;

import lombok.Builder;

@Builder
public record MetricAreaRangeDto(Integer startMetricArea,
                                 Integer endMetricArea
) {
}
