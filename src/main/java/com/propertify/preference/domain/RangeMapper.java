package com.propertify.preference.domain;

import com.propertify.preference.dto.MetricAreaRangeDto;
import com.propertify.preference.dto.PriceRangeDto;

class RangeMapper {

    PriceRange priceRangeFromDto(PriceRangeDto priceRangeDto) {

        PreferencesValuesValidator.validateRangeParameters(
                priceRangeDto.startPrice(),
                priceRangeDto.endPrice()
        );

        return PriceRange.builder()
                .startPrice(PreferencesValuesValidator
                        .validateStartValue(priceRangeDto.startPrice()))
                .endPrice(PreferencesValuesValidator
                        .validateEndValue(priceRangeDto.endPrice()))
                .build();
    }

    MetricAreaRange metricAreaRangeFromDto(MetricAreaRangeDto metricAreaRangeDto) {

        PreferencesValuesValidator.validateRangeParameters(
                metricAreaRangeDto.startMetricArea(),
                metricAreaRangeDto.endMetricArea()
        );

        return MetricAreaRange.builder()
                .startMetricArea(PreferencesValuesValidator
                        .validateStartValue(metricAreaRangeDto.startMetricArea()))
                .endMetricArea(PreferencesValuesValidator.
                        validateEndValue(metricAreaRangeDto.endMetricArea()))
                .build();
    }
}
