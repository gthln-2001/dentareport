package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.*;

public class EventOfInterest extends EvaluationColumn {

    public EventOfInterest(Evaluation evaluation,
                           Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setEvent(
                options.get(ORIGINAL_NAME),
                caseData.eventFromEventlist(
                        "events_of_interest__after__" + options.get(AFTER),
                        options.get(POSITION))
        );
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("°°Ereignis %s°° nach ##%s##",
                             options.get(POSITION),
                             options.get(AFTER));
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                "events_of_interest__after__" + options.get(AFTER)
        );
    }
}
