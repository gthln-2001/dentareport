package de.dentareport.imports.dampsoft.convert;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuantityConvertTest {

    @Test
    public void it_converts_quantity() {
        assertThat(QuantityConvert.convert("")).isEqualTo("");
        assertThat(QuantityConvert.convert("5")).isEqualTo("5");
        assertThat(QuantityConvert.convert("15.5")).isEqualTo("15.5");
        assertThat(QuantityConvert.convert("25.0")).isEqualTo("25");
        assertThat(QuantityConvert.convert("25.0x")).isEqualTo("25.0x");
    }
}