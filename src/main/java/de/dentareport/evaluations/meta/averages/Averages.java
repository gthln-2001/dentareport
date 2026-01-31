package de.dentareport.evaluations.meta.averages;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.utils.db.DbColumn;

import java.util.List;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class Averages {

    private final Evaluation evaluation;
    private final PerCase perCase;
    private final PerPatient perPatient;

    public Averages(Evaluation evaluation) {
        this.evaluation = evaluation;
        this.perCase = new PerCase(this);
        this.perPatient = new PerPatient(this);
    }

    public void evaluate() {
        rebuildTable();
        perCase.evaluate();
        perPatient.evaluate();
    }

    public List<DbColumn> columns() {
        List<String> columns = ImmutableList.of(
                "item",
                "name",
                "unit",
                "average",
                "minimum",
                "maximum",
                "median"
        );
        return columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
    }

    public String sourceTable() {
        return evaluation.dbTable();
    }

    public String targetTable() {
        return String.format("%s_averages", evaluation.dbTable());
    }

    private void rebuildTable() {
        db().rebuildTable(targetTable(), columns());
    }
}