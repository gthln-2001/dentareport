package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

// TODO: TEST?
public class Date extends DateSimpleValue {

    public Date(Evaluation evaluation,
                Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("##%s##", options.get(OF));
    }
}
