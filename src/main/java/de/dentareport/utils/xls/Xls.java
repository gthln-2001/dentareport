package de.dentareport.utils.xls;


import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

// TODO: TEST?
public class Xls {

    private final XSSFWorkbook workbook;
    private final XlsColors xlsColors;
    private final XlsParse xlsParse;
    private XSSFSheet worksheet;
    private XSSFRow row;
    private int rowPointer;
    private int columnPointer;


    public Xls() {
        this.workbook = new XSSFWorkbook();
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
            } catch (IOException ignored) {
            }
        }
    }


    private void addXlsCell(String value, String background) {
        XSSFCell cell = row.createCell(columnPointer);
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
