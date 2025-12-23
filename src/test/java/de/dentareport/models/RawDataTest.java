package de.dentareport.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.repositories.Evidences01Repository;
import de.dentareport.repositories.PatientsRepository;
import de.dentareport.repositories.TreatmentsRepository;
import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RawDataTest {

    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    Evidences01Repository mockEvidences01Repository;
    @Mocked
    Patient mockPatient;
    @Mocked
    PatientsRepository mockPatientsRepository;
    @Mocked
    TreatmentsRepository mockTreatmentsRepository;

    @Test
    public void it_gets_patient_data() {
        new Expectations() {{
            PatientsRepository.patient("3456");
        }};

        RawData result = RawData.instance(mockEvaluation, "3456", teeth());

        assertThat(result.patient()).isEqualTo(mockPatient);
        assertThat(result.caseCount()).isEqualTo("3");
        assertThat(result.caseCountUpperJaw()).isEqualTo("2");
        assertThat(result.caseCountLowerJaw()).isEqualTo("1");
    }

    @Test
    public void it_gets_first_visit_of_patient() {
        new Expectations() {{
            PatientsRepository.patient("3456");
            mockPatient.firstVisit();
            result = "date-of-first-visit";
        }};

        RawData result = RawData.instance(mockEvaluation, "3456", teeth());

        assertThat(result.firstVisit()).isEqualTo("date-of-first-visit");
    }

    @Test
    public void it_gets_last_visit_of_patient() {
        new Expectations() {{
            PatientsRepository.patient("3456");
            mockPatient.lastVisit();
            result = "date-of-last-visit";
        }};

        RawData result = RawData.instance(mockEvaluation, "3456", teeth());

        assertThat(result.lastVisit()).isEqualTo("date-of-last-visit");
    }

    @Test
    public void it_gets_date_of_birth_of_patient() {
        new Expectations() {{
            PatientsRepository.patient("3456");
            mockPatient.dateOfBirth();
            result = "date-of-birth";
        }};

        RawData result = RawData.instance(mockEvaluation, "3456", teeth());

        assertThat(result.dateOfBirth()).isEqualTo("date-of-birth");
    }

    @Test
    public void it_gets_patient_index() {
        new Expectations() {{
            PatientsRepository.patient("3456");
            mockPatient.patientIndex();
            result = "some-patient-index";
        }};

        RawData result = RawData.instance(mockEvaluation, "3456", teeth());

        assertThat(result.patientIndex()).isEqualTo("some-patient-index");
    }

    @Test
    public void it_gets_gender_of_patient() {
        new Expectations() {{
            PatientsRepository.patient("3456");
            mockPatient.gender();
            result = "patient-gender";
        }};

        RawData result = RawData.instance(mockEvaluation, "3456", teeth());

        assertThat(result.gender()).isEqualTo("patient-gender");
    }

    @Test
    public void it_sets_and_gets_treatments_on_evaluated_tooth() {
        Set<String> billingCodes = ImmutableSet.of("foo", "bar");
        List<Treatment> treatments = ImmutableList.of(
                new Treatment("a", "11", "b", "", "c", "d", "comm1", "e"),
                new Treatment("f", "12", "g", "", "h", "i", "comm2", "j"),
                new Treatment("k", "12", "l", "", "m", "n", "comm3", "o")
        );

        new Expectations() {{
            mockEvaluation.requiredBillingCodes();
            result = billingCodes;
            PatientsRepository.patient("3456");
            TreatmentsRepository.treatments("3456", billingCodes);
            result = treatments;
        }};

        RawData rawData = RawData.instance(mockEvaluation, "3456", teeth());

        rawData.setTreatmentsOnEvaluatedTooth("12");

        assertThat(rawData.treatmentsOnEvaluatedTooth().size()).isEqualTo(2);
        assertThat(rawData.treatmentsOnEvaluatedTooth().get(0).billingcode()).isEqualTo("g");
        assertThat(rawData.treatmentsOnEvaluatedTooth().get(1).billingcode()).isEqualTo("l");

        assertThat(rawData.treatments()).isEqualTo(treatments);
    }

    @Test
    public void it_sets_and_gets_evidences_01() {
        Set<String> evidenceTypes = ImmutableSet.of(Keys.EVIDENCE_TYPE_01);
        List<Evidence01> evidences = ImmutableList.of(
                new Evidence01("1990-01-01",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               new HashMap<>(),
                               new HashMap<>(),
                               new HashMap<>()),
                new Evidence01("1999-01-01",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               "",
                               new HashMap<>(),
                               new HashMap<>(),
                               new HashMap<>())
        );

        new Expectations() {{
            mockEvaluation.requiredEvidenceTypes();
            result = evidenceTypes;
            Evidences01Repository.evidences01("3456");
            result = evidences;
        }};

        RawData rawData = RawData.instance(mockEvaluation, "3456", teeth());

        assertThat(rawData.evidences01()).isEqualTo(evidences);
    }

    @Test
    public void it_sets_evidences_01_to_empty_list_if_01_evidences_are_not_required() {
        Set<String> evidenceTypes = ImmutableSet.of("foo", "bar");

        new Expectations() {{
            mockEvaluation.requiredEvidenceTypes();
            result = evidenceTypes;
        }};

        RawData rawData = RawData.instance(mockEvaluation, "3456", teeth());

        new Verifications() {{
            Evidences01Repository.evidences01("3456");
            times = 0;
        }};

        assertThat(rawData.evidences01()).isEmpty();
    }

    private List<String> teeth() {
        return ImmutableList.of("11", "22", "33");
    }
}