package com.propertify.realestate.domain;

import java.util.List;
import java.util.Optional;

interface RealEstateRepository {

    List<RealEstate> findAll();

    Optional<RealEstate> findById(Long id);

    List<RealEstate> findAllByCity(String city);

    List<RealEstate> findAllInPriceRange(Integer startPrice, Integer endPrice);

    List<RealEstate> findAllInPriceBySquareMeterRange(Integer startPrice, Integer endPrice);

    List<RealEstate> findAllInMetricAreaRange(Integer startMetricArea, Integer endMetricArea);

    List<RealEstate> findAllByAllParams(Integer startPrice, Integer endPrice, Integer startMetricArea, Integer endMetricArea, String cityName);

}
