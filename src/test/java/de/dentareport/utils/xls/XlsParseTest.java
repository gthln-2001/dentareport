package de.dentareport.utils.xls;

import mockit.Mocked;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class XlsParseTest {

    private XlsParse xlsParse;
    @Mocked
    XSSFWorkbook mockWorkbook;

    @BeforeEach
    public void setUp() {
        this.xlsParse = new XlsParse(mockWorkbook);
    }

    @Test
    public void it_does_not_change_string_without_markup() {
        RichTextString result = xlsParse.parse("foobar");

        assertThat(result.getString()).isEqualTo("foobar");
        assertThat(result.numFormattingRuns()).isEqualTo(0);
    }

    @Test
    public void it_parses_string_to_font_bold_italic() {
        // There seems to be no easy way to check if the right format is actually applied
        // so we leave it at this for now...
        RichTextString result = xlsParse.parse("°°foobar°° biz °°baz°°");

        assertThat(result.getString()).isEqualTo("foobar biz baz");

        assertThat(result.numFormattingRuns()).isEqualTo(3);

        assertThat(result.getIndexOfFormattingRun(0)).isEqualTo(0);
        assertThat(result.getIndexOfFormattingRun(1)).isEqualTo(6);
        assertThat(result.getIndexOfFormattingRun(2)).isEqualTo(11);
    }
}