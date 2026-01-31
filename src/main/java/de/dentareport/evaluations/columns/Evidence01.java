package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Evidence01Interface;
import de.dentareport.models.RawData;
import de.dentareport.utils.Evidences01;
import de.dentareport.utils.Keys;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.dentareport.utils.Keys.*;

// TODO: TEST?
public class Evidence01 extends EvaluationColumn {

    public Evidence01(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setEvent(options.get(ORIGINAL_NAME), value(rawData, caseData));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("%s 01%s%s",
                             options.get(POSITION).equals(FIRST) ? "Erster" : "Letzter",
                             (options.containsKey(FROM)
                              ? String.format(" ab ##%s##", options.get(FROM))
                              : (options.containsKey(AFTER)
                                 ? String.format(" nach ##%s##", options.get(AFTER))
                                 : "")),
                             (options.containsKey(BEFORE)
                              ? String.format(" vor ##%s##", options.get(BEFORE))
                              : (options.containsKey(UNTIL)
                                 ? String.format(" bis ##%s##", options.get(UNTIL))
                                 : "")));
    }

    @Override
    public Set<String> requiredEvidenceTypes() {
        return ImmutableSet.of(Keys.EVIDENCE_TYPE_01);
    }

    @Override
    protected List<String> requiredColumns() {
        List<String> requiredColumns = ImmutableList.of(FROM, AFTER, BEFORE, UNTIL).stream()
                                                    .filter(keyword -> options.containsKey(keyword))
                                                    .map(keyword -> options.get(keyword))
                                                    .collect(Collectors.toList());
        if (requiredColumns.size() == 0 || (requiredColumns.size() == 1 && (options.containsKey(FROM)
                                                                            || options.containsKey(AFTER)))) {
            requiredColumns.add(DATE_END_SEARCH_PERIOD);
        }
        return requiredColumns;
    }

    private Evidence01Interface value(RawData rawData, CaseData caseData) {
        if (options.get(POSITION).equals(FIRST)) {
            if (options.containsKey(AFTER) && options.containsKey(UNTIL)) {
                return Evidences01.firstAfterUntil(rawData,
                                                   caseData.string(options.get(AFTER)),
                                                   caseData.string(options.get(UNTIL))
                );
            } else if (options.containsKey(AFTER) && options.containsKey(BEFORE)) {
                return Evidences01.firstAfterBefore(rawData,
                                                    caseData.string(options.get(AFTER)),
                                                    caseData.string(options.get(BEFORE))
                );
            } else if (options.containsKey(FROM) && options.containsKey(UNTIL)) {
                return Evidences01.firstFromUntil(rawData,
                                                  caseData.string(options.get(FROM)),
                                                  caseData.string(options.get(UNTIL))
                );
            } else if (options.containsKey(FROM) && options.containsKey(BEFORE)) {
                return Evidences01.firstFromBefore(rawData,
                                                   caseData.string(options.get(FROM)),
                                                   caseData.string(options.get(BEFORE))
                );
            } else if (options.containsKey(UNTIL)) {
                return Evidences01.firstUntil(rawData,
                                              caseData.string(options.get(UNTIL))
                );
            } else if (options.containsKey(BEFORE)) {
                return Evidences01.firstBefore(rawData,
                                               caseData.string(options.get(BEFORE))
                );
            } else if (options.containsKey(AFTER)) {
                return Evidences01.firstAfterUntil(rawData,
                                                   caseData.string(options.get(AFTER)),
                                                   caseData.string(DATE_END_SEARCH_PERIOD)
                );
            } else if (options.containsKey(FROM)) {
                return Evidences01.firstFromUntil(rawData,
                                                  caseData.string(options.get(FROM)),
                                                  caseData.string(DATE_END_SEARCH_PERIOD)
                );
            }
            return Evidences01.firstUntil(rawData,
                                          caseData.string(DATE_END_SEARCH_PERIOD)
            );
        }
        if (options.containsKey(AFTER) && options.containsKey(UNTIL)) {
            return Evidences01.lastAfterUntil(rawData,
                                              caseData.string(options.get(AFTER)),
                                              caseData.string(options.get(UNTIL))
            );
        } else if (options.containsKey(AFTER) && options.containsKey(BEFORE)) {
            return Evidences01.lastAfterBefore(rawData,
                                               caseData.string(options.get(AFTER)),
                                               caseData.string(options.get(BEFORE))
            );
        } else if (options.containsKey(FROM) && options.containsKey(UNTIL)) {
            return Evidences01.lastFromUntil(rawData,
                                             caseData.string(options.get(FROM)),
                                             caseData.string(options.get(UNTIL))
            );
        } else if (options.containsKey(FROM) && options.containsKey(BEFORE)) {
            return Evidences01.lastFromBefore(rawData,
                                              caseData.string(options.get(FROM)),
                                              caseData.string(options.get(BEFORE))
            );
        } else if (options.containsKey(UNTIL)) {
            return Evidences01.lastUntil(rawData,
                                         caseData.string(options.get(UNTIL))
            );
        } else if (options.containsKey(BEFORE)) {
            return Evidences01.lastBefore(rawData,
                                          caseData.string(options.get(BEFORE))
            );
        } else if (options.containsKey(AFTER)) {
            return Evidences01.lastAfterUntil(rawData,
                                              caseData.string(options.get(AFTER)),
                                              caseData.string(DATE_END_SEARCH_PERIOD)
            );
        } else if (options.containsKey(FROM)) {
            return Evidences01.lastFromUntil(rawData,
                                             caseData.string(options.get(FROM)),
                                             caseData.string(DATE_END_SEARCH_PERIOD)
            );
        }
        return Evidences01.lastUntil(rawData,
                                     caseData.string(DATE_END_SEARCH_PERIOD)
        );
    }
}
