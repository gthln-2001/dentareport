package de.dentareport.evaluations.meta.kaplan_meier;

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

public class KaplanMeier {

    private String sourceTable;
    private final List<Dependency> dependencies;
    private final List<Event> events;
    private final PerItemCombination perItemCombination;

    public KaplanMeier(Evaluation evaluation) {
        this.sourceTable = evaluation.dbTable();
        this.dependencies = new AvailableDependencies(evaluation.evaluationType()).dependencies();
        this.events = new AvailableEvents(evaluation.evaluationType()).events();
        this.perItemCombination = new PerItemCombination();
    }

    public void evaluate() {
        rebuildTable();
        events.forEach(this::evaluateEvent);
    }

    private void evaluateEvent(Event event) {
        dependencies.forEach(dependency -> evaluateCombination(event, dependency));
    }

    private void evaluateCombination(Event event,
                                     Dependency dependency) {
        perItemCombination.evaluate(sourceTable, event, dependency);
    }

    private void rebuildTable() {
        db().rebuildTable(targetTable(), columns());
    }

    private String targetTable() {
        return String.format("%s%s", sourceTable, Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER);
    }

    private List<DbColumn> columns() {
        List<String> columns = ImmutableList.of(
                "event",
                "dependency",
                "group_name",
                "group_order",
                "x",
                "y"
        );
        return columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
    }
}
