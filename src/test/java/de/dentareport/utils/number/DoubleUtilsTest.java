package de.dentareport.utils.number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleUtilsTest {

    @Test
    public void it_rounds_double_down_to_tenth_not_smaller_than_zero() {
        assertThat(DoubleUtils.roundDownToTenth(1.22)).isEqualTo(1.2);
        assertThat(DoubleUtils.roundDownToTenth(1.2999999)).isEqualTo(1.2);
        assertThat(DoubleUtils.roundDownToTenth(1.0999999)).isEqualTo(1);
        assertThat(DoubleUtils.roundDownToTenth(5.1)).isEqualTo(5.1);
        assertThat(DoubleUtils.roundDownToTenth(123)).isEqualTo(123.0);
        assertThat(DoubleUtils.roundDownToTenth(-2.567)).isEqualTo(0);
    }
}