package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

// TODO: TEST?
public class Handler extends SimpleValue {

    public Handler(Evaluation evaluation,
                   Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("Behandler ##%s##", options.get(OF));
    }
}
