package de.dentareport.models;

import de.dentareport.exceptions.DentareportSqlException;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PatientFactoryTest {

    @Test
    public void it_creates_patient_from_sql_resultset(@Mocked ResultSet mockRs) throws Exception {
        new Expectations() {{
            mockRs.getString("patient_index");
            result = "rs-patient_index";
            mockRs.getString("date_of_birth");
            result = "rs-date_of_birth";
            mockRs.getString("gender");
            result = "rs-gender";
            mockRs.getString("first_visit");
            result = "rs-first_visit";
            mockRs.getString("last_visit");
            result = "rs-last_visit";
        }};

        Patient patient = PatientFactory.create(mockRs);

        assertThat(patient.patientIndex()).isEqualTo("rs-patient_index");
        assertThat(patient.dateOfBirth()).isEqualTo("rs-date_of_birth");
        assertThat(patient.gender()).isEqualTo("rs-gender");
        assertThat(patient.firstVisit()).isEqualTo("rs-first_visit");
        assertThat(patient.lastVisit()).isEqualTo("rs-last_visit");
    }

    @Test
    public void it_throws_exception_when_trying_to_create_patient_from_sql_result_set_with_missing_fields(
            @Mocked ResultSet mockRs) throws Exception {

        new Expectations() {{
            mockRs.getString("patient_index");
            result = new SQLException();
        }};

        assertThatExceptionOfType(DentareportSqlException.class).isThrownBy(() ->
                PatientFactory.create(mockRs)
        );
    }

}