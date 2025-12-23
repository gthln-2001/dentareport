package de.dentareport.testclasses.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.columns.EvaluationColumn;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;

public class D extends EvaluationColumn {

    public D(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        return null;
    }

    @Override
    public String documentationShortDe() {
        return null;
    }

    @Override
    public String documentationLongDe() {
        return null;
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of("a");
    }
}
