package de.dentareport.utils.xls;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

// TODO: TEST?
public class XlsFonts {
    private final SXSSFWorkbook workbook;
    private Map<String, Font> fonts;

    public XlsFonts(SXSSFWorkbook workbook) {
        this.workbook = workbook;
        createFonts();
    }

    public Font font(String key) {
        if (!fonts.containsKey(key)) {
            throw new IllegalArgumentException(key);
        }
        return fonts.get(key);
    }

    private void createFonts() {
        fonts = new HashMap<>();

        Font boldItalic = workbook.createFont();
        boldItalic.setItalic(true);
        boldItalic.setBold(true);
        boldItalic.setFontHeight((short) 10);
        fonts.put("bold_italic", boldItalic);
    }
}
