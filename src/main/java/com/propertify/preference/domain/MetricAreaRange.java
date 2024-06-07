package com.propertify.preference.domain;

import com.propertify.preference.dto.MetricAreaRangeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Builder
@AllArgsConstructor
class MetricAreaRange {

    private Integer startMetricArea;
    private Integer endMetricArea;

    MetricAreaRangeDto toDto() {
        return MetricAreaRangeDto.builder()
                .startMetricArea(startMetricArea)
                .endMetricArea(endMetricArea)
                .build();
    }
}
