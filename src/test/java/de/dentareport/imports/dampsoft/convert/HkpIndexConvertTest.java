package de.dentareport.imports.dampsoft.convert;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class HkpIndexConvertTest {

    @Test
    public void it_converts_hkp_index() {
        assertThat(HkpIndexConvert.convert("123")).isEqualTo("123");
        assertThat(HkpIndexConvert.convert(" 345 ")).isEqualTo("345");
        assertThat(HkpIndexConvert.convert("567a")).isEqualTo("");
        assertThat(HkpIndexConvert.convert("567-")).isEqualTo("");
        assertThat(HkpIndexConvert.convert("567.")).isEqualTo("");
    }
}