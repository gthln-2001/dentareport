package de.dentareport.imports.dampsoft.convert;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerialConvertTest {

    @Test
    public void it_converts_serial_value() {
        assertThat(SerialConvert.convert("123")).isEqualTo("123");
        assertThat(SerialConvert.convert(" 345 ")).isEqualTo("345");
        assertThat(SerialConvert.convert("567a")).isEqualTo("");
        assertThat(SerialConvert.convert("567-")).isEqualTo("");
        assertThat(SerialConvert.convert("567.")).isEqualTo("");
    }
}