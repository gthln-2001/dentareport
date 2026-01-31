package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;

// TODO: TEST?
public class DateLastVisit extends DateEvaluationColumn {

    public DateLastVisit(Evaluation evaluation,
                         Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString(
                "date_last_visit",
                rawData.lastVisit());
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Letzte Beobachtung Patient";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of();
    }
}
