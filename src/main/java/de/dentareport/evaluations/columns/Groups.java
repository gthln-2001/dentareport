package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.GroupUtils;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.OF;
import static de.dentareport.utils.Keys.ORIGINAL_NAME;

public class Groups extends EvaluationColumn {

    public Groups(Evaluation evaluation,
                  Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString(
                options.get(ORIGINAL_NAME),
                ""
        );
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("Gruppiert ##%s##", options.get(OF));
    }

    @Override
    public String documentationLongDe() {
        return GroupUtils.valuesForColumn(
                evaluation.dbTable(),
                options.get(ORIGINAL_NAME),
                options.get(OF));
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                options.get(OF)
        );
    }
}
