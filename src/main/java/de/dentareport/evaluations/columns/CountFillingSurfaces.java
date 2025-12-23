package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.Translate;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Treatments;
import de.dentareport.utils.string.NumericStringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.dentareport.utils.Keys.*;

public class CountFillingSurfaces extends EvaluationColumn {

    public CountFillingSurfaces(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        String value;
        if (options.containsKey(OF)) {
            value = evaluateEvent(caseData);
        } else {
            value = evaluateTreatment(rawData, caseData);
        }

        caseData.setString(options.get(ORIGINAL_NAME), value);
        return caseData;
    }

    private String evaluateEvent(CaseData caseData) {
        return caseData.event(options.get(OF)).countFillingSurfaces(caseData.tooth());
    }

    private String evaluateTreatment(RawData rawData, CaseData caseData) {
        String value;
        Treatments treatments = new Treatments(rawData, caseData);
        if (options.containsKey(FROM)) {
            treatments = treatments.from(caseData.string(options.get(FROM)));
        }
        if (options.containsKey(AFTER)) {
            treatments = treatments.after(caseData.string(options.get(AFTER)));
        }
        if (options.containsKey(BEFORE)) {
            treatments = treatments.before(caseData.string(options.get(BEFORE)));
        }
        if (options.containsKey(UNTIL)) {
            treatments = treatments.until(caseData.string(options.get(UNTIL)));
        }
        if (!options.containsKey(BEFORE) && !options.containsKey(UNTIL)) {
            treatments = treatments.until(caseData.string(DATE_END_SEARCH_PERIOD));
        }
        if (options.containsKey(ON)) {
            value = treatments.countFillingSurfacesOnDentition();
        } else {
            value = treatments.countFillingSurfaces();
        }
        if (options.containsKey(PER)) {
            value = NumericStringUtils.countPerTimeframe(value, options.get(PER), caseData.string(String.format(
                    "count_days__%s__%s__%s__%s",
                    options.containsKey(AFTER) ? AFTER : FROM,
                    options.containsKey(AFTER) ? options.get(AFTER) : options.getOrDefault(FROM, "date_first_visit"),
                    options.containsKey(BEFORE) ? BEFORE : UNTIL,
                    options.containsKey(BEFORE) ? options.get(BEFORE) : options.getOrDefault(UNTIL, "date_last_visit")
            )));
        }
        return value;
    }

    @Override
    public String documentationShortDe() {
        if (options.containsKey(OF)) {
            return String.format("Anzahl Füllungsflächen ##%s##", options.get(OF));
        }
        return String.format("Anzahl Füllungsflächen%s%s%s%s",
                             options.containsKey(ON) ? "" : " behandelter Zahn",
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
    public Set<String> requiredBillingpositions() {
        if (!options.containsKey(OF)) {
            return ImmutableSet.of(FUELLUNG);
        }
        return ImmutableSet.of();
    }

    @Override
    protected List<String> requiredColumns() {
        if (options.containsKey(OF)) {
            return ImmutableList.of("tooth", options.get(OF));
        }
        List<String> columns = ImmutableList.of(FROM, AFTER, BEFORE, UNTIL).stream()
                                            .filter(keyword -> options.containsKey(keyword))
                                            .map(keyword -> options.get(keyword))
                                            .collect(Collectors.toList());
        if (columns.size() == 0 || (columns.size() == 1 && (options.containsKey(FROM)
                                                            || options.containsKey(AFTER)))) {
            columns.add(DATE_END_SEARCH_PERIOD);
        }
        if (!options.containsKey(ON)) {
            columns.add("tooth");
        }
        if (options.containsKey(PER)) {
            if (options.containsKey(FROM) && options.containsKey(UNTIL)) {
                columns.add(String.format("count_days__from__%s__until__%s", options.get(FROM), options.get(UNTIL)));
            } else if (options.containsKey(FROM) && options.containsKey(BEFORE)) {
                columns.add(String.format("count_days__from__%s__before__%s", options.get(FROM), options.get(BEFORE)));
            } else if (options.containsKey(AFTER) && options.containsKey(BEFORE)) {
                columns.add(String.format("count_days__after__%s__before__%s",
                                          options.get(AFTER),
                                          options.get(BEFORE)));
            } else if (options.containsKey(AFTER) && options.containsKey(UNTIL)) {
                columns.add(String.format("count_days__after__%s__until__%s", options.get(AFTER), options.get(UNTIL)));
            } else if (options.containsKey(UNTIL)) {
                columns.add(String.format("count_days__from__date_first_visit__until__%s", options.get(UNTIL)));
            } else if (options.containsKey(BEFORE)) {
                columns.add(String.format("count_days__from__date_first_visit__before__%s", options.get(BEFORE)));
            } else if (options.containsKey(AFTER)) {
                columns.add(String.format("count_days__after__%s__until__date_last_visit", options.get(AFTER)));
            } else if (options.containsKey(FROM)) {
                columns.add(String.format("count_days__from__%s__until__date_last_visit", options.get(FROM)));
            } else {
                columns.add("count_days__from__date_first_visit__until__date_last_visit");
            }
        }
        return columns;
    }
}