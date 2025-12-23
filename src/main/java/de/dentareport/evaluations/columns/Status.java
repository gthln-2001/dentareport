package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.Translate;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.EventInterface;
import de.dentareport.models.NullEvidence01;
import de.dentareport.models.RawData;
import de.dentareport.utils.Keys;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.*;

public class Status extends EvaluationColumn {

    public Status(Evaluation evaluation,
                  Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        EventInterface evidence = caseData.event(options.get(OF));
        String result;
        if (evidence.getClass().equals(NullEvidence01.class)) {
            result = NO_DATA;
        } else {
            result =  evidence.hasStatus(caseData.tooth(), options.get(IS))
                    ? CENSORED_YES
                    : CENSORED_NO;
        }
        caseData.setString(options.get(ORIGINAL_NAME), result);
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("%s ##%s##", new Translate().translate(options.get(IS)), options.get(OF));
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                options.get(OF),
                "tooth"
        );
    }
}
