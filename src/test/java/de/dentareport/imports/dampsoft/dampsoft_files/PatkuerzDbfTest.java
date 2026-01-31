package de.dentareport.imports.dampsoft.dampsoft_files;

//import de.dentareport.gui.ProgressUpdate;
import de.dentareport.utils.dbf.Dbf;
import de.dentareport.utils.dbf.DbfCell;
import de.dentareport.utils.dbf.DbfRow;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class PatkuerzDbfTest {

    @Mocked
    private Dbf mockDbf;

    @BeforeEach
    public void setUp() {
        new Expectations() {{
            mockDbf.chunkOfRows();
            result = mockDbfRows();
        }};
    }

//    @Test
//    public void it_imports_data(@Mocked ProgressUpdate mockProgressUpdate) {
//        PatkuerzDbf patkuerzDbf = new PatkuerzDbf();
//        patkuerzDbf.importFile();
//
//        assertThat(PatkuerzDbf.tokens().size()).isEqualTo(2);
//
//        assertThat(PatkuerzDbf.token(0)).isEqualTo("FOO");
//        assertThat(PatkuerzDbf.token(1)).isEqualTo("BAR");
//
//        new Verifications() {{
//            ProgressUpdate.tick();
//            times = 3;
//            mockDbf.close();
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_imports_data_only_once_even_if_called_multiple_times(
//            @Mocked ProgressUpdate mockProgressUpdate) {
//
//        PatkuerzDbf patkuerzDbf1 = new PatkuerzDbf();
//        PatkuerzDbf patkuerzDbf2 = new PatkuerzDbf();
//        PatkuerzDbf patkuerzDbf3 = new PatkuerzDbf();
//
//        patkuerzDbf1.importFile();
//        patkuerzDbf2.importFile();
//        patkuerzDbf3.importFile();
//
//        assertThat(PatkuerzDbf.tokens().size()).isEqualTo(2);
//
//        assertThat(PatkuerzDbf.token(0)).isEqualTo("FOO");
//        assertThat(PatkuerzDbf.token(1)).isEqualTo("BAR");
//    }

    private List<DbfRow> mockDbfRows() {
        List<DbfRow> dbfRows = new ArrayList<>();

        DbfRow row1 = new DbfRow();
        row1.addCell(new DbfCell("TYPE", "Foo"));
        dbfRows.add(row1);

        DbfRow row2 = new DbfRow();
        row2.addCell(new DbfCell("TYPE", "   "));
        dbfRows.add(row2);

        DbfRow row3 = new DbfRow();
        row3.addCell(new DbfCell("TYPE", " bar "));
        dbfRows.add(row3);

        return dbfRows;
    }
}