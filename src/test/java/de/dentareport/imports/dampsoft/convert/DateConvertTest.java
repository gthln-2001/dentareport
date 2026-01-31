package de.dentareport.imports.dampsoft.convert;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DateConvertTest {

    @Test
    public void it_converts_date_string() {
        assertThat(DateConvert.convert("20001013")).isEqualTo("2000-10-13");
    }
}