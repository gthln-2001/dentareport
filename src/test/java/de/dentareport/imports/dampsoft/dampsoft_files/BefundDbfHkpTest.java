package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.imports.dampsoft.convert.EvidenceHkpConvert;
import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfCell;
import de.dentareport.utils.dbf.DbfRow;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BefundDbfHkpTest {

    private BefundDbfHkp befundDbfHkp;

    @BeforeEach
    public void setUp() {
        befundDbfHkp = new BefundDbfHkp();
    }

    @Test
    public void it_converts_row_of_data(@Mocked DateConvert mockDateConvert,
                                        @Mocked EvidenceHkpConvert mockEvidenceHkpConvert) {
        new Expectations() {{
            DateConvert.convert("somedate");
            result = "some-date";
            EvidenceHkpConvert.convert((DbfRow) any);
            result = mockEvidence();
        }};

        DbfRow dbfRow = testRow();
        List<DbRow> converted = befundDbfHkp.convert(dbfRow);

        assertThat(converted.size()).isEqualTo(1);
        assertThat(converted.get(0).value("deleted")).isEqualTo("0");
        assertThat(converted.get(0).value("patient_index")).isEqualTo("234");
        assertThat(converted.get(0).value("date")).isEqualTo("some-date");
        assertThat(converted.get(0).value("billingcode")).isEqualTo("foobar");
        assertThat(converted.get(0).value("hkp_index")).isEqualTo("8844");
        assertThat(converted.get(0).value("status_14")).isEqualTo("mock_status_14");
        assertThat(converted.get(0).value("status_26")).isEqualTo("mock_status_26");
    }

    @Test
    public void it_converts_deleted_row_of_data() {
        DbfRow dbfRow = testRow();
        dbfRow.markDeleted();

        List<DbRow> converted = befundDbfHkp.convert(dbfRow);

        assertThat(converted.get(0).value("deleted")).isEqualTo("1");
    }

    @Test
    public void it_returns_empty_list_if_evidence_is_not_of_type_HKP() {
        DbfRow dbfRow = new DbfRow();
        dbfRow.addCell(new DbfCell("BEFTYP", "something-else"));
        List<DbRow> converted = befundDbfHkp.convert(dbfRow);

        assertThat(converted.size()).isEqualTo(0);
    }

    @Test
    public void it_validates_row_of_data() {
        DbRow dbRow1 = new DbRow();
        assertThat(befundDbfHkp.isValidRow(dbRow1)).isFalse();

        DbRow dbRow2 = new DbRow();
        dbRow2.addCell(new DbCell("date", "invalid"));
        assertThat(befundDbfHkp.isValidRow(dbRow2)).isFalse();

        DbRow dbRow3 = new DbRow();
        dbRow3.addCell(new DbCell("date", "2014-04-28"));
        assertThat(befundDbfHkp.isValidRow(dbRow3)).isTrue();
    }

    private DbfRow testRow() {
        DbfRow dbfRow = new DbfRow();
        dbfRow.addCell(new DbfCell("BEFTYP", "H"));
        dbfRow.addCell(new DbfCell("PATNR", " 234 "));
        dbfRow.addCell(new DbfCell("GEBNR", " foobar "));
        dbfRow.addCell(new DbfCell("HKPNR", " 8844 "));
        dbfRow.addCell(new DbfCell("DATUM", "somedate"));
        for (String toothnumber : Toothnumbers.ALL) {
            dbfRow.addCell(new DbfCell("ZA" + toothnumber, "EVIDENCE_" + toothnumber));
        }
        return dbfRow;
    }

    private List<DbCell> mockEvidence() {
        return Toothnumbers.ALL.stream()
                .map(toothnumber -> new DbCell(
                        "status_" + toothnumber,
                        "mock_status_" + toothnumber))
                .collect(Collectors.toList());
    }
}