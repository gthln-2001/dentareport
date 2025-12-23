package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.OF;

public class Year extends SimpleValue {

    public Year(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public String documentationShortDe() {
        return String.format("Jahr ##%s##", options.get(OF));
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(options.get(OF));
    }
}