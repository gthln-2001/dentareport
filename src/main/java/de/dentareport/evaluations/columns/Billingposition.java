package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

// TODO: TEST?
public class Billingposition extends TranslatableSimpleValue {

    public Billingposition(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("Gebührenposition ##%s##", options.get(OF));
    }
}
