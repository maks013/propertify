package com.propertify.realestate.domain;

import com.propertify.preference.dto.MetricAreaRangeDto;
import com.propertify.preference.dto.PreferenceDto;
import com.propertify.preference.dto.PriceRangeDto;
import com.propertify.realestate.dto.RealEstateDto;
import com.propertify.realestate.exception.InvalidParameterRangeException;
import com.propertify.realestate.exception.RealEstateNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RealEstateFacadeTest {

    private final InMemoryRealEstateRepository realEstateRepository = new InMemoryRealEstateRepository();

    private final RealEstateFacade realEstateFacade = new RealEstateFacade(realEstateRepository);

    private final RealEstate realEstate1 = new RealEstate(1L, "Number 1", "CityA", 124.0, 1350000.0, 10887.1, "https://example.com/offer1");
    private final RealEstate realEstate2 = new RealEstate(2L, "Number 2", "CityA", 48.0, 300000.0, 6250.0, "https://example.com/offer2");
    private final RealEstate realEstate3 = new RealEstate(3L, "Number 3", "CityB", 64.0, 480000.0, 7500.0, "https://example.com/offer3");

    @BeforeEach
    void setUp() {
        realEstateRepository.addRealEstate(realEstate1);
        realEstateRepository.addRealEstate(realEstate2);
        realEstateRepository.addRealEstate(realEstate3);
    }

    @Test
    void should_find_all_real_estates() {

        // given
        final int allRealEstatesSize = realEstateFacade.getAllRealEstates().size();

        // when
        // then
        assertEquals(3, allRealEstatesSize);
    }

    @Test
    void should_throw_exception_when_trying_to_get_real_estate_by_not_existing_id() {

        // given
        final long notExistingId = 9999L;

        // when
        // then
        assertThrows(RealEstateNotFoundException.class, () -> realEstateFacade.getRealEstateById(notExistingId));
    }

    @Test
    void should_find_real_estate_by_id() {

        // given
        final long realEstateId = realEstate1.getId();

        // when
        RealEstateDto realEstate = realEstateFacade.getRealEstateById(realEstateId);

        // then
        assertAll(
                () -> assertEquals(realEstate1.getTitle(), realEstate.title()),
                () -> assertEquals(realEstate1.getCity(), realEstate.city()),
                () -> assertEquals(realEstate1.getPrice(), realEstate.price()),
                () -> assertEquals(realEstate1.getUrl(), realEstate.url())
        );
    }

    @Test
    void should_find_all_real_estates_by_city_name() {

        // given
        final String cityName = "CityA";

        // when
        int sizeOfListWithRealEstates = realEstateFacade.getAllRealEstatesByCityName(cityName).size();

        // then
        assertEquals(2, sizeOfListWithRealEstates);
    }

    @Test
    void should_throw_exception_when_trying_to_get_real_estates_by_invalid_price_range() {

        // given
        final int startPrice = 250000;
        final int endPrice = 150000;
        PriceRangeDto priceRangeDto = new PriceRangeDto(startPrice, endPrice);

        // when
        // then
        assertThrows(InvalidParameterRangeException.class,
                () -> realEstateFacade.getAllRealEstatesInPriceRange(priceRangeDto));
    }

    @Test
    void should_find_all_real_estates_in_price_range() {

        // given
        final int startPrice = realEstate2.getPrice().intValue();
        final int endPrice = realEstate3.getPrice().intValue();
        PriceRangeDto priceRangeDto = new PriceRangeDto(startPrice, endPrice);

        // when
        int sizeOfListWithRealEstates = realEstateFacade
                .getAllRealEstatesInPriceRange(priceRangeDto).size();

        // then
        assertEquals(2, sizeOfListWithRealEstates);
    }

    @Test
    void should_find_all_real_estates_in_price_by_square_range() {

        // given
        final int startPrice = realEstate2.getPricePerSquareMeter().intValue();
        final int endPrice = realEstate3.getPricePerSquareMeter().intValue();
        PriceRangeDto priceRangeDto = new PriceRangeDto(startPrice, endPrice);

        // when
        int sizeOfListWithRealEstates = realEstateFacade
                .getAllRealEstatesInPriceBySquareMeterRange(priceRangeDto).size();

        // then
        assertEquals(2, sizeOfListWithRealEstates);
    }

    @Test
    void should_find_all_real_estates_in_metric_area_range() {

        // given
        final int startValue = 50;
        final int endValue = 130;
        MetricAreaRangeDto metricAreaRangeDto = new MetricAreaRangeDto(startValue, endValue);

        // when
        int sizeOfListWithRealEstates = realEstateFacade
                .getAllRealEstatesInMetricAreaRange(metricAreaRangeDto).size();

        // then
        assertEquals(2, sizeOfListWithRealEstates);
    }

    @Test
    void should_find_all_real_estates_by_search_request_with_all_params() {

        // given
        final PreferenceDto preferenceDto = PreferenceDto.builder()
                .priceRange(new PriceRangeDto(200000, 1500000))
                .metricAreaRange(new MetricAreaRangeDto(40, 130))
                .cityName("CityA")
                .userId(1L)
                .build();

        // when
        int sizeOfListWithRealEstates = realEstateFacade
                .getAllRealEstatesByAllParams(preferenceDto).size();

        // then
        assertEquals(2, sizeOfListWithRealEstates);
    }

    @Test
    void should_find_all_real_estates_by_search_request_without_all_params() {

        // given
        final PreferenceDto preferenceDto = PreferenceDto.builder()
                .priceRange(new PriceRangeDto(null, null))
                .metricAreaRange(new MetricAreaRangeDto(null, null))
                .cityName("CityB")
                .userId(1L)
                .build();

        // when
        int sizeOfListWithRealEstates = realEstateFacade
                .getAllRealEstatesByAllParams(preferenceDto).size();

        // then
        assertEquals(1, sizeOfListWithRealEstates);
    }
}
