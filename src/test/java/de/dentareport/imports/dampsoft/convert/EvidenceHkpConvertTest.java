package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.dbf.DbfRow;
import org.junit.jupiter.api.Test;

import static de.dentareport.imports.dampsoft.convert.Helper.testRow;
import static de.dentareport.imports.dampsoft.convert.Helper.value;
import static org.assertj.core.api.Assertions.assertThat;

public class EvidenceHkpConvertTest {

    @Test
    public void it_converts_hkp_evidence_tooth_status() {
        DbfRow dbfRow = testRow();

        dbfRow.setValue("ZA11", "");
        assertThat(value(EvidenceHkpConvert.convert(dbfRow), "status_11")).isEqualTo("");

        dbfRow.setValue("ZA22", "X");
        assertThat(value(EvidenceHkpConvert.convert(dbfRow), "status_22")).isEqualTo("X");

        dbfRow.setValue("ZA47", " y ");
        assertThat(value(EvidenceHkpConvert.convert(dbfRow), "status_47")).isEqualTo("y");
    }
}