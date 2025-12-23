package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.string.DateStringUtils;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.AT;
import static de.dentareport.utils.Keys.ORIGINAL_NAME;

public class AgeDays extends EvaluationColumn {

    public AgeDays(Evaluation evaluation,
                   Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString(
                options.get(ORIGINAL_NAME),
                DateStringUtils.daysBetween(
                        caseData.string("date_of_birth"),
                        caseData.dateOfEvent(options.get(AT))
                )
        );
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("Alter Tage ##%s##", options.get(AT));
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                "date_of_birth",
                "date__of__" + options.get(AT)
        );
    }
}