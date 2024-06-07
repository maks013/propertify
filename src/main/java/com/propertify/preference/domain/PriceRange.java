package com.propertify.preference.domain;

import com.propertify.preference.dto.PriceRangeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
class PriceRange {

    private Integer startPrice;
    private Integer endPrice;

    PriceRangeDto toDto() {
        return PriceRangeDto.builder()
                .startPrice(startPrice)
                .endPrice(endPrice)
                .build();
    }
}
