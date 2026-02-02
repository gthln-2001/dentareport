package de.dentareport.utils.xls;

import mockit.Mocked;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class XlsFontsTest {

    @Mocked
    SXSSFWorkbook mockWorkbook;
    private XlsFonts xlsFonts;

    @BeforeEach
    public void setUp() {
        this.xlsFonts = new XlsFonts(mockWorkbook);
    }

    @Test
    public void it_creates_fonts() {
        assertThat(xlsFonts.font("bold_italic")).isInstanceOf(Font.class);
    }

    @Test
    public void it_throws_exception_when_trying_to_access_font_that_does_not_exist() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> xlsFonts.font("some_invalid_font_name")
        );
    }


}