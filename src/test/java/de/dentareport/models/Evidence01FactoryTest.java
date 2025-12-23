package de.dentareport.models;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Toothnumbers;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static de.dentareport.utils.Keys.SURFACES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Evidence01FactoryTest {

    @Test
    public void it_creates_evidence_01_from_sql_resultset(@Mocked ResultSet mockRs) throws Exception {
        new Expectations() {{
            mockRs.getString("date");
            result = "rs-date";
            mockRs.getString("billingcode");
            result = "rs-billingcode";
            mockRs.getString("dmft");
            result = "rs-dmft";
            mockRs.getString("dt");
            result = "rs-dt";
            mockRs.getString("mt");
            result = "rs-mt";
            mockRs.getString("ft");
            result = "rs-ft";
            mockRs.getString("tooth_count_q1");
            result = "rs-tooth_count_q1";
            mockRs.getString("tooth_count_q2");
            result = "rs-tooth_count_q2";
            mockRs.getString("tooth_count_q3");
            result = "rs-tooth_count_q3";
            mockRs.getString("tooth_count_q4");
            result = "rs-tooth_count_q4";
        }};
        for (String toothnumber : Toothnumbers.ALL) {
            new Expectations() {{
                mockRs.getString(String.format("status_%s", toothnumber));
                result = String.format("rs-status_%s", toothnumber);
            }};
            for (String surface : SURFACES) {
                new Expectations() {{
                    mockRs.getString(String.format("caries_%s_%s", toothnumber, surface));
                    result = String.format("rs-caries_%s_%s", toothnumber, surface);
                    mockRs.getString(String.format("filling_%s_%s", toothnumber, surface));
                    result = String.format("rs-filling_%s_%s", toothnumber, surface);
                }};
            }
        }

        Evidence01 evidence01 = Evidence01Factory.create(mockRs);

        assertThat(evidence01.date()).isEqualTo("rs-date");
        assertThat(evidence01.billingcode()).isEqualTo("rs-billingcode");
        assertThat(evidence01.dmft()).isEqualTo("rs-dmft");
        assertThat(evidence01.dt()).isEqualTo("rs-dt");
        assertThat(evidence01.mt()).isEqualTo("rs-mt");
        assertThat(evidence01.ft()).isEqualTo("rs-ft");
        assertThat(evidence01.toothcountQ1()).isEqualTo("rs-tooth_count_q1");
        assertThat(evidence01.toothcountQ2()).isEqualTo("rs-tooth_count_q2");
        assertThat(evidence01.toothcountQ3()).isEqualTo("rs-tooth_count_q3");
        assertThat(evidence01.toothcountQ4()).isEqualTo("rs-tooth_count_q4");
        for (String toothnumber : Toothnumbers.ALL) {
            assertThat(evidence01.status(toothnumber)).isEqualTo(String.format("rs-status_%s", toothnumber));
            for (String surface : SURFACES) {
                assertThat(evidence01.cariesSpecification(toothnumber, surface)).isEqualTo(String.format("rs-caries_%s_%s",
                                                                                                         toothnumber,
                                                                                                         surface));
                assertThat(evidence01.filling(toothnumber, surface)).isEqualTo(String.format("rs-filling_%s_%s",
                                                                                                         toothnumber,
                                                                                                         surface));
            }
        }
    }

    @Test
    public void it_throws_exception_when_trying_to_create_evidence_01_from_sql_result_set_with_missing_fields(
            @Mocked ResultSet mockRs) throws Exception {

        new Expectations() {{
            mockRs.getString("date");
            result = new SQLException();
        }};
        assertThatExceptionOfType(DentareportSqlException.class).isThrownBy(() ->
                                                                                    Evidence01Factory.create(mockRs)
        );
    }
}