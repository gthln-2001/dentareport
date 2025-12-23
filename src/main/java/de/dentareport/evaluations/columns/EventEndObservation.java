package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventEndObservation extends EvaluationColumn {

    public EventEndObservation(Evaluation evaluation,
                               Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setEvent(
                "event_end_observation",
                evaluation.eventEndObservation(rawData, caseData));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Ende Beobachtung";
    }

    @Override
    public String documentationLongDe() {
        return evaluation.longDocumentationForEndObservation();
    }

    @Override
    protected List<String> requiredColumns() {
        return evaluation.dependenciesForEventEndObservation();
    }

    @Override
    public Set<String> requiredBillingpositions() {
        return evaluation.requiredBillingPositionsForEventEndObservation();
    }

    @Override
    public Set<String> requiredEvidenceTypes() {
        return evaluation.requiredEvidenceTypesForEventEndObservation();
    }
}
