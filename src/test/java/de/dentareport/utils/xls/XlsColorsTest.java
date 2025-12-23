package de.dentareport.utils.xls;

import mockit.Mocked;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class XlsColorsTest {

    private XlsColors xlsColors;
    @Mocked
    XSSFWorkbook mockWorkbook;

    @BeforeEach
    public void setUp() {
        this.xlsColors = new XlsColors(mockWorkbook);
    }

    @Test
    public void it_creates_background_styles() {
        assertThat(xlsColors.background("1")).isInstanceOf(CellStyle.class);
        assertThat(xlsColors.background("2")).isInstanceOf(CellStyle.class);
        assertThat(xlsColors.background("3")).isInstanceOf(CellStyle.class);
    }

    @Test
    public void it_gets_background_if_index_is_not_defined() {
        assertThat(xlsColors.background("some_index_we_never_use")).isInstanceOf(CellStyle.class);
    }
}