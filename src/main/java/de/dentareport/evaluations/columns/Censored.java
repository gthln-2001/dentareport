package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

public class Censored extends SimpleValue {

    public Censored(Evaluation evaluation,
                    Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("Zensiert ##%s##", options.get(OF));
    }
}