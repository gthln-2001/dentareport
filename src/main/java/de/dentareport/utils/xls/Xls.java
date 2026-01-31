package de.dentareport.utils.xls;


import de.dentareport.exceptions.DentareportIOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

// TODO: TEST?
public class Xls {

    private final XSSFWorkbook workbook;
    private XSSFSheet worksheet;
    private XSSFRow row;
    private final XlsColors xlsColors;
    private final XlsParse xlsParse;
    private Map<String, Integer> pointer;

    public Xls() {
        this.workbook = new XSSFWorkbook();
        this.xlsColors = new XlsColors(this.workbook);
        this.xlsParse = new XlsParse(this.workbook);
    }

    public void addSheet(String name) {
        worksheet = workbook.createSheet(name);
        pointer = new HashMap<>();
        addRow();
    }

    public void addRow() {
        if (pointer.isEmpty()) {
            initializeRowPointer();
        } else {
            increaseRowPointer();
        }
        initializeColumnPointer();
        row = worksheet.createRow(pointer.get("row"));
    }

    public void addCell(String value) {
        addCell(value, "none");
    }

    public void addCell(String value,
                        String background) {
        addXlsCell(value, background);
    }

    public void freezeRow() {
        worksheet.createFreezePane(0, rowPointer());
    }

    public int rowPointer() {
        return pointer.get("row");
    }

    public int columnPointer() {
        return pointer.get("column");
    }

    public void autosizeAllColumns() {
        int columnCount = worksheet.getRow(0).getLastCellNum();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            worksheet.autoSizeColumn(columnIndex);
        }
    }

    public void write(String filename) {
        try {
            OutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    private void initializeColumnPointer() {
        pointer.put("column", 0);
    }

    private void initializeRowPointer() {
        pointer.put("row", 0);
    }

    private void increaseRowPointer() {
        pointer.put("row", pointer.get("row") + 1);
    }

    private void addXlsCell(String value,
                            String background) {
        XSSFCell cell = row.createCell(pointer.get("column"));
        cell.setCellValue(xlsParse.parse(value));
        cell.setCellStyle(xlsColors.background(background));
        increaseColumnPointer();
    }

    private void increaseColumnPointer() {
        pointer.put("column", pointer.get("column") + 1);
    }
}
