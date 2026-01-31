package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfCell;
import de.dentareport.utils.dbf.DbfRow;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ZnotizDbfTest {

    private ZnotizDbf znotizDbf;

    @BeforeEach
    public void setUp() {
        znotizDbf = new ZnotizDbf();
    }

    @Test
    public void it_converts_row_of_data(@Mocked DateConvert mockDateConvert) {
        new Expectations() {{
            DateConvert.convert("somedate");
            result = "some-date";
        }};

        DbfRow dbfRow = testRow();

        List<DbRow> converted = znotizDbf.convert(dbfRow);

        assertThat(converted.size()).isEqualTo(1);
        assertThat(converted.get(0).value("deleted")).isEqualTo("0");
        assertThat(converted.get(0).value("patient_index")).isEqualTo("12345");
        assertThat(converted.get(0).value("tooth")).isEqualTo("67");
        assertThat(converted.get(0).value("date")).isEqualTo("some-date");
        assertThat(converted.get(0).value("note")).isEqualTo("FooBar");
    }

    @Test
    public void it_converts_deleted_row_of_data() {
        DbfRow dbfRow = testRow();
        dbfRow.markDeleted();

        List<DbRow> converted = znotizDbf.convert(dbfRow);

        assertThat(converted.get(0).value("deleted")).isEqualTo("1");
    }

    @Test
    public void it_trims_converted_patient_index() {
        DbfRow dbfRow = testRow();
        dbfRow.setValue("PATNR", "  3  12");

        List<DbRow> converted = znotizDbf.convert(dbfRow);

        assertThat(converted.get(0).value("patient_index")).isEqualTo("3");
    }

    @Test
    public void it_trims_converted_tooth() {
        DbfRow dbfRow = testRow();
        dbfRow.setValue("PATNR", "12345  ");

        List<DbRow> converted = znotizDbf.convert(dbfRow);

        assertThat(converted.get(0).value("tooth")).isEqualTo("");
    }

    @Test
    public void it_validates_row_of_data() {
        DbRow row1 = new DbRow();
        row1.addCell(new DbCell("patient_index", "123"));
        DbRow row2 = new DbRow();
        row2.addCell(new DbCell("patient_index", "0"));
        DbRow row3 = new DbRow();
        row3.addCell(new DbCell("patient_index", ""));

        assertThat(znotizDbf.isValidRow(row1)).isTrue();
        assertThat(znotizDbf.isValidRow(row2)).isTrue();
        assertThat(znotizDbf.isValidRow(row3)).isFalse();
    }

    private DbfRow testRow() {
        DbfRow dbfRow = new DbfRow();
        dbfRow.addCell(new DbfCell("PATNR", "1234567"));
        dbfRow.addCell(new DbfCell("DATUM", "somedate"));
        dbfRow.addCell(new DbfCell("NOTIZ", "  FooBar  "));
        return dbfRow;
    }
}