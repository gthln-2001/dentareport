package de.dentareport.utils.xls;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.apache.poi.xssf.usermodel.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class XlsTest {

    // TODO: Fix tests
//
//    @Test
//    public void it_creates_new_workbook(@Mocked XSSFWorkbook mockWorkbook) {
//        new Xls();
//
//        new Verifications() {{
//            new XSSFWorkbook();
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_creates_new_worksheet_and_sets_row_and_column(@Mocked XSSFWorkbook mockWorkbook) {
//        Xls xls = new Xls();
//        xls.addSheet("bar");
//        xls.addRow();
//        xls.addSheet("biz");
//
//        new Verifications() {{
//            mockWorkbook.createSheet("bar");
//            times = 1;
//            mockWorkbook.createSheet("biz");
//            times = 1;
//        }};
//
//        assertThat(xls.columnPointer()).isEqualTo(0);
//        assertThat(xls.rowPointer()).isEqualTo(0);
//    }
//
//    @Test
//    public void it_freezes_row_in_worksheet(@Mocked XSSFWorkbook mockWorkbook,
//                                            @Mocked XSSFSheet mockWorksheet) {
//        Xls xls = new Xls();
//        xls.addSheet("bar");
//        xls.addRow();
//        xls.addRow();
//        xls.addRow();
//
//        xls.freezeRow();
//
//        new Verifications() {{
//            mockWorksheet.createFreezePane(0, 3);
//        }};
//
//        assertThat(xls.rowPointer()).isEqualTo(3);
//        assertThat(xls.columnPointer()).isEqualTo(0);
//    }
//
//    @Test
//    public void it_adds_cell_to_worksheet(@Mocked XSSFWorkbook mockWorkbook,
//                                          @Mocked XSSFSheet mockWorksheet,
//                                          @Mocked XSSFRow mockRow,
//                                          @Mocked XSSFCell mockCell,
//                                          @Mocked XSSFRichTextString mockRichTextString1,
//                                          @Mocked XSSFRichTextString mockRichTextString2) {
//        Xls xls = new Xls();
//
//        new Expectations() {{
//            mockWorksheet.createRow(0);
//
//            mockRow.createCell(0);
//            new XSSFRichTextString("val1");
//            result = mockRichTextString1;
//            mockCell.setCellValue(mockRichTextString1);
//
//            mockRow.createCell(1);
//            new XSSFRichTextString("val2");
//            result = mockRichTextString2;
//            mockCell.setCellValue(mockRichTextString2);
//        }};
//
//        xls.addSheet("bar");
//        xls.addCell("val1");
//        xls.addCell("val2");
//
//        assertThat(xls.rowPointer()).isEqualTo(0);
//        assertThat(xls.columnPointer()).isEqualTo(2);
//    }
//
//    @Test
//    public void it_moves_pointer_to_first_column_of_new_row(@Mocked XSSFWorkbook mockWorkbook,
//                                                            @Mocked XSSFSheet mockWorksheet) {
//        Xls xls = new Xls();
//        xls.addSheet("bar");
//        xls.addCell("val1");
//        xls.addCell("val2");
//        xls.addRow();
//
//        assertThat(xls.rowPointer()).isEqualTo(1);
//        assertThat(xls.columnPointer()).isEqualTo(0);
//    }
//
//    @Test
//    public void it_sets_all_columns_in_worksheet_to_optimal_width(@Mocked XSSFWorkbook mockWorkbook,
//                                                                  @Mocked XSSFSheet mockWorksheet,
//                                                                  @Mocked XSSFRow mockRow) {
//        Xls xls = new Xls();
//        xls.addSheet("bar");
//        xls.addCell("val1");
//        xls.addCell("val2");
//
//        new Expectations() {{
//            mockRow.getLastCellNum(); // Unfortunately it seems like we have to mock this. Otherwise getLastCellNum just returns 0.
//            result = 2;
//        }};
//
//        xls.autosizeAllColumns();
//
//        new Verifications() {{
//            mockWorksheet.autoSizeColumn(anyInt);
//            times = 2;
//
//            mockWorksheet.autoSizeColumn(0);
//            times = 1;
//            mockWorksheet.autoSizeColumn(1);
//            times = 1;
//        }};
//    }
}