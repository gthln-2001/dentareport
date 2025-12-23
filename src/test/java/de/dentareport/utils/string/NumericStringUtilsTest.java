package de.dentareport.utils.string;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class NumericStringUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "0, year, 0, ''",
            "23, year, 100, ''",
            "23, year, 180, ''",
            "1, year, 365, '1,000'",
            "23, year, 365, '23,000'",
            "23, year, 366, '22,937'",
            "23, year, 700, '11,993'",
            "0, half_year, 0, ''",
            "23, half_year, 100, ''",
            "1, half_year, 182.5, '1,000'",
            "23, half_year, 182.5, '23,000'",
            "23, half_year, 183, '22,937'",
            "23, half_year, 700, '5,996'"
    })
    void it_calculates_count_per_timeframe(String count, String timeframe,
                                           String daysInTimeframe, String expected) {
        assertThat(NumericStringUtils.countPerTimeframe(count, timeframe, daysInTimeframe))
                .isEqualTo(expected);
    }
}
