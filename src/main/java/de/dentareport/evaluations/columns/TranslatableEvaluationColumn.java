package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

public abstract class TranslatableEvaluationColumn extends EvaluationColumn {

    public TranslatableEvaluationColumn(Evaluation evaluation,
                                        Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public boolean isTranslatable() {
        return true;
    }
}
