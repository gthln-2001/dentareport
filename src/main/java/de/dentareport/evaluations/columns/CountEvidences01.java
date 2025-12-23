package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.Translate;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Evidences01;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.NumericStringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.dentareport.utils.Keys.*;

public class CountEvidences01 extends EvaluationColumn {

    public CountEvidences01(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        String value;
        if (options.containsKey(AFTER) && options.containsKey(UNTIL)) {
            value = Evidences01.countAfterUntil(
                    rawData,
                    caseData.string(options.get(AFTER)),
                    caseData.string(options.get(UNTIL))
            );
        } else if (options.containsKey(AFTER) && options.containsKey(BEFORE)) {
            value = Evidences01.countAfterBefore(
                    rawData,
                    caseData.string(options.get(AFTER)),
                    caseData.string(options.get(BEFORE))
            );
        } else if (options.containsKey(FROM) && options.containsKey(UNTIL)) {
            value = Evidences01.countFromUntil(
                    rawData,
                    caseData.string(options.get(FROM)),
                    caseData.string(options.get(UNTIL))
            );
            if (options.containsKey(PER)) {
                value = NumericStringUtils.countPerTimeframe(value,
                                                             options.get(PER),
                                                             caseData.string(String.format(
                                                                     "count_days__from__%s__until__%s",
                                                                     options.get(FROM),
                                                                     options.get(UNTIL))));
            }
        } else if (options.containsKey(FROM) && options.containsKey(BEFORE)) {
            value = Evidences01.countFromBefore(
                    rawData,
                    caseData.string(options.get(FROM)),
                    caseData.string(options.get(BEFORE))
            );
        } else if (options.containsKey(UNTIL)) {
            value = Evidences01.countUntil(
                    rawData,
                    caseData.string(options.get(UNTIL))
            );
        } else if (options.containsKey(BEFORE)) {
            value = Evidences01.countBefore(
                    rawData,
                    caseData.string(options.get(BEFORE))
            );
        } else if (options.containsKey(AFTER)) {
            value = Evidences01.countAfter(
                    rawData,
                    caseData.string(options.get(AFTER))
            );
        } else if (options.containsKey(FROM)) {
            value = Evidences01.countFrom(
                    rawData,
                    caseData.string(options.get(FROM))
            );
        } else {
            value = Evidences01.count(rawData);
        }
        caseData.setString(options.get(ORIGINAL_NAME), value);
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("Anzahl 01%s%s%s",
                             options.containsKey(FROM)
                             ? String.format(" ab ##%s##", options.get(FROM))
                             : (options.containsKey(AFTER) ? String.format(" nach ##%s##", options.get(AFTER)) : ""),
                             options.containsKey(BEFORE)
                             ? String.format(" vor ##%s##", options.get(BEFORE))
                             : (options.containsKey(UNTIL) ? String.format(" bis ##%s##", options.get(UNTIL)) : ""),
                             options.containsKey(PER)
                             ? String.format(" pro %s", new Translate().translate(options.get(PER)))
                             : "");
    }

    @Override
    protected List<String> requiredColumns() {
        List<String> columns = ImmutableList.of(FROM, AFTER, BEFORE, UNTIL).stream()
                                            .filter(keyword -> options.containsKey(keyword))
                                            .map(keyword -> options.get(keyword))
                                            .collect(Collectors.toList());
        if (options.containsKey(PER)) {
            if (options.containsKey(FROM) && options.containsKey(UNTIL)) {
                columns.add(String.format("count_days__from__%s__until__%s", options.get(FROM), options.get(UNTIL)));
            }
        }
        return columns;
    }

    @Override
    public Set<String> requiredEvidenceTypes() {
        return ImmutableSet.of(Keys.EVIDENCE_TYPE_01);
    }
}