package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.imports.dampsoft.convert.HkpIndexConvert;
import de.dentareport.imports.dampsoft.convert.InfoConvert;
import de.dentareport.imports.dampsoft.convert.SerialConvert;
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
public class PatinfoDbfTest {

    @Mocked
    private HkpplanDbf mockHkpplanDbf;
    private PatinfoDbf patinfoDbf;

    @BeforeEach
    public void setUp() {
        patinfoDbf = new PatinfoDbf(mockHkpplanDbf);
    }

    @Test
    public void it_converts_row_of_data(@Mocked HkpIndexConvert mockHkpIndexConvert,
                                        @Mocked DateConvert mockDateConvert,
                                        @Mocked InfoConvert mockInfoConvert,
                                        @Mocked SerialConvert mockSerialConvert) {
        new Expectations() {{
            DateConvert.convert("somedate");
            result = "some-date";
            SerialConvert.convert("someserial");
            result = "sequence";
            InfoConvert.convertToBillingCode("info_string", "some_type");
            result = "some_billingcode";
            InfoConvert.convertToQuantity("info_string", "some_type");
            result = "some_quantity";
            InfoConvert.convertToSurfaces("info_string", "some_type");
            result = "some_surfaces";
            InfoConvert.convertToTooth("info_string", "some_type");
            result = "some_tooth";
            HkpIndexConvert.convert("hkpnr");
            result = "some_hkp_index";
            mockHkpplanDbf.insurance("some_hkp_index");
            result = "some_insurance";
        }};

        DbfRow dbfRow = testRow(" 234 ");
        List<DbRow> converted = patinfoDbf.convert(dbfRow);

        assertThat(converted.size()).isEqualTo(1);
        assertThat(converted.get(0).value("deleted")).isEqualTo("0");
        assertThat(converted.get(0).value("patient_index")).isEqualTo("234");
        assertThat(converted.get(0).value("date")).isEqualTo("some-date");
        assertThat(converted.get(0).value("sequence")).isEqualTo("sequence");
        assertThat(converted.get(0).value("type")).isEqualTo("some_type");
        assertThat(converted.get(0).value("billingcode")).isEqualTo("some_billingcode");
        assertThat(converted.get(0).value("quantity")).isEqualTo("some_quantity");
        assertThat(converted.get(0).value("surfaces")).isEqualTo("some_surfaces");
        assertThat(converted.get(0).value("tooth")).isEqualTo("some_tooth");
        assertThat(converted.get(0).value("toothrange")).isEqualTo("");
        assertThat(converted.get(0).value("hkp_index")).isEqualTo("some_hkp_index");
        assertThat(converted.get(0).value("insurance")).isEqualTo("some_insurance");
        assertThat(converted.get(0).value("handler")).isEqualTo("behand");
        assertThat(converted.get(0).value("comment")).isEqualTo(" kommentar ");
        assertThat(converted.get(0).value("source")).isEqualTo("patinfo");
    }

    @Test
    public void it_converts_deleted_row_of_data() {
        DbfRow dbfRow = testRow("");
        dbfRow.markDeleted();

        List<DbRow> converted = patinfoDbf.convert(dbfRow);

        assertThat(converted.get(0).value("deleted")).isEqualTo("1");
    }

    @Test
    public void it_validates_row_of_data() {
        DbRow row1 = new DbRow();
        row1.addCell(new DbCell("patient_index", "17"));
        row1.addCell(new DbCell("date", "2010-12-22"));
        DbRow row2 = new DbRow();
        row2.addCell(new DbCell("patient_index", "23"));
        row2.addCell(new DbCell("date", "not-a-date"));
        DbRow row3 = new DbRow();
        row3.addCell(new DbCell("patient_index", ""));
        row3.addCell(new DbCell("date", "2011-04-23"));
        DbRow row4 = new DbRow();
        row4.addCell(new DbCell("patient_index", ""));
        row4.addCell(new DbCell("date", "234234"));

        assertThat(patinfoDbf.isValidRow(row1)).isTrue();
        assertThat(patinfoDbf.isValidRow(row2)).isFalse();
        assertThat(patinfoDbf.isValidRow(row3)).isFalse();
        assertThat(patinfoDbf.isValidRow(row4)).isFalse();
    }

