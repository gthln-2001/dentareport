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

public class CariesSurfaces extends EvaluationColumn {

    public CariesSurfaces(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setString(options.get(ORIGINAL_NAME),
                           String.join(",", caseData.event(options.get(OF))
                                   .cariesSurfaces(caseData.tooth(), options.get(SPECIFICATION))));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("Flächen Karies %s ##%s##",
                             new Translate().translate(options.get(SPECIFICATION)),
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