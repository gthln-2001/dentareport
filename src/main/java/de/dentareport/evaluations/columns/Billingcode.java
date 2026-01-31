package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

// TODO: TEST?
public class Billingcode extends SimpleValue {

    public Billingcode(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("Gebührennummer ##%s##", options.get(OF));
    }
}
