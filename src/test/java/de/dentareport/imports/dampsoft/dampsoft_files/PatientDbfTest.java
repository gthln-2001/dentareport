package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.imports.dampsoft.convert.GenderConvert;
import de.dentareport.imports.dampsoft.convert.TokensConvert;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfCell;
import de.dentareport.utils.dbf.DbfRow;
import de.dentareport.utils.string.DateStringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientDbfTest {

    @Mocked
    private FirstAndLastVisit mockFirstAndLastVisit;
    private PatientDbf patientDbf;

    @BeforeEach
    public void setUp() {
        patientDbf = new PatientDbf();
    }

    @Test
    public void it_converts_row_of_data(@Mocked TokensConvert mockTokensConvert,
                                        @Mocked DateConvert mockDateConvert,
                                        @Mocked GenderConvert mockGenderConvert,
                                        @Mocked DateStringUtils mockDateStringUtils) {
        new Expectations() {{
            DateConvert.convert("some_date");
            result = "date-of-birth";
            GenderConvert.convert("Foo", "Bar");
            result = "foobar";
            TokensConvert.convert("some_token");
            result = "tokens";
            FirstAndLastVisit.firstVisit("234");
            result = "first-visit";
            FirstAndLastVisit.lastVisit("234");
            result = "last-visit";
            FirstAndLastVisit.last01("234");
            result = "last-01";
            DateStringUtils.age("date-of-birth", "last-01");
            result = "age-last-01";
            DateStringUtils.groupAgeByDecade("age-last-01");
            result = "group-age-last-01";
        }};
        DbfRow dbfRow = testRowDbf();

        List<DbRow> converted = patientDbf.convert(dbfRow);

        assertThat(converted.size()).isEqualTo(1);

        assertThat(converted.get(0).value("deleted")).isEqualTo("0");
        assertThat(converted.get(0).value("patient_index")).isEqualTo("234");
        assertThat(converted.get(0).value("date_of_birth")).isEqualTo("date-of-birth");
        assertThat(converted.get(0).value("gender")).isEqualTo("foobar");
        assertThat(converted.get(0).value("token")).isEqualTo("tokens");
        assertThat(converted.get(0).value("first_visit")).isEqualTo("first-visit");
        assertThat(converted.get(0).value("last_visit")).isEqualTo("last-visit");
        assertThat(converted.get(0).value("age_last_01")).isEqualTo("age-last-01");
        assertThat(converted.get(0).value("group_age_last_01")).isEqualTo("group-age-last-01");
    }

    @Test
    public void it_converts_deleted_row_of_data() {
        DbfRow dbfRow = testRowDbf();
        dbfRow.markDeleted();

        List<DbRow> converted = patientDbf.convert(dbfRow);

        assertThat(converted.get(0).value("deleted")).isEqualTo("1");
    }

    @Test
    public void it_validates_row_of_data() {
        DbRow row1 = new DbRow();
        row1.addCell(new DbCell("patient_index", "123"));
        DbRow row2 = new DbRow();
        row2.addCell(new DbCell("patient_index", "0"));
        DbRow row3 = new DbRow();
        row3.addCell(new DbCell("patient_index", ""));

        assertThat(patientDbf.isValidRow(row1)).isTrue();
        assertThat(patientDbf.isValidRow(row2)).isTrue();
        assertThat(patientDbf.isValidRow(row3)).isFalse();
    }

    private DbfRow testRowDbf() {
        DbfRow dbfRow = new DbfRow();
        dbfRow.addCell(new DbfCell("PATNR", " 234 "));
        dbfRow.addCell(new DbfCell("GEBDATP", "some_date"));
        dbfRow.addCell(new DbfCell("GESCHLECHT", "Foo"));
        dbfRow.addCell(new DbfCell("PAT_ANREDE", "Bar"));
        dbfRow.addCell(new DbfCell("KUERZEL", "some_token"));
        return dbfRow;
    }
}