package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

// TODO: TEST?
public class Mt extends SimpleValue {

    public Mt(Evaluation evaluation,
              Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("°°MT°° ##%s##", options.get(OF));
    }
}
