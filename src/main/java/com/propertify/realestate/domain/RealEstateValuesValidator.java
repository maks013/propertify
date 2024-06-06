package com.propertify.realestate.domain;

import com.propertify.realestate.exception.InvalidParameterRangeException;

public interface RealEstateValuesValidator {

    static int validateStartValue(Integer startValue) {
        return (startValue == null) || (startValue.describeConstable().isEmpty()) ? 0 : startValue;
    }

    static int validateEndValue(Integer endValue) {
        return (endValue == null) || endValue.describeConstable().isEmpty() ? 999999999 : endValue;
    }

    static String validateCityName(String cityName) {
        return cityName == null || cityName.isEmpty() ? "" : cityName;
    }

    static void validateRangeParameters(Integer startValue, Integer endValue) {
        if (startValue < 0 || endValue < 0 || startValue > endValue) {
            throw new InvalidParameterRangeException();
        }
    }
}
