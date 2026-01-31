package de.dentareport.models;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.repositories.Evidences01Repository;
import de.dentareport.repositories.PatientsRepository;
import de.dentareport.repositories.TreatmentsRepository;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.ToothStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: TEST?
public class RawData {

    private Patient patient;
    private String caseCount;
    private String caseCountUpperJaw;
    private String caseCountLowerJaw;
    private List<TreatmentInterface> treatments;
    private List<Evidence01Interface> evidences01;
    private List<TreatmentInterface> treatmentsOnEvaluatedTooth;

    private RawData() {
    }

    public static RawData instance(Evaluation evaluation,
                                   String patientIndex,
                                   List<String> teeth) {
        RawData rawData = new RawData();
        rawData.treatments = TreatmentsRepository.treatments(patientIndex, evaluation.requiredBillingCodes());
        rawData.evidences01 = prepareEvidences01(patientIndex, evaluation.requiredEvidenceTypes());
        rawData.patient = PatientsRepository.patient(patientIndex);
        rawData.caseCount = String.valueOf(teeth.size());
        rawData.caseCountUpperJaw = String.valueOf(teeth.stream().filter(tooth -> ToothStringUtils.isInJaw(tooth, Keys.UPPER_JAW)).count());
        rawData.caseCountLowerJaw = String.valueOf(teeth.stream().filter(tooth -> ToothStringUtils.isInJaw(tooth, Keys.LOWER_JAW)).count());
        return rawData;
    }

    private static List<Evidence01Interface> prepareEvidences01(String patientIndex,
                                                                Set<String> requiredEvidenceTypes) {
        if (!requiredEvidenceTypes.contains(Keys.EVIDENCE_TYPE_01)) {
            return new ArrayList<>();
        }
        return Evidences01Repository.evidences01(patientIndex);
    }

    public String caseCount() {
        return caseCount;
    }

    public String caseCountUpperJaw() {
        return caseCountUpperJaw;
    }

    public String caseCountLowerJaw() {
        return caseCountLowerJaw;
    }

    public Patient patient() {
        return patient;
    }

    public String firstVisit() {
        return patient().firstVisit();
    }

    public String lastVisit() {
        return patient().lastVisit();
    }

    public String dateOfBirth() {
        return patient().dateOfBirth();
    }

    public String patientIndex() {
        return patient().patientIndex();
    }

    public String gender() {
        return patient().gender();
    }

    public List<Evidence01Interface> evidences01() {
        return evidences01;
    }

    public List<TreatmentInterface> treatments() {
        return treatments;
    }

    public List<TreatmentInterface> treatmentsOnEvaluatedTooth() {
        return treatmentsOnEvaluatedTooth;
    }

    public void setTreatmentsOnEvaluatedTooth(String tooth) {
        this.treatmentsOnEvaluatedTooth = treatments().stream()
                .filter(treatment -> treatment.isOnTooth(tooth))
                .collect(Collectors.toList());
    }
}
