package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;

import java.util.Map;

import static de.dentareport.utils.Keys.OF;

public class Dft extends SimpleValue {

    public Dft(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("°°DFT°° ##%s##", options.get(OF));
    }
}