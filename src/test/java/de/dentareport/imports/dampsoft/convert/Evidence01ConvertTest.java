package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.Keys;
import de.dentareport.utils.dbf.DbfRow;
import org.junit.jupiter.api.Test;

import static de.dentareport.imports.dampsoft.convert.Helper.testRow;
import static de.dentareport.imports.dampsoft.convert.Helper.value;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class Evidence01ConvertTest {

    @Test
    public void it_converts_01_evidence_caries() {
        DbfRow dbfRow = testRow();

        dbfRow.setValue("ZA11", "     AAAAAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_11_m")).isEqualTo(Keys.EVIDENCE_CARIES__NO_CARIES);
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_11_d")).isEqualTo(Keys.EVIDENCE_CARIES__NO_CARIES);
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_11_o")).isEqualTo(Keys.EVIDENCE_CARIES__NO_CARIES);
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_11_l")).isEqualTo(Keys.EVIDENCE_CARIES__NO_CARIES);
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_11_v")).isEqualTo(Keys.EVIDENCE_CARIES__NO_CARIES);
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_11_c")).isEqualTo(Keys.EVIDENCE_CARIES__NO_CARIES);

        dbfRow.setValue("ZA12", "     BBBBAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__NO_CARIES);
        dbfRow.setValue("ZA12", "     GGGGAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY);
        dbfRow.setValue("ZA12", "     GGBBAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY);
        dbfRow.setValue("ZA12", "     CCGGAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__TO_TREAT_WITH_XRAY);
        dbfRow.setValue("ZA12", "     CBBGAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__TO_TREAT_WITH_XRAY);
        dbfRow.setValue("ZA12", "     HHHHAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY);
        dbfRow.setValue("ZA12", "     BHBHAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY);
        dbfRow.setValue("ZA12", "     HHCCAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITH_XRAY);
        dbfRow.setValue("ZA12", "     HBBCAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITH_XRAY);
        dbfRow.setValue("ZA12", "     BBCHAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "caries_12_d")).isEqualTo(Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITH_XRAY);
    }

    @Test
    public void it_converts_01_evidence_filling_materials() {
        DbfRow dbfRow = testRow();

        dbfRow.setValue("ZA11", "     AAAAAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_11_m")).isEqualTo(Keys.FILLING__NO_FILLING);
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_11_d")).isEqualTo(Keys.FILLING__NO_FILLING);
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_11_o")).isEqualTo(Keys.FILLING__NO_FILLING);
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_11_l")).isEqualTo(Keys.FILLING__NO_FILLING);
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_11_v")).isEqualTo(Keys.FILLING__NO_FILLING);
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_11_c")).isEqualTo(Keys.FILLING__NO_FILLING);

        dbfRow.setValue("ZA12", "     BBBBAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_d")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA12", "     AAAABBBBAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_v")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA12", "     AAAAAAAABBBBAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_m")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA12", "     AAAAAAAAAAAABBBBAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_l")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA12", "     AAAAAAAAAAAAAAAABBBBAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_o")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA12", "     AAAAAAAAAAAAAAAAAAAABBBB                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_c")).isEqualTo(Keys.FILLING__MATERIAL_1);

        dbfRow.setValue("ZA23", "     BBBBAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_23_m")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA23", "     AAAABBBBAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_23_v")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA23", "     AAAAAAAABBBBAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_23_d")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA23", "     AAAAAAAAAAAABBBBAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_23_l")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA23", "     AAAAAAAAAAAAAAAABBBBAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_23_o")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA23", "     AAAAAAAAAAAAAAAAAAAABBBB                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_23_c")).isEqualTo(Keys.FILLING__MATERIAL_1);

        dbfRow.setValue("ZA34", "     BBBBAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_34_m")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA34", "     AAAABBBBAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_34_l")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA34", "     AAAAAAAABBBBAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_34_d")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA34", "     AAAAAAAAAAAABBBBAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_34_v")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA34", "     AAAAAAAAAAAAAAAABBBBAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_34_o")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA34", "     AAAAAAAAAAAAAAAAAAAABBBB                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_34_c")).isEqualTo(Keys.FILLING__MATERIAL_1);

        dbfRow.setValue("ZA46", "     BBBBAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_46_d")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA46", "     AAAABBBBAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_46_l")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA46", "     AAAAAAAABBBBAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_46_m")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA46", "     AAAAAAAAAAAABBBBAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_46_v")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA46", "     AAAAAAAAAAAAAAAABBBBAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_46_o")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA46", "     AAAAAAAAAAAAAAAAAAAABBBB                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_46_c")).isEqualTo(Keys.FILLING__MATERIAL_1);

        dbfRow.setValue("ZA12", "     AAAABBBBBBBBBBBBBBBBBBBB                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_d")).isEqualTo(Keys.FILLING__NO_FILLING);
        dbfRow.setValue("ZA12", "     CCCCAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_d")).isEqualTo(Keys.FILLING__NO_FILLING);
        dbfRow.setValue("ZA12", "     CGHBAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_d")).isEqualTo(Keys.FILLING__MATERIAL_1);
        dbfRow.setValue("ZA12", "     DDDDAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_d")).isEqualTo(Keys.FILLING__MATERIAL_2);
        dbfRow.setValue("ZA12", "     DAADAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_d")).isEqualTo(Keys.FILLING__MATERIAL_2_EXTERNAL);

        dbfRow.setValue("ZA12", "     ZZZZAAAAAAAAAAAAAAAAAAAA                               ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "filling_12_d")).isEqualTo("unknown filling: Z");
    }

    @Test
    public void it_converts_01_evidence_milk_tooth() {
        DbfRow dbfRow = testRow();

        dbfRow.setValue("ZA11", "                                                            ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "milktooth_11")).isEqualTo(Keys.EVIDENCE_MILKTOOTH_NO);

        dbfRow.setValue("ZA11", "A                                                           ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "milktooth_11")).isEqualTo(Keys.EVIDENCE_MILKTOOTH_YES);

        dbfRow.setValue("ZA11", "B                                                           ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "milktooth_11")).isEqualTo(Keys.EVIDENCE_MILKTOOTH_YES);

        dbfRow.setValue("ZA11", "F                                                           ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "milktooth_11")).isEqualTo(Keys.EVIDENCE_MILKTOOTH_YES);

        dbfRow.setValue("ZA11", "X                                                           ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "milktooth_11")).isEqualTo(Keys.EVIDENCE_MILKTOOTH_NO);

        dbfRow.setValue("ZA25", "A                                                           ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "milktooth_25")).isEqualTo(Keys.EVIDENCE_MILKTOOTH_YES);

        dbfRow.setValue("ZA26", "A                                                           ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "milktooth_26")).isEqualTo(Keys.EVIDENCE_MILKTOOTH_NO);
    }

    @Test
    public void it_converts_01_evidence_sealing() {
        DbfRow dbfRow = testRow();

        dbfRow.setValue("ZA11", "                                                            ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "sealing_11")).isEqualTo(EVIDENCE_SEALING__NOT_SEALED);

        dbfRow.setValue("ZA11", "                                      U                     ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "sealing_11")).isEqualTo(EVIDENCE_SEALING__SEALING_NECESSARY);

        dbfRow.setValue("ZA11", "                                      V                     ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "sealing_11")).isEqualTo(EVIDENCE_SEALING__SEALED);

        dbfRow.setValue("ZA11", "                                      Z                     ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "sealing_11")).isEqualTo("unknown sealing: Z");
    }

    @Test
    public void it_converts_01_evidence_tooth_status() {
        DbfRow dbfRow = testRow();

        dbfRow.setValue("ZA11", "                                                            ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_11")).isEqualTo(EVIDENCE_STATUS__SOUND);

        dbfRow.setValue("ZA11", " b                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_11")).isEqualTo(EVIDENCE_STATUS__BRUECKENGLIED);

        dbfRow.setValue("ZA11", " ?                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_11")).isEqualTo("unknown status: ?");

        dbfRow.setValue("ZA11", " )                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_11")).isEqualTo(EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL);

        dbfRow.setValue("ZA21", " )                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_21")).isEqualTo(EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL);

        dbfRow.setValue("ZA31", " )                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_31")).isEqualTo(EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL);

        dbfRow.setValue("ZA41", " )                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_41")).isEqualTo(EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL);

        dbfRow.setValue("ZA11", " (                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_11")).isEqualTo(EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL);

        dbfRow.setValue("ZA21", " (                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_21")).isEqualTo(EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL);

        dbfRow.setValue("ZA31", " (                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_31")).isEqualTo(EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL);

        dbfRow.setValue("ZA41", " (                                                          ");
        assertThat(value(Evidence01Convert.convert(dbfRow), "status_41")).isEqualTo(EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL);
    }


}