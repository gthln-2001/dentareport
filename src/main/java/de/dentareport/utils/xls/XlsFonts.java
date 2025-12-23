package de.dentareport.utils.xls;

import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

public class XlsFonts {
    private XSSFWorkbook workbook;
    private Map<String, XSSFFont> fonts;

    public XlsFonts(XSSFWorkbook workbook) {
        this.workbook = workbook;
        createFonts();
    }

    public XSSFFont font(String key) {
        if (!fonts.containsKey(key)) {
            throw new IllegalArgumentException(key);
        }
        return fonts.get(key);
    }

    private void createFonts() {
        fonts = new HashMap<>();

        XSSFFont boldItalic = workbook.createFont();
        boldItalic.setItalic(true);
        boldItalic.setBold(true);
        boldItalic.setFontHeight(10);
        fonts.put("bold_italic", boldItalic);

    }
}
