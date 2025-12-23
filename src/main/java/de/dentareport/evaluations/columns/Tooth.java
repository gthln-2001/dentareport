package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;

public class Tooth extends EvaluationColumn {

    public Tooth(Evaluation evaluation,
                 Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setString(
                "tooth",
                caseData.tooth());
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Zahn";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of();
    }
}
