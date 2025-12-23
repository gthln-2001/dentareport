package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

public abstract class DateEvaluationColumn extends EvaluationColumn {

    public DateEvaluationColumn(Evaluation evaluation,
                                Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public boolean isDate() {
        return true;
    }
}
