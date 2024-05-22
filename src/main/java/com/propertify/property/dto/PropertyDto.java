package com.propertify.property.dto;

import lombok.Builder;

@Builder
public record PropertyDto(String title,
                          String city,
                          Double metricArea,
                          Double price,
                          Double pricePerSquareMeter,
                          String url
) {
}
