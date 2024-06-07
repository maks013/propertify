package com.propertify.preference.domain;

import com.propertify.realestate.domain.RealEstateValuesValidator;

interface PreferencesValuesValidator {

    static int validateStartValue(Integer startValue) {
        return RealEstateValuesValidator.validateStartValue(startValue);
    }

    static int validateEndValue(Integer endValue) {
        return RealEstateValuesValidator.validateEndValue(endValue);
    }

    static String validateCityName(String cityName) {
        return RealEstateValuesValidator.validateCityName(cityName);
    }

    static void validateRangeParameters(Integer startValue, Integer endValue) {
        RealEstateValuesValidator.validateRangeParameters(startValue, endValue);
    }
}
