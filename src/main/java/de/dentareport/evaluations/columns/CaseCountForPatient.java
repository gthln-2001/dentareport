package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.List;
import java.util.Map;

public class CaseCountForPatient extends EvaluationColumn {

    public CaseCountForPatient(Evaluation evaluation,
                               Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData,
                             CaseData caseData) {
        caseData.setString("case_count_for_patient", rawData.caseCount());
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Anzahl Fälle in Auswertung";
    }

    @Override
    public String documentationLongDe() {
        return "Anzahl der Fälle, die der Patient in der Auswertung hat.";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of();
    }
}
