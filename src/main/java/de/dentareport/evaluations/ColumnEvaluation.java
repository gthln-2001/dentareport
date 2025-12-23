package de.dentareport.evaluations;

import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.RawData;

public class ColumnEvaluation {

    public CaseData evaluate(RawData rawData,
                             CaseData caseData,
                             Column column) {
        return column.evaluate(rawData, caseData);
    }
}