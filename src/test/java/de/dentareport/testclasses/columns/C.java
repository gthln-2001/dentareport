package de.dentareport.testclasses.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.columns.EvaluationColumn;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Keys;

import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO: TEST?
public class C extends EvaluationColumn {

    public C(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        return null;
    }

    @Override
    public String documentationShortDe() {
        return null;
    }

    @Override
    public String documentationLongDe() {
        return null;
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of("d");
    }

    @Override
    public Set<String> requiredBillingpositions() {
        return ImmutableSet.of("foo");
    }

    @Override
    public Set<String> requiredEvidenceTypes() {
        return ImmutableSet.of(Keys.EVIDENCE_TYPE_01, "X");
    }
}
