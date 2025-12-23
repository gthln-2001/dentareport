package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

public class Dt extends SimpleValue {

    public Dt(Evaluation evaluation,
              Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("°°DT°° ##%s##", options.get(OF));
    }
}