package com.propertify.realestate.domain;

import com.propertify.realestate.dto.RealEstateDto;
import com.propertify.realestate.dto.SearchRequest;
import com.propertify.realestate.exception.RealEstateNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.propertify.realestate.domain.RealEstateValuesValidator.*;

@RequiredArgsConstructor
public class RealEstateFacade {

    private final RealEstateRepository realEstateRepository;

    public List<RealEstateDto> getAllRealEstates() {
        return realEstateRepository.findAll()
                .stream()
                .map(RealEstate::toDto)
                .toList();
    }

    public RealEstateDto getRealEstateById(Long id) {
        return realEstateRepository
                .findById(id)
                .map(RealEstate::toDto)
                .orElseThrow(RealEstateNotFoundException::new);
    }

    public List<RealEstateDto> getAllRealEstatesByCityName(String cityName) {
        return realEstateRepository.findAllByCity(cityName)
                .stream()
                .map(RealEstate::toDto)
                .toList();
    }

    public List<RealEstateDto> getAllRealEstatesInPriceRange(Integer startPrice, Integer endPrice) {
        return findRealEstatesInRange(startPrice, endPrice, realEstateRepository::findAllInPriceRange);
    }

    public List<RealEstateDto> getAllRealEstatesInPriceBySquareMeterRange(Integer startPrice, Integer endPrice) {
        return findRealEstatesInRange(startPrice, endPrice, realEstateRepository::findAllInPriceBySquareMeterRange);
    }

    public List<RealEstateDto> getAllRealEstatesInMetricAreaRange(Integer startMetricArea, Integer endMetricArea) {
        return findRealEstatesInRange(startMetricArea, endMetricArea, realEstateRepository::findAllInMetricAreaRange);
    }

    public List<RealEstateDto> getAllRealEstatesByAllParams(SearchRequest searchRequest) {

        Integer startPrice = validateStartValue(searchRequest.totalPriceStart());
        Integer endPrice = validateEndValue(searchRequest.totalPriceEnd());
        validateRangeParameters(startPrice, endPrice);

        Integer startMetricArea = validateStartValue(searchRequest.metricAreaStart());
        Integer endMetricArea = validateEndValue(searchRequest.metricAreaEnd());
        validateRangeParameters(startMetricArea, endMetricArea);

        String cityName = validateCityName(searchRequest.city());

        return realEstateRepository.findAllByAllParams(
                        startPrice, endPrice, startMetricArea,
                        endMetricArea, cityName)
                .stream()
                .map(RealEstate::toDto)
                .toList();
    }

    private List<RealEstateDto> findRealEstatesInRange(Integer startValue, Integer endValue,
                                                       RangeFinder rangeFinder) {
        startValue = validateStartValue(startValue);
        endValue = validateEndValue(endValue);

        validateRangeParameters(startValue, endValue);

        return rangeFinder.findInRange(startValue, endValue)
                .stream()
                .map(RealEstate::toDto)
                .toList();
    }

    @FunctionalInterface
    interface RangeFinder {
        List<RealEstate> findInRange(Integer startValue, Integer endValue);
    }
}
