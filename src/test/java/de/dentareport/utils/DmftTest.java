package de.dentareport.utils;

import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class DmftTest {

    @Test
    public void it_throws_exception_if_no_valid_evidence_is_given() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                Dmft.calculate(new DbRow())
        );
    }

    @Test
    public void it_calculates_dmft_for_sound_evidence() {
        DbRow evidence = testEvidence();
        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("tooth_count_q1")).isEqualTo("8");
        assertThat(evidence.value("tooth_count_q2")).isEqualTo("8");
        assertThat(evidence.value("tooth_count_q3")).isEqualTo("8");
        assertThat(evidence.value("tooth_count_q4")).isEqualTo("8");
        assertThat(evidence.value("dt")).isEqualTo("0");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("ft")).isEqualTo("0");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("mt")).isEqualTo("0");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("dft")).isEqualTo("0");
        assertThat(evidence.value("dmft")).isEqualTo("0");
        assertThat(evidence.value("dmft_milkteeth")).isEqualTo("0");
    }

    @Test
    public void it_counts_teeth_in_quadrant() {
        DbRow evidence = testEvidence();
        evidence.setValue("status_11", Keys.EVIDENCE_STATUS__BRUECKENGLIED);
        evidence.setValue("status_12", Keys.EVIDENCE_STATUS__ERSETZT);
        evidence.setValue("status_13", Keys.EVIDENCE_STATUS__FEHLEND);
        evidence.setValue("status_14", Keys.EVIDENCE_STATUS__GESCHLOSSEN);
        evidence.setValue("status_25", Keys.EVIDENCE_STATUS__IMPLANTAT);
        evidence.setValue("status_26", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL);
        evidence.setValue("status_37", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL);

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("tooth_count_q1")).isEqualTo("4");
        assertThat(evidence.value("tooth_count_q2")).isEqualTo("6");
        assertThat(evidence.value("tooth_count_q3")).isEqualTo("7");
        assertThat(evidence.value("tooth_count_q4")).isEqualTo("8");
    }

    @Test
    public void it_calculates_dt() {
        DbRow evidence = testEvidence();

        evidence.setValue("caries_11_m", Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY);
        evidence.setValue("caries_22_d", Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY);
        evidence.setValue("caries_33_c", Keys.EVIDENCE_CARIES__UNKNOWN_SEVERITY);
        evidence.setValue("caries_44_v", Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY);
        evidence.setValue("caries_44_l", Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY); // do not count tooth twice
        evidence.setValue("caries_38_m", Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY); // do not count 8er

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("4");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("ft")).isEqualTo("0");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("mt")).isEqualTo("0");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("0");
    }

    @Test
    public void it_calculates_dt_for_milktooth() {
        DbRow evidence = testEvidence();

        evidence.setValue("milktooth_11", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("caries_11_m", Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY);
        evidence.setValue("milktooth_22", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("caries_22_d", Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY);
        evidence.setValue("milktooth_33", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("caries_33_c", Keys.EVIDENCE_CARIES__UNKNOWN_SEVERITY);
        evidence.setValue("caries_33_v", Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY); // do not count tooth twice
        evidence.setValue("milktooth_48", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("caries_48_m", Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY); // do not count 8er

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("0");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("3");
        assertThat(evidence.value("ft")).isEqualTo("0");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("mt")).isEqualTo("0");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("0");
    }

    @Test
    public void it_calculates_ft() {
        DbRow evidence = testEvidence();

        evidence.setValue("status_11", Keys.EVIDENCE_STATUS__KRONE);
        evidence.setValue("filling_22_m", Keys.FILLING__MATERIAL_1);
        evidence.setValue("filling_33_v", Keys.FILLING__MATERIAL_1_EXTERNAL);
        evidence.setValue("filling_44_m", Keys.FILLING__MATERIAL_2);
        evidence.setValue("filling_44_d", Keys.FILLING__MATERIAL_2); // do not count tooth twice
        evidence.setValue("status_44", Keys.EVIDENCE_STATUS__KRONE); // do not count tooth twice
        evidence.setValue("filling_18_m", Keys.FILLING__MATERIAL_2); // do not count 8er

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("0");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("ft")).isEqualTo("4");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("mt")).isEqualTo("0");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("0");
    }

    @Test
    public void it_calculates_ft_for_milkteeth() {
        DbRow evidence = testEvidence();

        evidence.setValue("milktooth_11", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_11", Keys.EVIDENCE_STATUS__KRONE);
        evidence.setValue("milktooth_22", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("filling_22_l", Keys.FILLING__MATERIAL_1);
        evidence.setValue("milktooth_33", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("filling_33_m", Keys.FILLING__MATERIAL_1_EXTERNAL);
        evidence.setValue("milktooth_44", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("filling_44_m", Keys.FILLING__MATERIAL_2);
        evidence.setValue("filling_44_d", Keys.FILLING__MATERIAL_2); // do not count tooth twice
        evidence.setValue("status_44", Keys.EVIDENCE_STATUS__KRONE); // do not count tooth twice
        evidence.setValue("milktooth_18", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("filling_18_m", Keys.FILLING__MATERIAL_2); // do not count 8er

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("0");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("ft")).isEqualTo("0");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("4");
        assertThat(evidence.value("mt")).isEqualTo("0");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("0");
    }

    @Test
    public void it_calculates_mt() {
        DbRow evidence = testEvidence();

        evidence.setValue("status_11", Keys.EVIDENCE_STATUS__BRUECKENGLIED);
        evidence.setValue("status_12", Keys.EVIDENCE_STATUS__ERSETZT);
        evidence.setValue("status_23", Keys.EVIDENCE_STATUS__FEHLEND);
        evidence.setValue("status_24", Keys.EVIDENCE_STATUS__GESCHLOSSEN);
        evidence.setValue("status_35", Keys.EVIDENCE_STATUS__IMPLANTAT);
        evidence.setValue("status_36", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL);
        evidence.setValue("status_47", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL);
        evidence.setValue("status_48", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL); // do not count 8er

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("0");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("ft")).isEqualTo("0");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("mt")).isEqualTo("7");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("0");
    }

    @Test
    public void it_calculates_mt_for_milktooth() {
        DbRow evidence = testEvidence();

        evidence.setValue("milktooth_11", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_11", Keys.EVIDENCE_STATUS__BRUECKENGLIED);
        evidence.setValue("milktooth_12", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_12", Keys.EVIDENCE_STATUS__ERSETZT);
        evidence.setValue("milktooth_23", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_23", Keys.EVIDENCE_STATUS__FEHLEND);
        evidence.setValue("milktooth_24", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_24", Keys.EVIDENCE_STATUS__GESCHLOSSEN);
        evidence.setValue("milktooth_35", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_35", Keys.EVIDENCE_STATUS__IMPLANTAT);
        evidence.setValue("milktooth_36", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_36", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL);
        evidence.setValue("milktooth_47", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_47", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL);
        evidence.setValue("milktooth_48", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_48", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL); // do not count 8er

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("0");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("ft")).isEqualTo("0");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("mt")).isEqualTo("0");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("7");
    }

    @Test
    public void it_calculates_mt_for_milkteeth() {
        DbRow evidence = testEvidence();

        evidence.setValue("milktooth_11", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_11", Keys.EVIDENCE_STATUS__BRUECKENGLIED);
        evidence.setValue("milktooth_12", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_12", Keys.EVIDENCE_STATUS__ERSETZT);
        evidence.setValue("milktooth_13", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_13", Keys.EVIDENCE_STATUS__FEHLEND);
        evidence.setValue("milktooth_14", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_14", Keys.EVIDENCE_STATUS__GESCHLOSSEN);
        evidence.setValue("milktooth_15", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_15", Keys.EVIDENCE_STATUS__IMPLANTAT);
        evidence.setValue("milktooth_16", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_16", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL);
        evidence.setValue("milktooth_17", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_17", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL);
        evidence.setValue("milktooth_18", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_18", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL); // do not count 8er

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("0");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("ft")).isEqualTo("0");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("mt")).isEqualTo("0");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("7");
    }

    @Test
    public void it_calculates_dft() {
        DbRow evidence = testEvidence();

        evidence.setValue("caries_11_m", Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY);
        evidence.setValue("milktooth_12", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("caries_12_m", Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY);
        evidence.setValue("status_13", Keys.EVIDENCE_STATUS__KRONE);
        evidence.setValue("milktooth_14", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_14", Keys.EVIDENCE_STATUS__KRONE);
        evidence.setValue("caries_14_m", Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY); // do not count tooth twice
        evidence.setValue("status_18", Keys.EVIDENCE_STATUS__KRONE); // do not count 8er


        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("1");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("2");
        assertThat(evidence.value("ft")).isEqualTo("1");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("1");
        assertThat(evidence.value("mt")).isEqualTo("0");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("dft")).isEqualTo("4");
    }

    @Test
    public void it_calculates_dmft() {
        DbRow evidence = testEvidence();

        evidence.setValue("caries_11_m", Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY); // dmft
        evidence.setValue("milktooth_12", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("caries_12_m", Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY); //dmft_milkteeth
        evidence.setValue("status_13", Keys.EVIDENCE_STATUS__KRONE); // dmft
        evidence.setValue("milktooth_14", Keys.EVIDENCE_MILKTOOTH_YES);
        evidence.setValue("status_14", Keys.EVIDENCE_STATUS__KRONE); //dmft_milkteeth
        evidence.setValue("status_15", Keys.EVIDENCE_STATUS__FEHLEND); //dmft
        evidence.setValue("caries_15_m", Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY); // do not count tooth twice
        evidence.setValue("status_18", Keys.EVIDENCE_STATUS__KRONE); // do not count 8er

        evidence.addCells(Dmft.calculate(evidence));

        assertThat(evidence.value("dt")).isEqualTo("2");
        assertThat(evidence.value("dt_milkteeth")).isEqualTo("1");
        assertThat(evidence.value("ft")).isEqualTo("1");
        assertThat(evidence.value("ft_milkteeth")).isEqualTo("1");
        assertThat(evidence.value("mt")).isEqualTo("1");
        assertThat(evidence.value("mt_milkteeth")).isEqualTo("0");
        assertThat(evidence.value("dmft")).isEqualTo("3");
        assertThat(evidence.value("dmft_milkteeth")).isEqualTo("2");
    }

    private DbRow testEvidence() {
        DbRow ret = new DbRow();
        for (String tooth : Toothnumbers.ALL) {
            ret.addCell(new DbCell(String.format("status_%s", tooth), Keys.EVIDENCE_STATUS__SOUND));
            ret.addCell(new DbCell(String.format("milktooth_%s", tooth), Keys.EVIDENCE_MILKTOOTH_NO));
            for (String surface : Keys.SURFACES) {
                ret.addCell(new DbCell(String.format("caries_%s_%s", tooth, surface), Keys.EVIDENCE_CARIES__NO_CARIES));
                ret.addCell(new DbCell(String.format("filling_%s_%s", tooth, surface), Keys.EVIDENCE_CARIES__NO_CARIES));
            }
        }
        return ret;
    }
}