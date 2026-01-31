package de.dentareport.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class PatientTest {
    @Test
    public void it_can_be_initialized() {
        Patient patient = new Patient("a", "d", "e", "f", "g");

        assertThat(patient.patientIndex()).isEqualTo("a");
        assertThat(patient.dateOfBirth()).isEqualTo("d");
        assertThat(patient.gender()).isEqualTo("e");
        assertThat(patient.firstVisit()).isEqualTo("f");
        assertThat(patient.lastVisit()).isEqualTo("g");
    }
}