package de.dentareport.utils.xls;


import de.dentareport.exceptions.DentareportIOException;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

// TODO: TEST?
public class Xls {

    private final SXSSFWorkbook workbook;
    private final XlsColors xlsColors;
    private final XlsParse xlsParse;
    private SXSSFSheet worksheet;
    private SXSSFRow row;
    private int rowPointer;
    private int columnPointer;


    public Xls() {
        this.workbook = new SXSSFWorkbook(500);
        this.workbook.setCompressTempFiles(true);
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
        worksheet.createFreezePane(0, rowPointer());
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
        try (SXSSFWorkbook wb = this.workbook;
             OutputStream fileOut = new FileOutputStream(filename)) {
            wb.write(fileOut);
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    private void addXlsCell(String value, String background) {
        SXSSFCell cell = row.createCell(columnPointer);
        cell.setCellValue(xlsParse.parse(value));
        cell.setCellStyle(xlsColors.background(background));
        columnPointer++;
    }
}
