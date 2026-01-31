package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.utils.dbf.Dbf;
import de.dentareport.utils.dbf.DbfCell;
import de.dentareport.utils.dbf.DbfRow;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class HkpplanDbfTest {

    @Mocked
    private Dbf mockDbf;

    @Test
    public void it_imports_data(@Mocked DateConvert mockDateConvert) {
        new Expectations() {{
            mockDbf.chunkOfRows();
            result = mockDbfRows();
            DateConvert.convert("somedate1");
            result = "some-date-1";
            DateConvert.convert("somedate2");
            result = "some-date-2";
        }};

        HkpplanDbf hkpplanDbf = new HkpplanDbf();
        hkpplanDbf.importFile();

        assertThat(hkpplanDbf.date("42")).isEqualTo("some-date-2");
        assertThat(hkpplanDbf.insurance("42")).isEqualTo("M");
        assertThat(HkpplanDbf.hkps().size()).isEqualTo(1);

        new Verifications() {{
            mockDbf.close();
            times = 1;
        }};
    }

    @Test
    public void it_imports_data_only_once_even_if_called_multiple_times(
            @Mocked DateConvert mockDateConvert) {

        new Expectations() {{
            mockDbf.chunkOfRows();
            result = mockDbfRows();
            DateConvert.convert("somedate1");
            result = "some-date-1";
            DateConvert.convert("somedate2");
            result = "some-date-2";
        }};
        HkpplanDbf hkpplanDbf1 = new HkpplanDbf();
        HkpplanDbf hkpplanDbf2 = new HkpplanDbf();
        HkpplanDbf hkpplanDbf3 = new HkpplanDbf();

        hkpplanDbf1.importFile();
        hkpplanDbf1.importFile();
        hkpplanDbf2.importFile();

        assertThat(hkpplanDbf3.date("42")).isEqualTo("some-date-2");
        assertThat(hkpplanDbf3.insurance("42")).isEqualTo("M");
        assertThat(HkpplanDbf.hkps().size()).isEqualTo(1);
    }

    @Test
    public void it_returns_empty_value_when_trying_to_access_hkp_that_does_not_exist() {
        HkpplanDbf hkpplanDbf = new HkpplanDbf();

        assertThat(hkpplanDbf.insurance("does_not_exist")).isEqualTo("");
        assertThat(hkpplanDbf.date("does_not_exist")).isEqualTo("");
    }

    private List<DbfRow> mockDbfRows() {
        List<DbfRow> dbfRows = new ArrayList<>();

        DbfRow row1 = new DbfRow();
        row1.addCell(new DbfCell("HKPNR", "42"));
        row1.addCell(new DbfCell("EINGLIED", "somedate1"));
        row1.addCell(new DbfCell("MFRP", "M"));
        dbfRows.add(row1);

        DbfRow row2 = new DbfRow();
        row2.addCell(new DbfCell("HKPNR", "42"));
        row2.addCell(new DbfCell("EINGLIED", "somedate2"));
        row2.addCell(new DbfCell("MFRP", "M"));
        dbfRows.add(row2);

        return dbfRows;
    }
}