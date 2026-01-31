package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.imports.dampsoft.convert.QuantityConvert;
import de.dentareport.imports.dampsoft.convert.SurfacesConvert;
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
public class HkplstDbfTest {

    private HkplstDbf hkplstDbf;
    @Mocked
    private HkpplanDbf mockHkpplanDbf;
    @Mocked
    private PatinfoDbf mockPatinfoDbf;

    @BeforeEach
    public void setUp() {
        this.hkplstDbf = new HkplstDbf(mockHkpplanDbf, mockPatinfoDbf);
    }

    @Test
    public void it_converts_row_of_data(@Mocked QuantityConvert mockQuantityConvert,
                                        @Mocked SurfacesConvert mockSurfacesConvert) {
        new Expectations() {{
            QuantityConvert.convert("some_quantity");
            result = "quantity";
            SurfacesConvert.convert("some_surfaces");
            result = "surfaces";
            mockHkpplanDbf.date("42");
            result = "some-date";
            mockHkpplanDbf.insurance("42");
            result = "insurance";
        }};

        DbfRow dbfRow = testRowDbf();

        List<DbRow> converted = hkplstDbf.convert(dbfRow);

        assertThat(converted.size()).isEqualTo(2);

        assertThat(converted.get(0).value("tooth")).isEqualTo("12");
        assertThat(converted.get(1).value("tooth")).isEqualTo("23");

        assertThat(converted.get(0).value("deleted")).isEqualTo("0");
        assertThat(converted.get(1).value("deleted")).isEqualTo("0");
        assertThat(converted.get(0).value("sequence")).isEqualTo("hkp");
        assertThat(converted.get(1).value("sequence")).isEqualTo("hkp");
        assertThat(converted.get(0).value("type")).isEqualTo("");
        assertThat(converted.get(1).value("type")).isEqualTo("");
        assertThat(converted.get(0).value("comment")).isEqualTo("");
        assertThat(converted.get(1).value("comment")).isEqualTo("");
        assertThat(converted.get(0).value("source")).isEqualTo("hkp");
        assertThat(converted.get(1).value("source")).isEqualTo("hkp");
        assertThat(converted.get(0).value("patient_index")).isEqualTo("234");
        assertThat(converted.get(1).value("patient_index")).isEqualTo("234");
        assertThat(converted.get(0).value("handler")).isEqualTo("71");
        assertThat(converted.get(1).value("handler")).isEqualTo("71");
        assertThat(converted.get(0).value("billingcode")).isEqualTo("23A");
        assertThat(converted.get(1).value("billingcode")).isEqualTo("23A");
        assertThat(converted.get(0).value("toothrange")).isEqualTo("12, 23");
        assertThat(converted.get(1).value("toothrange")).isEqualTo("12, 23");
        assertThat(converted.get(0).value("hkp_index")).isEqualTo("42");
        assertThat(converted.get(1).value("hkp_index")).isEqualTo("42");
        assertThat(converted.get(0).value("insurance")).isEqualTo("insurance");
        assertThat(converted.get(1).value("insurance")).isEqualTo("insurance");
        assertThat(converted.get(0).value("quantity")).isEqualTo("quantity");
        assertThat(converted.get(1).value("quantity")).isEqualTo("quantity");
        assertThat(converted.get(0).value("surfaces")).isEqualTo("surfaces");
        assertThat(converted.get(1).value("surfaces")).isEqualTo("surfaces");
        assertThat(converted.get(0).value("date")).isEqualTo("some-date");
        assertThat(converted.get(1).value("date")).isEqualTo("some-date");
        assertThat(converted.get(0).value("sign")).isEqualTo("+");
        assertThat(converted.get(1).value("sign")).isEqualTo("+");
    }

    @Test
    public void it_converts_deleted_row_of_data() {
        DbfRow dbfRow = testRowDbf();
        dbfRow.markDeleted();

        List<DbRow> converted = hkplstDbf.convert(dbfRow);

        assertThat(converted.get(0).value("deleted")).isEqualTo("1");
    }

    @Test
    public void it_validates_row_of_data() {
        DbRow dbRow = testRowDb();
        assertThat(hkplstDbf.isValidRow(dbRow)).isTrue();
    }

    @Test
    public void it_marks_row_as_invalid_when_treatment_already_exists_in_patinfo() {
        DbRow dbRow = testRowDb();
        new Expectations() {{
            mockPatinfoDbf.hkpTreatmentExists(dbRow);
            result = true;
        }};

        assertThat(hkplstDbf.isValidRow(dbRow)).isFalse();
    }

    @Test
    public void it_marks_row_as_invalid_when_date_is_invalid() {
        DbRow dbRow = testRowDb();
        dbRow.setValue("date", "invalid-date");
        assertThat(hkplstDbf.isValidRow(dbRow)).isFalse();
    }

    @Test
    public void it_marks_row_as_invalid_when_value_of_sign_is_invalid() {
        DbRow dbRow = testRowDb();
        dbRow.setValue("sign", "something else than +");
        assertThat(hkplstDbf.isValidRow(dbRow)).isFalse();
    }

    @Test
    public void it_marks_row_as_invalid_when_quantity_is_invalid() {
        DbRow dbRow = testRowDb();

        dbRow.setValue("quantity", "");
        assertThat(hkplstDbf.isValidRow(dbRow)).isFalse();

        dbRow.setValue("quantity", "0.00");
        assertThat(hkplstDbf.isValidRow(dbRow)).isFalse();
    }

    @Test
    public void it_removes_temporary_columns() {
        DbRow testRow = testRowDb();

        DbRow dbRow = hkplstDbf.removeTemporaryColumns(testRow);

        assertThat(dbRow.hasCell("sign")).isFalse();
    }

    private DbRow testRowDb() {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("date", "2010-10-01"));
        dbRow.addCell(new DbCell("quantity", "5"));
        dbRow.addCell(new DbCell("sign", "+"));
        return dbRow;
    }

    private DbfRow testRowDbf() {
        DbfRow dbfRow = new DbfRow();
        dbfRow.addCell(new DbfCell("PATNR", " 234 "));
        dbfRow.addCell(new DbfCell("HKPNR", " 42 "));
        dbfRow.addCell(new DbfCell("GEBNR", " 23a "));
        dbfRow.addCell(new DbfCell("ANZAHL", "some_quantity"));
        dbfRow.addCell(new DbfCell("ZAHN", "12, 23"));
        dbfRow.addCell(new DbfCell("FLAECHE", "some_surfaces"));
        dbfRow.addCell(new DbfCell("BEHANDLER", " 71 "));
        dbfRow.addCell(new DbfCell("SIGN", " + "));
        return dbfRow;
    }
}