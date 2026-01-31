package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.OF;
import static de.dentareport.utils.Keys.ORIGINAL_NAME;

// TODO: TEST?
public class Toothcontacts extends EvaluationColumn {

    public Toothcontacts(Evaluation evaluation,
                         Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString(
                options.get(ORIGINAL_NAME),
                caseData.toothcontactsOfEvent(options.get(OF), caseData.string("tooth"))
        );
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("°°Zahnkontakte°° ##%s##", options.get(OF));
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                options.get(OF),
                "tooth"
        );
    }
}