    @Test
    public void it_assigns_valid_hkp_treatments_to_static_variable(
            @Mocked HkpIndexConvert mockHkpIndexConvert,
            @Mocked DateConvert mockDateConvert,
            @Mocked InfoConvert mockInfoConvert) {

        new Expectations() {{
            DateConvert.convert("somedate");
            result = "2000-10-01";
            InfoConvert.convertToBillingCode("info_string", "some_type");
            result = "some_billingcode";
            InfoConvert.convertToTooth("info_string", "some_type");
            result = "some_tooth";
            HkpIndexConvert.convert("hkpnr");
            result = "some_hkp_index";
        }};

        patinfoDbf.convert(testRow("123"));

        assertThat(PatinfoDbf.hkpTreatments("123").size()).isEqualTo(1);
        assertThat(PatinfoDbf.hkpTreatments("123").get(0).billingcode()).isEqualTo("some_billingcode");
        assertThat(PatinfoDbf.hkpTreatments("123").get(0).tooth()).isEqualTo("some_tooth");
        assertThat(PatinfoDbf.hkpTreatments("123").get(0).hkpIndex()).isEqualTo("some_hkp_index");
    }

    @Test
    public void it_checks_if_hkp_treatment_exists(@Mocked HkpIndexConvert mockHkpIndexConvert,
                                                  @Mocked DateConvert mockDateConvert,
                                                  @Mocked InfoConvert mockInfoConvert) {
        new Expectations() {{
            DateConvert.convert("somedate");
            result = "2000-10-01";
            InfoConvert.convertToBillingCode("info_string", "some_type");
            result = "some_billingcode";
            InfoConvert.convertToTooth("info_string", "some_type");
            result = "some_tooth";
            HkpIndexConvert.convert("hkpnr");
            result = "some_hkp_index";
        }};

        DbRow row = new DbRow();
        row.addCell(new DbCell("patient_index", "8855"));
        row.addCell(new DbCell("billingcode", "some_billingcode"));
        row.addCell(new DbCell("tooth", "some_tooth"));
        row.addCell(new DbCell("hkp_index", "some_hkp_index"));

        assertThat(patinfoDbf.hkpTreatmentExists(row)).isFalse();

        patinfoDbf.convert(testRow("8855"));

        assertThat(patinfoDbf.hkpTreatmentExists(row)).isTrue();
    }

    @Test
    public void it_assigns_valid_hkp_treatments_only_once_to_static_variable(
            @Mocked HkpIndexConvert mockHkpIndexConvert,
            @Mocked DateConvert mockDateConvert) {

        new Expectations() {{
            DateConvert.convert("somedate");
            result = "2000-10-01";
        }};

        patinfoDbf.convert(testRow("7878"));
        patinfoDbf.convert(testRow("7878"));

        assertThat(PatinfoDbf.hkpTreatments("7878").size()).isEqualTo(1);
    }

    @Test
    public void it_does_not_assign_invalid_hkp_treatments_to_static_variable(
            @Mocked DateConvert mockDateConvert) {

        new Expectations() {{
            DateConvert.convert("somedate");
            result = "some_invalid_date";
        }};

        patinfoDbf.convert(testRow("345"));

        assertThat(PatinfoDbf.hkpTreatments("345").size()).isEqualTo(0);
    }

    @Test
    public void it_does_not_assign_valid_non_hkp_treatments_to_static_variable(
            @Mocked HkpIndexConvert mockHkpIndexConvert,
            @Mocked DateConvert mockDateConvert) {

        new Expectations() {{
            DateConvert.convert("somedate");
            result = "2000-10-01";
            HkpIndexConvert.convert("hkpnr");
            result = "";
        }};

        patinfoDbf.convert(testRow("456"));

        assertThat(PatinfoDbf.hkpTreatments("456").size()).isEqualTo(0);
    }

    private DbfRow testRow(String patnr) {
        DbfRow dbfRow = new DbfRow();
        dbfRow.addCell(new DbfCell("PATNR", patnr));
        dbfRow.addCell(new DbfCell("DATUM", "somedate"));
        dbfRow.addCell(new DbfCell("SERIELL", "someserial"));
        dbfRow.addCell(new DbfCell("ITYPE", " some_type "));
        dbfRow.addCell(new DbfCell("INFO", "info_string"));
        dbfRow.addCell(new DbfCell("HKPNR", "hkpnr"));
        dbfRow.addCell(new DbfCell("BEHAND", " behand "));
        dbfRow.addCell(new DbfCell("KOMMENTAR", " kommentar "));
        return dbfRow;
    }
}