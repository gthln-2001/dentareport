package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.imports.dampsoft.convert.Evidence01Convert;
import de.dentareport.utils.Dmft;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfCell;
import de.dentareport.utils.dbf.DbfRow;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class BefundDbf01Test {

    private BefundDbf01 befundDbf01;
    @Mocked
    private Dmft mockDmft;

    @BeforeEach
    public void setUp() {
        befundDbf01 = new BefundDbf01();
    }

    @Test
    public void it_converts_row_of_data(@Mocked DateConvert mockDateConvert,
                                        @Mocked Evidence01Convert mockEvidence01Convert) {
        new Expectations() {{
            DateConvert.convert("somedate");
            result = "some-date";
            Evidence01Convert.convert((DbfRow) any);
            result = mockEvidence();
            Dmft.calculate((DbRow) any);
            result = mockDmft();
        }};

        DbfRow dbfRow = testRow();
        List<DbRow> converted = befundDbf01.convert(dbfRow);

        assertThat(converted.size()).isEqualTo(1);
        assertThat(converted.get(0).value("deleted")).isEqualTo("0");
        assertThat(converted.get(0).value("patient_index")).isEqualTo("234");
        assertThat(converted.get(0).value("date")).isEqualTo("some-date");
        assertThat(converted.get(0).value("billingcode")).isEqualTo("foobar");
        assertThat(converted.get(0).value("milktooth_18")).isEqualTo("mock_milktooth_18");
        assertThat(converted.get(0).value("milktooth_21")).isEqualTo("mock_milktooth_21");
        assertThat(converted.get(0).value("status_34")).isEqualTo("mock_status_34");
        assertThat(converted.get(0).value("status_22")).isEqualTo("mock_status_22");
        assertThat(converted.get(0).value("sealing_41")).isEqualTo("mock_sealing_41");
        assertThat(converted.get(0).value("sealing_18")).isEqualTo("mock_sealing_18");
        assertThat(converted.get(0).value("filling_18_d")).isEqualTo("mock_filling_18_d");
        assertThat(converted.get(0).value("filling_18_m")).isEqualTo("mock_filling_18_m");
        assertThat(converted.get(0).value("filling_28_m")).isEqualTo("mock_filling_28_m");
        assertThat(converted.get(0).value("caries_28_v")).isEqualTo("mock_caries_28_v");
        assertThat(converted.get(0).value("tooth_count_q1")).isEqualTo("mock_tooth_count_q1");
        assertThat(converted.get(0).value("tooth_count_q2")).isEqualTo("mock_tooth_count_q2");
        assertThat(converted.get(0).value("tooth_count_q3")).isEqualTo("mock_tooth_count_q3");
        assertThat(converted.get(0).value("tooth_count_q4")).isEqualTo("mock_tooth_count_q4");
        assertThat(converted.get(0).value("dt")).isEqualTo("mock_dt");
        assertThat(converted.get(0).value("dt_milkteeth")).isEqualTo("mock_dt_milkteeth");
        assertThat(converted.get(0).value("ft")).isEqualTo("mock_ft");
        assertThat(converted.get(0).value("ft_milkteeth")).isEqualTo("mock_ft_milkteeth");
        assertThat(converted.get(0).value("mt")).isEqualTo("mock_mt");
        assertThat(converted.get(0).value("mt_milkteeth")).isEqualTo("mock_mt_milkteeth");
        assertThat(converted.get(0).value("dft")).isEqualTo("mock_dft");
        assertThat(converted.get(0).value("dmft")).isEqualTo("mock_dmft");
        assertThat(converted.get(0).value("dmft_milkteeth")).isEqualTo("mock_dmft_milkteeth");
    }

    @Test
    public void it_converts_deleted_row_of_data(@Mocked DateConvert mockDateConvert,
                                                @Mocked Evidence01Convert mockEvidence01Convert) {
        DbfRow dbfRow = testRow();
        dbfRow.markDeleted();

        List<DbRow> converted = befundDbf01.convert(dbfRow);

        assertThat(converted.get(0).value("deleted")).isEqualTo("1");
    }

    @Test
    public void it_returns_empty_list_if_evidence_is_not_of_type_01() {
        DbfRow dbfRow = new DbfRow();
        dbfRow.addCell(new DbfCell("BEFTYP", "something-else"));
        List<DbRow> converted = befundDbf01.convert(dbfRow);

        assertThat(converted.size()).isEqualTo(0);
    }

    @Test
    public void it_validates_row_of_data() {
        DbRow dbRow1 = new DbRow();
        assertThat(befundDbf01.isValidRow(dbRow1)).isFalse();

        DbRow dbRow2 = new DbRow();
        dbRow2.addCell(new DbCell("date", "invalid"));
        assertThat(befundDbf01.isValidRow(dbRow2)).isFalse();

        DbRow dbRow3 = new DbRow();
        dbRow3.addCell(new DbCell("date", "2014-04-28"));
        assertThat(befundDbf01.isValidRow(dbRow3)).isTrue();
    }

    private DbfRow testRow() {
        DbfRow dbfRow = new DbfRow();
        dbfRow.addCell(new DbfCell("BEFTYP", "0"));
        dbfRow.addCell(new DbfCell("PATNR", " 234 "));
        dbfRow.addCell(new DbfCell("GEBNR", " foobar "));
        dbfRow.addCell(new DbfCell("DATUM", "somedate"));
        for (String toothnumber : Toothnumbers.ALL) {
            dbfRow.addCell(new DbfCell("ZA" + toothnumber, "EVIDENCE_" + toothnumber));
        }

        return dbfRow;
    }

    private List<DbCell> mockEvidence() {
        List<DbCell> ret = new ArrayList<>();

        for (String toothnumber : Toothnumbers.ALL) {
            ret.add(new DbCell("milktooth_" + toothnumber, "mock_milktooth_" + toothnumber));
            ret.add(new DbCell("status_" + toothnumber, "mock_status_" + toothnumber));
            ret.add(new DbCell("sealing_" + toothnumber, "mock_sealing_" + toothnumber));
            for (String surface : Keys.SURFACES) {
                ret.add(new DbCell(String.format("filling_%s_%s", toothnumber, surface), String.format("mock_filling_%s_%s", toothnumber, surface)));
                ret.add(new DbCell(String.format("caries_%s_%s", toothnumber, surface), String.format("mock_caries_%s_%s", toothnumber, surface)));
            }
        }
        return ret;
    }

    private List<DbCell> mockDmft() {
        List<DbCell> dmft = new ArrayList<>();
        dmft.add(new DbCell("tooth_count_q1", "mock_tooth_count_q1"));
        dmft.add(new DbCell("tooth_count_q2", "mock_tooth_count_q2"));
        dmft.add(new DbCell("tooth_count_q3", "mock_tooth_count_q3"));
        dmft.add(new DbCell("tooth_count_q4", "mock_tooth_count_q4"));
        dmft.add(new DbCell("dt", "mock_dt"));
        dmft.add(new DbCell("dt_milkteeth", "mock_dt_milkteeth"));
        dmft.add(new DbCell("ft", "mock_ft"));
        dmft.add(new DbCell("ft_milkteeth", "mock_ft_milkteeth"));
        dmft.add(new DbCell("mt", "mock_mt"));
        dmft.add(new DbCell("mt_milkteeth", "mock_mt_milkteeth"));
        dmft.add(new DbCell("dft", "mock_dft"));
        dmft.add(new DbCell("dmft", "mock_dmft"));
        dmft.add(new DbCell("dmft_milkteeth", "mock_dmft_milkteeth"));
        return dmft;
    }
}