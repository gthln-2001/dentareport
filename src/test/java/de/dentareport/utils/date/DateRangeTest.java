package de.dentareport.utils.date;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DateRangeTest {

    @Test
    public void it_gets_formatted_start_of_date_range() {
        DateRange dateRange = new DateRange("2000-10-01", "2002-12-20");

        assertThat(dateRange.formattedStart()).isEqualTo("01.10.2000");
    }

    @Test
    public void it_gets_formatted_end_of_date_range() {
        DateRange dateRange = new DateRange("2000-10-01", "2002-12-20");

        assertThat(dateRange.formattedEnd()).isEqualTo("20.12.2002");
    }
}