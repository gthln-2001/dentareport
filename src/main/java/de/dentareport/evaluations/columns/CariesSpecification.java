package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.Translate;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.dentareport.utils.Keys.*;

// TODO: TEST?
public class CariesSpecification extends EvaluationColumn {

    public CariesSpecification(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setString(options.get(ORIGINAL_NAME),
                           caseData.event(options.get(OF))
                                   .cariesSpecification(caseData.tooth(), options.get(SURFACE)));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("Karies %s ##%s##",
                             new Translate().translate(options.get(SURFACE)),
                             options.get(OF));
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of("tooth", options.get(OF));
    }

    @Override
    public Set<String> requiredEvidenceTypes() {
        return ImmutableSet.of(EVIDENCE_TYPE_01);
    }
}