package de.dentareport.groups;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.Column;

import java.util.List;
import java.util.stream.Collectors;

import static de.dentareport.utils.Keys.OF;
import static de.dentareport.utils.Keys.ORIGINAL_NAME;

public class Groups {

    private final ColumnGroups columnGroups;
    private Evaluation evaluation;

    public Groups(Evaluation evaluation) {
        this.evaluation = evaluation;
        this.columnGroups = new ColumnGroups();
    }

    public void group() {
        columns().forEach(column -> columnGroups.group(
                evaluation.dbTable(),
                column.option(OF),
                column.option(ORIGINAL_NAME)));
    }

    private List<Column> columns() {
        return evaluation.requiredColumns().stream()
                         .map(column -> new Column(evaluation, column))
                         .filter(Column::isGroupable)
                         .collect(Collectors.toList());
    }
}
