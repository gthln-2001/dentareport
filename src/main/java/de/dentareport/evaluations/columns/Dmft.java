package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

// TODO: TEST?
public class Dmft extends SimpleValue {

    public Dmft(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("°°DMFT°° ##%s##", options.get(OF));
    }
}