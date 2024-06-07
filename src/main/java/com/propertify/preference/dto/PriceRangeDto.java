package com.propertify.preference.dto;

import lombok.Builder;

@Builder
public record PriceRangeDto(Integer startPrice,
                            Integer endPrice
) {
}
