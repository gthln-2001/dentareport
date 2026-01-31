package de.dentareport.utils.string;

import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DateStringUtilsTest {

    @Test
    public void it_validates_a_date() {
        assertThat(DateStringUtils.isValidDate("2000-01-01")).isTrue();
        assertThat(DateStringUtils.isValidDate("2016-02-29")).isTrue();
        assertThat(DateStringUtils.isValidDate("2015-02-29")).isFalse();
        assertThat(DateStringUtils.isValidDate("2000-02-31")).isFalse();
        assertThat(DateStringUtils.isValidDate("2000-03-32")).isFalse();
        assertThat(DateStringUtils.isValidDate("          ")).isFalse();
        assertThat(DateStringUtils.isValidDate("LabelElement       ")).isFalse();
        assertThat(DateStringUtils.isValidDate("2000-01-0X")).isFalse();
        assertThat(DateStringUtils.isValidDate("2000X01X01")).isFalse();
        assertThat(DateStringUtils.isValidDate("2000      ")).isFalse();
        assertThat(DateStringUtils.isValidDate("    -  -  ")).isFalse();
        assertThat(DateStringUtils.isValidDate("")).isFalse();
    }

    @Test
    public void it_checks_if_a_date_is_after_another_date() {
        assertThat(DateStringUtils.isAfter("2000-10-01", "1990-10-01")).isTrue();
        assertThat(DateStringUtils.isAfter("1990-10-01", "2000-10-01")).isFalse();
        assertThat(DateStringUtils.isAfter("2000-10-01", "2000-10-01")).isFalse();
        assertThat(DateStringUtils.isAfter("2000-10-01", Keys.NO_DATA)).isTrue();
        assertThat(DateStringUtils.isAfter(Keys.NO_DATA, "2000-10-01")).isFalse();
        assertThat(DateStringUtils.isAfter(Keys.NO_DATA, Keys.NO_DATA)).isFalse();
    }

    @Test
    public void it_checks_if_a_date_is_from_another_date() {
        assertThat(DateStringUtils.isFrom("2000-10-01", "1990-10-01")).isTrue();
        assertThat(DateStringUtils.isFrom("1990-10-01", "2000-10-01")).isFalse();
        assertThat(DateStringUtils.isFrom("2000-10-01", "2000-10-01")).isTrue();
        assertThat(DateStringUtils.isFrom("2000-10-01", Keys.NO_DATA)).isTrue();
        assertThat(DateStringUtils.isFrom(Keys.NO_DATA, "2000-10-01")).isFalse();
        assertThat(DateStringUtils.isFrom(Keys.NO_DATA, Keys.NO_DATA)).isFalse();
    }

    @Test
    public void it_checks_if_a_date_is_before_another_date() {
        assertThat(DateStringUtils.isBefore("1990-10-01", "2000-10-01")).isTrue();
        assertThat(DateStringUtils.isBefore("2000-10-01", "1990-10-01")).isFalse();
        assertThat(DateStringUtils.isBefore("2000-10-01", "2000-10-01")).isFalse();
        assertThat(DateStringUtils.isBefore("2000-10-01", Keys.NO_DATA)).isTrue();
        assertThat(DateStringUtils.isBefore(Keys.NO_DATA, "2000-10-01")).isFalse();
        assertThat(DateStringUtils.isBefore(Keys.NO_DATA, Keys.NO_DATA)).isFalse();
    }

    @Test
    public void it_checks_if_a_date_is_until_another_date() {
        assertThat(DateStringUtils.isUntil("1990-10-01", "2000-10-01")).isTrue();
        assertThat(DateStringUtils.isUntil("2000-10-01", "1990-10-01")).isFalse();
        assertThat(DateStringUtils.isUntil("2000-10-01", "2000-10-01")).isTrue();
        assertThat(DateStringUtils.isUntil("2000-10-01", Keys.NO_DATA)).isTrue();
        assertThat(DateStringUtils.isUntil(Keys.NO_DATA, "2000-10-01")).isFalse();
        assertThat(DateStringUtils.isUntil(Keys.NO_DATA, Keys.NO_DATA)).isFalse();
    }

    @Test
    public void it_converts_date_from_charly_to_gregorian_date_string() {
        assertThat(DateStringUtils.convertDateFromCharlyToGregorian("2437843")).isEqualTo("1962-06-28");
        assertThat(DateStringUtils.convertDateFromCharlyToGregorian("2450426")).isEqualTo("1996-12-09");
        assertThat(DateStringUtils.convertDateFromCharlyToGregorian("2451589")).isEqualTo("2000-02-15");
        assertThat(DateStringUtils.convertDateFromCharlyToGregorian("2451933")).isEqualTo("2001-01-24");
        assertThat(DateStringUtils.convertDateFromCharlyToGregorian("2452953")).isEqualTo("2003-11-10");
        assertThat(DateStringUtils.convertDateFromCharlyToGregorian("2453268")).isEqualTo("2004-09-20");
    }

    @Test
    public void it_calculates_days_between_two_dates() {
        assertThat(DateStringUtils.daysBetween("2010-10-01", "2010-10-05")).isEqualTo("4");
        assertThat(DateStringUtils.daysBetween("2010-10-01", "2010-10-01")).isEqualTo("0");
        assertThat(DateStringUtils.daysBetween("2010-10-05", "2010-10-01")).isEqualTo("-4");
        assertThat(DateStringUtils.daysBetween("no_date", "2010-10-01")).isEqualTo(Keys.NO_DATA);
        assertThat(DateStringUtils.daysBetween("2010-10-05", "no_date")).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_calculates_days_between_two_dates_with_offset() {
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-01", "2010-10-05", 0)).isEqualTo("4");
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-01", "2010-10-05", -1)).isEqualTo("3");
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-01", "2010-10-05", 2)).isEqualTo("6");
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-01", "2010-10-01", 0)).isEqualTo("0");
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-01", "2010-10-01", -1)).isEqualTo("-1");
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-01", "2010-10-01", 4)).isEqualTo("4");
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-05", "2010-10-01", 0)).isEqualTo("-4");
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-05", "2010-10-01", -2)).isEqualTo("-6");
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-05", "2010-10-01", 6)).isEqualTo("2");
        assertThat(DateStringUtils.daysBetweenWithOffset("no_date", "2010-10-01", 0)).isEqualTo(Keys.NO_DATA);
        assertThat(DateStringUtils.daysBetweenWithOffset("2010-10-05", "no_date", 0)).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_calculates_days_between_two_dates_with_fallback_date() {
        assertThat(DateStringUtils.daysBetween("2010-10-01", "2010-10-05", "2010-10-06")).isEqualTo("4");
        assertThat(DateStringUtils.daysBetween("2010-10-01", "2010-10-01", "2010-10-06")).isEqualTo("0");
        assertThat(DateStringUtils.daysBetween("2010-10-05", "2010-10-01", "2010-10-06")).isEqualTo("-4");
        assertThat(DateStringUtils.daysBetween("2010-10-01", Keys.NO_DATA, "2010-10-06")).isEqualTo("5");
        assertThat(DateStringUtils.daysBetween("2010-10-01", "2010-10-05", "no_date")).isEqualTo("4");
        assertThat(DateStringUtils.daysBetween("2010-10-01", Keys.NO_DATA, "no_date")).isEqualTo(Keys.NO_DATA);
        assertThat(DateStringUtils.daysBetween("2010-10-01", "no_date", "2010-10-06")).isEqualTo(Keys.NO_DATA);
        assertThat(DateStringUtils.daysBetween("no_date", "2010-10-01", "2010-10-06")).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_calculates_years_between_two_dates() {
        assertThat(DateStringUtils.yearsBetween("2010-10-01", "2014-10-01")).isEqualTo("4");
        assertThat(DateStringUtils.yearsBetween("2010-10-01", "2010-10-01")).isEqualTo("0");
        assertThat(DateStringUtils.yearsBetween("2014-10-01", "2010-10-01")).isEqualTo("-4");
        assertThat(DateStringUtils.yearsBetween("2014-10-01", "2015-09-30")).isEqualTo("0");
        assertThat(DateStringUtils.yearsBetween("2014-10-01", "2016-09-30")).isEqualTo("1");
        assertThat(DateStringUtils.yearsBetween("no_date", "2016-09-30")).isEqualTo(Keys.NO_DATA);
        assertThat(DateStringUtils.yearsBetween("2014-10-01", "no_date")).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_calculates_age_at_given_date() {
        assertThat(DateStringUtils.age("2010-10-01", "2014-10-01")).isEqualTo("4");
        assertThat(DateStringUtils.age("2010-10-01", "2010-10-01")).isEqualTo("0");
        assertThat(DateStringUtils.age("2014-10-01", "2010-10-01")).isEqualTo(Keys.NO_DATA);
        assertThat(DateStringUtils.age("2014-10-01", "2015-09-30")).isEqualTo("0");
        assertThat(DateStringUtils.age("2014-10-01", "2016-09-30")).isEqualTo("1");
        assertThat(DateStringUtils.age("no_date", "2016-09-30")).isEqualTo(Keys.NO_DATA);
        assertThat(DateStringUtils.age("2014-10-01", "no_date")).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_reformats_sql_date_to_german_format() {
        assertThat(DateStringUtils.reformatSqlToGerman("1990-10-01")).isEqualTo("01.10.1990");
        assertThat(DateStringUtils.reformatSqlToGerman("1990-10-99")).isEqualTo("1990-10-99");
        assertThat(DateStringUtils.reformatSqlToGerman("foo")).isEqualTo("foo");
    }

    // TODO: Fix test
//    @Test
//    public void it_gets_current_date_nicely_formated(@Mocked Date mockDate,
//                                                     @Mocked SimpleDateFormat mockSimpleDateFormat) {
//        new Expectations() {{
//            new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(new Date());
//            result = "some-nice-date";
//        }};
//
//        assertThat(DateStringUtils.now()).isEqualTo("some-nice-date");
//    }

    @Test
    public void it_groups_age_by_decade() {
        assertThat(DateStringUtils.groupAgeByDecade("0")).isEqualTo("0-19");
        assertThat(DateStringUtils.groupAgeByDecade("5")).isEqualTo("0-19");
        assertThat(DateStringUtils.groupAgeByDecade("19")).isEqualTo("0-19");
        assertThat(DateStringUtils.groupAgeByDecade("20")).isEqualTo("20-29");
        assertThat(DateStringUtils.groupAgeByDecade("25")).isEqualTo("20-29");
        assertThat(DateStringUtils.groupAgeByDecade("29")).isEqualTo("20-29");
        assertThat(DateStringUtils.groupAgeByDecade("30")).isEqualTo("30-39");
        assertThat(DateStringUtils.groupAgeByDecade("76")).isEqualTo("70-79");
        assertThat(DateStringUtils.groupAgeByDecade("123")).isEqualTo("120-129");
        assertThat(DateStringUtils.groupAgeByDecade("no_data")).isEqualTo("no_data");
        assertThat(DateStringUtils.groupAgeByDecade("foo")).isEqualTo("no_data");
    }

    @Test
    public void it_gets_year_from_date() {
        assertThat(DateStringUtils.yearOf("")).isEqualTo("");
        assertThat(DateStringUtils.yearOf(Keys.NO_DATA)).isEqualTo(Keys.NO_DATA);
        assertThat(DateStringUtils.yearOf("1982-12-01")).isEqualTo("1982");
    }
}