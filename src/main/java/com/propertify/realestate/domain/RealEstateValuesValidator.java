package com.propertify.realestate.domain;

import com.propertify.realestate.exception.InvalidParameterRangeException;

public interface RealEstateValuesValidator {

    static int validateStartValue(Integer startValue){
        return (startValue == null) ? 0 : startValue;
    }

    static int validateEndValue(Integer endValue){
        return (endValue == null) ? 999999999 : endValue;
    }

    static void validateRangeParameters(Integer startValue, Integer endValue) {
        if (startValue < 0 || endValue < 0 || startValue > endValue) {
            throw new InvalidParameterRangeException();
        }
    }
}
