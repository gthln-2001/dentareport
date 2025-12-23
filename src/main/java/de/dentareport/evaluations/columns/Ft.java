package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

public class Ft extends SimpleValue {

    public Ft(Evaluation evaluation,
              Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("°°FT°° ##%s##", options.get(OF));
    }
}
