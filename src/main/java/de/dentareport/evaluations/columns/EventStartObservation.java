package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventStartObservation extends EvaluationColumn {

    public EventStartObservation(Evaluation evaluation,
                                 Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setEvent(
                "event_start_observation",
                evaluation.eventStartObservation(rawData, caseData));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Start Beobachtung";
    }

    @Override
    public String documentationLongDe() {
        return evaluation.longDocumentationForStartObservation();
    }

    @Override
    public Set<String> requiredBillingpositions() {
        return evaluation.requiredBillingPositionsForEventStartObservation();
    }

    @Override
    public Set<String> requiredEvidenceTypes() {
        return evaluation.requiredEvidenceTypesForEventStartObservation();
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of();
    }
}
