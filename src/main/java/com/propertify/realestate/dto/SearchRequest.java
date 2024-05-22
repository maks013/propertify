package com.propertify.realestate.dto;

public record SearchRequest(String city,
                            Integer totalPriceStart,
                            Integer totalPriceEnd,
                            Integer metricAreaStart,
                            Integer metricAreaEnd
) {
}
