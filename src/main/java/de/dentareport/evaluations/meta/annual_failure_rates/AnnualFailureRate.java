package de.dentareport.evaluations.meta.annual_failure_rates;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.meta.dependencies.AvailableDependencies;
import de.dentareport.evaluations.meta.dependencies.Dependency;
import de.dentareport.evaluations.meta.events.AvailableEvents;
import de.dentareport.evaluations.meta.events.Event;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbColumn;

import java.util.List;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class AnnualFailureRate {

    private final String sourceTable;
    private final AvailableDependencies availableDependencies;
    private final AvailableEvents availableEvents;
    private final PerItemCombination perItemCombination;

    public AnnualFailureRate(Evaluation evaluation) {
        this.sourceTable = evaluation.dbTable();
        this.availableDependencies = new AvailableDependencies(evaluation.evaluationType());
        this.availableEvents = new AvailableEvents(evaluation.evaluationType());
        this.perItemCombination = new PerItemCombination();
    }

    public void evaluate() {
        rebuildTable();
        availableEvents.events()
                .forEach(this::evaluateEvent);
    }

    private void evaluateEvent(Event event) {
        availableDependencies.dependencies()
                .forEach(dependency -> evaluateCombination(event, dependency));
    }

    private void evaluateCombination(Event event,
                                     Dependency dependency) {
        perItemCombination.evaluate(sourceTable, event, dependency);
    }

    private List<DbColumn> columns() {
        List<String> columns = ImmutableList.of(
                "event",
                "dependency",
                "group_name",
                "group_order",
                "interval",
                "afr");

        return columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
    }

    private void rebuildTable() {
        db().rebuildTable(targetTable(), columns());
    }

    private String targetTable() {
        return String.format(
                "%s%s",
                sourceTable,
                Keys.DB_TABLE_SUFFIX_AFR);
    }
}
