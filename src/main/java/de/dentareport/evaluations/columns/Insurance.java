package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Keys;

import java.util.List;
import java.util.Map;
import java.util.Objects;

// TODO: TEST?
public class Insurance extends EvaluationColumn {

    public Insurance(Evaluation evaluation,
                     Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString("insurance", insurance(caseData));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Versicherung";
    }

    @Override
    public String documentationLongDe() {
        return "Abgeleitet aus HKP zu Start Beobachtung oder " +
                "GebNr bei letztem 01-Befund vor Start Beobachtung";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                "event_start_observation",
                "evidence_01__position__last__before__date_start_observation"
        );
    }

    private String insurance(CaseData caseData) {
        if (isGesetzlich(caseData)) {
            return Keys.INSURANCE_GESETZLICH;
        } else if (isPrivat(caseData)) {
            return Keys.INSURANCE_PRIVAT;
        }
        return Keys.INSURANCE_GESETZLICH;
    }

    private boolean isGesetzlich(CaseData caseData) {
        return isEventStartObservationGesetzlich(caseData);
    }

    private boolean isEventStartObservationGesetzlich(CaseData caseData) {
        return Keys.DAMPSOFT_INSURANCE_GESETZLICH.contains(
                caseData.insuranceOfEvent("event_start_observation"));
    }

    private boolean isPrivat(CaseData caseData) {
        return isEventStartObservationPrivat(caseData)
                || isEvidence01BeforeStartObservationPrivat(caseData);
    }

    private boolean isEventStartObservationPrivat(CaseData caseData) {
        return Keys.DAMPSOFT_INSURANCE_PRIVAT.contains(
                caseData.insuranceOfEvent("event_start_observation"));
    }

    private boolean isEvidence01BeforeStartObservationPrivat(CaseData caseData) {
        return isBillingcodeLast01BeforeStartObservation(caseData, "001")
                || isBillingcodeLast01BeforeStartObservation(caseData, "0010");
    }

    private boolean isBillingcodeLast01BeforeStartObservation(CaseData caseData, String billingcode) {
        return Objects.equals(
                caseData.event("evidence_01__position__last__before__date_start_observation")
                        .billingcode()
                , billingcode);
    }
}
