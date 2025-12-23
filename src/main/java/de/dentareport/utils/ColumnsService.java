package de.dentareport.utils;

import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.Column;

import java.util.*;
import java.util.stream.Collectors;

public class ColumnsService {

    private final Evaluation evaluation;
    private final AvailableColumns availableColumns;
    private List<String> columns;

    public ColumnsService(Evaluation evaluation) {
        this.evaluation = evaluation;
        this.availableColumns = new AvailableColumns();
    }

    public List<Column> prepare(Set<String> columns) {
        this.columns = new ArrayList<>(columns);
        columns.forEach(this::addDependenciesToColumnList);
        return sortedList(this.columns);
    }

    public Set<String> requiredBillingCodes(Evaluation evaluation,
                                            List<Column> columns) {
        return Billingcodes.forPosition(allRequiredBillingPositions(evaluation, columns));
    }

    public Set<String> requiredEvidenceTypes(Evaluation evaluation, List<Column> columns) {
        Set<String> evidenceTypes = new HashSet<>();
        for (Column column : columns) {
            evidenceTypes.addAll(column.instance().requiredEvidenceTypes());
        }
        return evidenceTypes;
    }

    private Set<String> allRequiredBillingPositions(Evaluation evaluation, List<Column> columns) {
        Set<String> billingPositions = new HashSet<>();
        for (Column column : columns) {
            billingPositions.addAll(column.instance().requiredBillingpositions());
        }
        return billingPositions;
    }

    private List<String> addDependenciesToColumnList(String column) {
        if (!availableColumns.has(column)) {
            Log.error("Column is not in list of available columns: " + column);
        }
        dependencies(column).forEach(this::addDependencyToColumnList);
        return columns;
    }

    private List<String> dependencies(String column) {
        return new Column(evaluation, column).dependencies();
    }

    private void addDependencyToColumnList(String dependency) {
        if (dependencyAlreadyInColumnList(dependency)) {
            return;
        }
        columns.add(dependency);
        columns = addDependenciesToColumnList(dependency);
    }

    private boolean dependencyAlreadyInColumnList(String dependency) {
        return columns.contains(dependency);
    }

    private List<Column> sortedList(List<String> columnList) {
        return columnList.stream()
                         .map(column -> new Column(evaluation, column))
                         .sorted(Comparator.comparingInt(Column::hierarchyLevel))
                         .collect(Collectors.toList());
    }
}
