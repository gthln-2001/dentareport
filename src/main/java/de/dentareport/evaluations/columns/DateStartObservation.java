package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;

public class DateStartObservation extends DateEvaluationColumn {

    public DateStartObservation(Evaluation evaluation,
                                Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString(
                "date_start_observation",
                caseData.string("date__of__event_start_observation"));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Start Beobachtung";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                "date__of__event_start_observation"
        );
    }
}
