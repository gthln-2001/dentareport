package de.dentareport.evaluations;

import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.RawData;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;

// TODO: TEST?
public class CaseEvaluation {

    private final ColumnEvaluation columnEvaluation;

    public CaseEvaluation() {
        this.columnEvaluation = new ColumnEvaluation();
    }

    public DbRow evaluate(Evaluation evaluation,
                          RawData rawData,
                          String tooth) {
        CaseData caseData = new CaseData(tooth);
        rawData.setTreatmentsOnEvaluatedTooth(tooth);
        for (Column column : evaluation.columns()) {
            caseData = columnEvaluation.evaluate(rawData, caseData, column);
        }
        return caseDataToDbRow(evaluation, caseData);
    }

    private DbRow caseDataToDbRow(Evaluation evaluation,
                                  CaseData caseData) {
        DbRow ret = new DbRow();
        evaluation.requiredColumns().forEach(column -> ret.addCell(cell(column, caseData)));
        return ret;
    }

    private DbCell cell(String column,
                        CaseData caseData) {
        return new DbCell(column, caseData.string(column));
    }
}
