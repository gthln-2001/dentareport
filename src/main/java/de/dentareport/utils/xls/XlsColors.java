package de.dentareport.utils.xls;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

// TODO: TEST?
public class XlsColors {

    private final XSSFWorkbook workbook;
    private Map<String, CellStyle> backgrounds;

    public XlsColors(XSSFWorkbook workbook) {
        this.workbook = workbook;
        createBackgroundStyles();
    }

    public CellStyle background(String index) {
        return backgrounds.getOrDefault(index, backgrounds.get("none"));
    }

    private void createBackgroundStyles() {
        backgrounds = new HashMap<>();

        CellStyle none = workbook.createCellStyle();
        backgrounds.put("none", none);

        CellStyle bg1 = workbook.createCellStyle();
        bg1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        bg1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        backgrounds.put("1", bg1);

        CellStyle bg2 = workbook.createCellStyle();
        bg2.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        bg2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        backgrounds.put("2", bg2);

        CellStyle bg3 = workbook.createCellStyle();
        bg3.setFillForegroundColor(IndexedColors.PINK.getIndex());
        bg3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        backgrounds.put("3", bg3);
    }
}
