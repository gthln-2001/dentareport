package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

public class Toothcount extends SimpleValue {

    public Toothcount(Evaluation evaluation,
                      Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("°°Anzahl Zähne°° ##%s##", options.get(OF));
    }
}
