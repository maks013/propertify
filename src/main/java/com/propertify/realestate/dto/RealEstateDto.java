package com.propertify.realestate.dto;

import lombok.Builder;

@Builder
public record RealEstateDto(String title,
                            String city,
                            Double metricArea,
                            Double price,
                            Double pricePerSquareMeter,
                            String url
) {
}
