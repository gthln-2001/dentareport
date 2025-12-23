package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.string.DateStringUtils;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.*;

public class CountDays extends EvaluationColumn {

    public CountDays(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setString(options.get(ORIGINAL_NAME), value(caseData));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        String shortDoc;
        if (options.containsKey(FROM) && options.containsKey(BEFORE)) {
            shortDoc = String.format("Tage von ##%s## bis vor ##%s##",
                                     options.get(FROM),
                                     options.get(BEFORE));
        } else if (options.containsKey(OR_UNTIL)) {
            shortDoc = String.format("Tage von ##%s## bis ##%s## oder bis ##%s##",
                                     options.get(FROM),
                                     options.get(UNTIL),
                                     options.get(OR_UNTIL));
        } else {
            shortDoc = String.format("Tage von ##%s## bis ##%s##",
                                     options.get(FROM),
                                     options.get(UNTIL));
        }
        if (options.containsKey(FORMAT) && options.get(FORMAT).equals("epi")) {
            shortDoc = String.format("%s %s", shortDoc, "EPI");
        }
        return shortDoc;
    }

    @Override
    protected List<String> requiredColumns() {
        if (options.containsKey(FROM) && options.containsKey(BEFORE)) {
            return ImmutableList.of(
                    options.get(FROM),
                    options.get(BEFORE)
            );
        }
        if (options.containsKey(OR_UNTIL)) {
            return ImmutableList.of(
                    options.get(FROM),
                    options.get(OR_UNTIL),
                    options.get(UNTIL)
            );
        }
        return ImmutableList.of(
                options.get(FROM),
                options.get(UNTIL)
        );
    }

    private String value(CaseData caseData) {
        String count;
        if (options.containsKey(FROM) && options.containsKey(BEFORE)) {
            count = DateStringUtils.daysBetweenWithOffset(
                    caseData.string(options.get(FROM)),
                    caseData.string(options.get(BEFORE)),
                    -1
            );
        } else if (options.containsKey(OR_UNTIL)) {
            count = DateStringUtils.daysBetween(
                    caseData.string(options.get(FROM)),
                    caseData.string(options.get(UNTIL)),
                    caseData.string(options.get(OR_UNTIL))
            );
        } else {
            count = DateStringUtils.daysBetween(
                    caseData.string(options.get(FROM)),
                    caseData.string(options.get(UNTIL))
            );
        }

        if (options.containsKey(FORMAT) && options.get(FORMAT).equals("epi") && count.equals("0")) {
            count = "0.01";
        }

        return count;
    }
}
