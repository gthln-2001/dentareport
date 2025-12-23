package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class EvaluationColumn {

    private final AvailableColumns availableColumns;
    protected Evaluation evaluation;
    protected Map<String, String> options;

    public EvaluationColumn(Evaluation evaluation,
                            Map<String, String> options) {
        this.evaluation = evaluation;
        this.options = options;
        this.availableColumns = new AvailableColumns();
    }

    public abstract CaseData evaluate(RawData rawData, CaseData caseData);

    public abstract String documentationShortDe();

    public String documentationLongDe() {
        return "";
    }

    public List<String> dependencies() {
        return requiredColumns().stream()
                                .map(this::validatedColumn)
                                .collect(Collectors.toList());
    }

    public Set<String> requiredBillingpositions() {
        return new HashSet<>();
    }

    public Set<String> requiredEvidenceTypes() {
        return new HashSet<>();
    }

    public boolean isTranslatable() {
        return false;
    }

    public boolean isDate() {
        return false;
    }

    protected abstract List<String> requiredColumns();

    private String validatedColumn(String column) {
        return isColumnAvailable(column)
               ? column
               : relatedColumn(column);
    }

    private boolean isColumnAvailable(String column) {
        return availableColumns.has(column);
    }

    private String relatedColumn(String column) {
        return availableColumns.relatedColumn(column);
    }
}
