package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

public abstract class TranslatableSimpleValue extends SimpleValue {

    public TranslatableSimpleValue(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public boolean isTranslatable() {
        return true;
    }
}
