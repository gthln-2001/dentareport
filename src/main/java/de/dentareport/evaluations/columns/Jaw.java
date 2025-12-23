package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.string.ToothStringUtils;

import java.util.List;
import java.util.Map;

public class Jaw extends TranslatableEvaluationColumn {

    public Jaw(Evaluation evaluation,
               Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString(
                "jaw",
                ToothStringUtils.jaw(caseData.string("tooth")));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Kiefer";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of("tooth");
    }
}
