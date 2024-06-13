package com.propertify.preference.domain;

import com.propertify.preference.dto.MetricAreaRangeDto;
import com.propertify.preference.dto.PreferenceDto;
import com.propertify.preference.dto.PreferenceRequestDto;
import com.propertify.preference.dto.PriceRangeDto;
import com.propertify.preference.exception.PreferencesNotFoundException;
import com.propertify.realestate.exception.InvalidParameterRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreferenceFacadeTest {

    private final RangeMapper rangeMapper = new RangeMapper();
    private final InMemoryPreferenceRepository inMemoryPreferenceRepository = new InMemoryPreferenceRepository();

    private final PreferenceFacade preferenceFacade = new PreferenceFacade(
            inMemoryPreferenceRepository,
            rangeMapper,
            new PreferenceUpdater(rangeMapper)
    );

    private final Preference preference1 = new Preference(
            1L, new PriceRange(250000, 500000), new MetricAreaRange(60, 100), "Kraków", 1L
    );

    private final Preference preference2 = new Preference(
            2L, new PriceRange(250000, 500000), new MetricAreaRange(60, 100), "Kraków", 2L
    );

    @BeforeEach
    void setUp() {
        inMemoryPreferenceRepository.addPreferences(preference1);
        inMemoryPreferenceRepository.addPreferences(preference2);
    }

    @Test
    void should_get_all_preferences() {

        // given
        final int sizeOfAll = preferenceFacade.getAllPreferences().size();

        // when
        // then
        assertEquals(2, sizeOfAll);
    }

    @Test
    void should_add_new_preference() {

        // given
        PreferenceRequestDto preferenceRequestDto = new PreferenceRequestDto(
                3L, new PriceRangeDto(400000, 1000000),
                new MetricAreaRangeDto(100, 200), "");

        // when
        int sizeBefore = preferenceFacade.getAllPreferences().size();
        preferenceFacade.addNewPreferences(preferenceRequestDto);
        int sizeAfter = preferenceFacade.getAllPreferences().size();

        // then
        assertEquals(1, sizeAfter - sizeBefore);
    }

    @Test
    void should_throw_exception_when_adding_preferences_with_invalid_parameters_range() {

        // given
        final int startPriceBiggerThanEnd = 200000;
        final int endPriceLessThanStart = 100000;

        PreferenceRequestDto preferenceRequestDto = new PreferenceRequestDto(
                3L, new PriceRangeDto(startPriceBiggerThanEnd, endPriceLessThanStart),
                new MetricAreaRangeDto(100, 200), "");
        // when
        // then
        assertThrows(InvalidParameterRangeException.class,
                () -> preferenceFacade.addNewPreferences(preferenceRequestDto));
    }

    @Test
    void should_throw_exception_when_preference_not_found() {

        // given
        final long notExistingUserId = 9999L;

        // when
        // then
        assertThrows(PreferencesNotFoundException.class, () -> preferenceFacade.getPreferencesByUserId(notExistingUserId)
                , "No preference found for the specified user ID: " + notExistingUserId);
    }

    @Test
    void should_update_preferences() {

        // given
        final long userId = 2L;

        PreferenceRequestDto preferenceRequestDto = new PreferenceRequestDto(
                userId, new PriceRangeDto(400000, 1000000),
                new MetricAreaRangeDto(100, 200), "");

        // when
        PreferenceDto beforeUpdate = preferenceFacade.getPreferencesByUserId(userId);
        preferenceFacade.updatePreferences(preferenceRequestDto);
        PreferenceDto afterUpdate = preferenceFacade.getPreferencesByUserId(userId);

        // then
        assertAll(
                () -> assertEquals("", afterUpdate.cityName()),
                () -> assertNotEquals(beforeUpdate.cityName(), afterUpdate.cityName()),
                () -> assertEquals(400000, afterUpdate.priceRange().startPrice()),
                () -> assertNotEquals(beforeUpdate.priceRange().startPrice(), afterUpdate.priceRange().startPrice()),
                () -> assertEquals(100, afterUpdate.metricAreaRange().startMetricArea()),
                () -> assertNotEquals(beforeUpdate.metricAreaRange().startMetricArea(), afterUpdate.metricAreaRange().startMetricArea())
        );
    }

    @Test
    void should_throw_exception_when_updating_preferences_with_invalid_parameters_range() {

        // given
        final int startMetricAreaBiggerThanEnd = 100;
        final int endMetricAreaRangeLessThanStart = 50;

        PreferenceRequestDto preferenceRequestDto = new PreferenceRequestDto(
                2L, new PriceRangeDto(100000, 200000),
                new MetricAreaRangeDto(startMetricAreaBiggerThanEnd,
                        endMetricAreaRangeLessThanStart), "");
        // when
        // then
        assertThrows(InvalidParameterRangeException.class,
                () -> preferenceFacade.updatePreferences(preferenceRequestDto));
    }
}
