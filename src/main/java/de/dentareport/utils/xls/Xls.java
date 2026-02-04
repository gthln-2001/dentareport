package de.dentareport.utils.xls;


import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

// TODO: TEST?
public class Xls {

    public static final Pattern INVALID_XML = Pattern.compile("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]");
    private final SXSSFWorkbook workbook;
    private final XlsColors xlsColors;
    private final XlsParse xlsParse;
    private SXSSFSheet worksheet;
    private SXSSFRow row;
    private int rowPointer;
    private int columnPointer;


    public Xls() {
        workbook = new SXSSFWorkbook(100); // keep 100 rows in memory
        workbook.setCompressTempFiles(true);

        this.xlsColors = new XlsColors(this.workbook);
        this.xlsParse = new XlsParse(this.workbook);
    }

    public void addSheet(String name) {
        worksheet = workbook.createSheet(name);
        rowPointer = -1;
        addRow();
    }

    public void addRow() {
        rowPointer++;
        columnPointer = 0;
        row = worksheet.createRow(rowPointer);
    }

    public void addCell(String value) {
        addCell(value, "none");
    }

    public void addCell(String value, String background) {
        addXlsCell(value, background);
    }

    public void freezeRow() {
        worksheet.createFreezePane(0, rowPointer() + 1);
    }

    public int rowPointer() {
        return rowPointer;
    }

    // TODO: Fix?
    public void autosizeHeaderColumns() {
//        SXSSFRow header = worksheet.getRow(0);
//        if (header == null) {
//            return;
//        }
//
//        short lastCell = header.getLastCellNum();
//        IntStream.range(0, lastCell).forEach(i -> worksheet.trackColumnForAutoSizing(i));
//        IntStream.range(0, lastCell).forEach(i -> {
//            worksheet.autoSizeColumn(i);
//            worksheet.untrackColumnForAutoSizing(i);
//        });
    }

    public void write(String filename) {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            workbook.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
            } catch (IOException ignored) {}
        }
    }



    private void addXlsCell(String value, String background) {
        SXSSFCell cell = row.createCell(columnPointer);
        value = INVALID_XML.matcher(value).replaceAll("");
        RichTextString richTextString = sanitizeRichText(xlsParse.parse(value));
        cell.setCellValue(richTextString);
        cell.setCellStyle(xlsColors.background(background));
        columnPointer++;
    }

    /**
     * Removes control characters except CR, LF, and TAB
     */
    private RichTextString sanitizeRichText(RichTextString richTextString) {
        String ret;
        if (richTextString == null) {
            ret = "";
        } else {
            ret = richTextString.getString().replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
        }
        return workbook.getCreationHelper().createRichTextString(ret);
    }


}
