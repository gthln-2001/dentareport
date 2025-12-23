package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Treatments;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.dentareport.utils.Keys.*;

public class EventsOfInterest extends EvaluationColumn {

    public EventsOfInterest(Evaluation evaluation,
                            Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setEventlist(options.get(ORIGINAL_NAME),
                              new Treatments(rawData, caseData).after(caseData.string(options.get(AFTER)))
                                                               .until(caseData.string(DATE_END_SEARCH_PERIOD))
                                                               .withBillingpositions(evaluation.requiredBillingPositionsForEventsOfInterest(
                                                             options))
                                                               .list());
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("Ereignisse nach ##%s##", options.get(AFTER));
    }

    @Override
    public Set<String> requiredBillingpositions() {
        return evaluation.requiredBillingPositionsForEventsOfInterest(options);
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                options.get(AFTER),
                DATE_END_SEARCH_PERIOD
        );
    }
}
