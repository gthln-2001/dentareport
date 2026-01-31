package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.Keys;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class GenderConvertTest {

    @Test
    public void it_converts_salutation_and_gender_to_gender() {
        assertThat(GenderConvert.convert("", "")).isEqualTo("");
        assertThat(GenderConvert.convert("w", "")).isEqualTo(Keys.FEMALE);
        assertThat(GenderConvert.convert("m", "")).isEqualTo(Keys.MALE);
        assertThat(GenderConvert.convert("X", "")).isEqualTo("x");
        assertThat(GenderConvert.convert(" X ", "")).isEqualTo("x");
        assertThat(GenderConvert.convert("", "F")).isEqualTo(Keys.FEMALE);
        assertThat(GenderConvert.convert("", "H")).isEqualTo(Keys.MALE);
        assertThat(GenderConvert.convert("", "f")).isEqualTo("");
        assertThat(GenderConvert.convert("", "X")).isEqualTo("");
        assertThat(GenderConvert.convert("X", "F")).isEqualTo("x");
    }
}