package com.propertify.realestate.domain;


import com.propertify.realestate.dto.RealEstateDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class RealEstate {

    private Long id;
    private String title;
    private String city;
    private Double metricArea;
    private Double price;
    private Double pricePerSquareMeter;
    private String url;


    RealEstateDto toDto(){
        return RealEstateDto.builder()
                .title(title)
                .city(city)
                .metricArea(metricArea)
                .price(price)
                .pricePerSquareMeter(pricePerSquareMeter)
                .url(url)
                .build();
    }
}
