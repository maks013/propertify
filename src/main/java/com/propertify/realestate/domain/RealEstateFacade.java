package com.propertify.realestate.domain;

import com.propertify.preference.dto.MetricAreaRangeDto;
import com.propertify.preference.dto.PreferenceDto;
import com.propertify.preference.dto.PriceRangeDto;
import com.propertify.realestate.dto.RealEstateDto;
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

    public List<RealEstateDto> getAllRealEstatesInPriceRange(PriceRangeDto priceRangeDto) {
        return findRealEstatesInRange(priceRangeDto.startPrice(), priceRangeDto.endPrice(), realEstateRepository::findAllInPriceRange);
    }

    public List<RealEstateDto> getAllRealEstatesInPriceBySquareMeterRange(PriceRangeDto priceRangeDto) {
        return findRealEstatesInRange(priceRangeDto.startPrice(), priceRangeDto.endPrice(), realEstateRepository::findAllInPriceBySquareMeterRange);
    }

    public List<RealEstateDto> getAllRealEstatesInMetricAreaRange(MetricAreaRangeDto metricAreaRangeDto) {
        return findRealEstatesInRange(metricAreaRangeDto.startMetricArea(), metricAreaRangeDto.endMetricArea(), realEstateRepository::findAllInMetricAreaRange);
    }

    public List<RealEstateDto> getAllRealEstatesByAllParams(PreferenceDto preferenceDto) {

        Integer startPrice = validateStartValue(preferenceDto.priceRange().startPrice());
        Integer endPrice = validateEndValue(preferenceDto.priceRange().endPrice());
        validateRangeParameters(startPrice, endPrice);

        Integer startMetricArea = validateStartValue(preferenceDto.metricAreaRange().startMetricArea());
        Integer endMetricArea = validateEndValue(preferenceDto.metricAreaRange().endMetricArea());
        validateRangeParameters(startMetricArea, endMetricArea);

        String cityName = validateCityName(preferenceDto.cityName());

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
