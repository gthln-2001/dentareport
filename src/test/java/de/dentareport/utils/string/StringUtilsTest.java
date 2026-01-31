package de.dentareport.utils.string;

import de.dentareport.utils.Keys;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class StringUtilsTest {

    @Test
    public void it_left_pads_a_given_string_with_given_pad_string() {
        String str = "FooBar";

        assertThat(StringUtils.leftPad(str, 10, "X")).isEqualTo("XXXXFooBar");
        assertThat(StringUtils.leftPad(str, 6, "X")).isEqualTo("FooBar");
        assertThat(StringUtils.leftPad(str, 5, "X")).isEqualTo("FooBar");
    }

    @Test
    public void it_left_pads_a_given_string_with_spaces() {
        String str = "FooBar";

        assertThat(StringUtils.leftPad(str, 10)).isEqualTo("    FooBar");
        assertThat(StringUtils.leftPad(str, 6)).isEqualTo("FooBar");
        assertThat(StringUtils.leftPad(str, 5)).isEqualTo("FooBar");
    }

    @Test
    public void it_right_pads_a_given_string_with_given_pad_string() {
        String str = "FooBar";

        assertThat(StringUtils.rightPad(str, 10, "X")).isEqualTo("FooBarXXXX");
        assertThat(StringUtils.rightPad(str, 6, "X")).isEqualTo("FooBar");
        assertThat(StringUtils.rightPad(str, 5, "X")).isEqualTo("FooBar");
    }

    @Test
    public void it_right_pads_a_given_string_with_spaces() {
        String str = "FooBar";

        assertThat(StringUtils.rightPad(str, 10)).isEqualTo("FooBar    ");
        assertThat(StringUtils.rightPad(str, 6)).isEqualTo("FooBar");
        assertThat(StringUtils.rightPad(str, 5)).isEqualTo("FooBar");
    }

    @Test
    public void it_checks_if_a_string_contains_only_numeric_values() {
        assertThat(StringUtils.isNumeric("123")).isTrue();
        assertThat(StringUtils.isNumeric("0")).isTrue();
        assertThat(StringUtils.isNumeric("012")).isTrue();
        assertThat(StringUtils.isNumeric("A")).isFalse();
        assertThat(StringUtils.isNumeric("A1")).isFalse();
        assertThat(StringUtils.isNumeric("1A")).isFalse();
        assertThat(StringUtils.isNumeric("1A2")).isFalse();
        assertThat(StringUtils.isNumeric(" 12 ")).isFalse();
        assertThat(StringUtils.isNumeric("")).isFalse();
    }

    @Test
    public void it_checks_if_string_is_set_to_no_data() {
        assertThat(StringUtils.isNoData("foo")).isFalse();
        assertThat(StringUtils.isNoData(Keys.NO_DATA)).isTrue();
    }
}