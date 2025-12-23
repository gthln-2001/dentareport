package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.models.TreatmentInterface;
import de.dentareport.utils.TreatmentFilters;
import de.dentareport.utils.Treatments;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.dentareport.utils.Keys.*;

public class Treatment extends EvaluationColumn {

    public Treatment(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setEvent(options.get(ORIGINAL_NAME), value(rawData, caseData));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("%s Behandlung %s~~%s~~%s%s%s",
                             options.get(POSITION).equals(FIRST) ? "Erste" : "Letzte",
                             options.containsKey(ON) && options.get(ON).equals(DENTITION) ? "Gebiss " : "",
                             options.get(WITH),
                             (options.containsKey(FROM)
                              ? String.format(" ab ##%s##", options.get(FROM))
                              : (options.containsKey(AFTER)
                                 ? String.format(" nach ##%s##", options.get(AFTER))
                                 : "")),
                             (options.containsKey(BEFORE)
                              ? String.format(" vor ##%s##", options.get(BEFORE))
                              : (options.containsKey(UNTIL)
                                 ? String.format(" bis ##%s##", options.get(UNTIL))
                                 : "")),
                             (options.containsKey(FILTER_ONLY)
                              ? String.format(" (%s)", TreatmentFilters.shortDoc(options.get(FILTER_ONLY)))
                              : ""
                             )
        );
    }

    @Override
    public Set<String> requiredBillingpositions() {
        return ImmutableSet.of(options.get(WITH));
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
        if (options.containsKey(FILTER_ONLY)) {
            requiredColumns.addAll(TreatmentFilters.requiredColumns(options.get(FILTER_ONLY)));
        }
        return requiredColumns;
    }

    private TreatmentInterface value(RawData rawData, CaseData caseData) {
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
        if (options.containsKey(WITH)) {
            treatments = treatments.withBillingposition(options.get(WITH));
        }
        if (options.containsKey(FILTER_ONLY)) {
            treatments = treatments.filterOnly(options.get(FILTER_ONLY));
        }
        if (options.containsKey(ON) && options.get(ON).equals(DENTITION)) {
            if (options.get(POSITION).equals(FIRST)) {
                return treatments.firstOnDentition();
            }
            return treatments.lastOnDentition();
        }
        if (options.get(POSITION).equals(FIRST)) {
            return treatments.first();
        }
        return treatments.last();
    }
}

