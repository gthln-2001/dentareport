package de.dentareport.models;

import com.google.common.collect.ImmutableList;
import de.dentareport.exceptions.DentareportSqlException;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TreatmentFactoryTest {

    @Test
    public void it_creates_treatment_from_sql_resultset(@Mocked ResultSet mockRs) throws Exception {
        new Expectations() {{
            mockRs.getString("date");
            result = "rs-date";
            mockRs.getString("tooth");
            result = "rs-tooth";
            mockRs.getString("billingcode");
            result = "rs-billingcode";
            mockRs.getString("surfaces");
            result = "s1,s2,s4";
            mockRs.getString("handler");
            result = "rs-handler";
            mockRs.getString("insurance");
            result = "rs-insurance";
            mockRs.getString("comment");
            result = "rs-comment";
            mockRs.getString("source");
            result = "rs-source";
        }};

        Treatment treatment = TreatmentFactory.create(mockRs);

        assertThat(treatment.date()).isEqualTo("rs-date");
        assertThat(treatment.tooth()).isEqualTo("rs-tooth");
        assertThat(treatment.billingcode()).isEqualTo("rs-billingcode");
        assertThat(treatment.surfaces()).isEqualTo(ImmutableList.of("s1", "s2", "s4"));
        assertThat(treatment.handler()).isEqualTo("rs-handler");
        assertThat(treatment.insurance()).isEqualTo("rs-insurance");
        assertThat(treatment.comment()).isEqualTo("rs-comment");
        assertThat(treatment.source()).isEqualTo("rs-source");
    }

    @Test
    public void it_throws_exception_when_trying_to_create_treatment_from_sql_result_set_with_missing_fields(
            @Mocked ResultSet mockRs) throws Exception {

        new Expectations() {{
            mockRs.getString("date");
            result = new SQLException();
        }};

        assertThatExceptionOfType(DentareportSqlException.class).isThrownBy(() ->
                TreatmentFactory.create(mockRs)
        );
    }
}