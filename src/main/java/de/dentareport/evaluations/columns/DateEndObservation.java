package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.string.StringUtils.isNoData;

// TODO: TEST?
public class DateEndObservation extends DateEvaluationColumn {

    public DateEndObservation(Evaluation evaluation,
                              Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString("date_end_observation",
                isNoData(caseData.string("date__of__event_end_observation"))
                        ? rawData.lastVisit()
                        : caseData.string("date__of__event_end_observation"));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Ende Beobachtung";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                "date__of__event_end_observation"
        );
    }
}
