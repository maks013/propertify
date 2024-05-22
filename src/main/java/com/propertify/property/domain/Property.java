package com.propertify.property.domain;


import com.propertify.property.dto.PropertyDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class Property {

    private Long id;
    private String title;
    private String city;
    private Double metricArea;
    private Double price;
    private Double pricePerSquareMeter;
    private String url;


    PropertyDto toDto(){
        return PropertyDto.builder()
                .title(title)
                .city(city)
                .metricArea(metricArea)
                .price(price)
                .pricePerSquareMeter(pricePerSquareMeter)
                .url(url)
                .build();
    }
}
