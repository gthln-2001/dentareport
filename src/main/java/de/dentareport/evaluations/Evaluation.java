package de.dentareport.evaluations;

import de.dentareport.Documentation;
import de.dentareport.Export;
import de.dentareport.evaluations.meta.dependencies.AvailableDependencies;
import de.dentareport.evaluations.meta.dependencies.Dependency;
import de.dentareport.evaluations.meta.events.AvailableEvents;
import de.dentareport.groups.Groups;
import de.dentareport.gui.util.ProgressListener;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.repositories.EvaluationsRepository;
import de.dentareport.repositories.ValidCasesRepository;
import de.dentareport.utils.ColumnsService;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.xls.XlsColumn;
import de.dentareport.utils.xls.XlsColumnComparerByIndex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: Refactor! Clean up dependencies, this class is a mess...
public abstract class Evaluation {

    private static final Logger log = LogManager.getLogger(Evaluation.class);
    private final List<Column> columns;
    private final EvaluationsRepository evaluationsRepository;
    private final PatientEvaluation patientEvaluation;
    private final ValidCasesRepository validCasesRepository;
    private final Groups groups;
    private final Export export;
    private final Documentation documentation;
    private final AvailableEvents availableEvents;
    private final AvailableDependencies availableDependencies;
    private final Set<String> requiredBillingCodes;
    private final Set<String> requiredEvidenceTypes;

    public Evaluation() {
        this.availableEvents = new AvailableEvents(this.evaluationType());
        this.availableDependencies = new AvailableDependencies(this.evaluationType());
        ColumnsService columnsService = new ColumnsService(this);
        this.columns = columnsService.prepare(requiredColumns());
        this.documentation = new Documentation(this.columns);
        this.requiredBillingCodes = columnsService.requiredBillingCodes(this, columns);
        this.requiredEvidenceTypes = columnsService.requiredEvidenceTypes(this, columns);
        this.evaluationsRepository = new EvaluationsRepository();
        this.validCasesRepository = new ValidCasesRepository();
        this.patientEvaluation = new PatientEvaluation();
        this.groups = new Groups(this);
        this.export = new Export(this);
    }

    public abstract String evaluationName();

    public Set<String> requiredColumns() {
        Set<String> ret = new HashSet<>(evaluationColumns());
        ret.add("date_start_observation");
        ret.add("date_end_observation");
        ret.addAll(columnsFromAvailableEvents());
        ret.addAll(columnsFromAvailableDependencies());
        return ret.stream().filter(c -> !Objects.equals(c, "")).collect(Collectors.toSet());
    }

    public abstract EventInterface eventStartObservation(RawData rawData, CaseData caseData);

    public abstract String longDocumentationForStartObservation();

    public abstract Set<String> requiredBillingPositionsForEventStartObservation();

    public abstract Set<String> requiredEvidenceTypesForEventStartObservation();

    public abstract EventInterface eventEndObservation(RawData rawData, CaseData caseData);

    public abstract String longDocumentationForEndObservation();

    public abstract Set<String> requiredBillingPositionsForEventEndObservation();

    public abstract Set<String> requiredEvidenceTypesForEventEndObservation();

    public abstract List<String> dependenciesForEventEndObservation();

    public abstract Set<String> requiredBillingPositionsForEventsOfInterest(Map<String, String> options);

    public void evaluate(ProgressListener listener) {
        List<DbRow> dbRows = new ArrayList<>();
        Integer totalValidCases = countValidCases(evaluationType());
        int count = 0;
        for (Map.Entry<String, List<String>> entry : validCasesRepository.validCases(evaluationType()).entrySet()) {
            String patientIndex = entry.getKey();
            List<String> teeth = entry.getValue();
            dbRows.addAll(evaluatePatient(patientIndex, teeth, listener, totalValidCases, count));
            count += teeth.size();
        }

        listener.onProgress(100, Keys.GUI_TEXT_FINISHING_EVALUATION);
        writeDataToDb(dbRows);
        documentation.document();
        groups.group();
        log.info("Start export");
        export.export();
        log.info("finished export");
    }

    public List<Column> columns() {
        return columns;
    }

    public List<XlsColumn> xlsColumns() {
        List<XlsColumn> ret = new ArrayList<>();
        for (Column column : columns()) {
            ret.add(new XlsColumn(column, requiredColumns()));
        }
        ret.sort(new XlsColumnComparerByIndex());
        return ret;
    }

    public List<XlsColumn> xlsColumnsInEvaluation() {
        return xlsColumns().stream()
                .filter(XlsColumn::isInEvaluation)
                .collect(Collectors.toList());
    }

    public String evaluationType() {
        return this.getClass().getSimpleName().replace("Evaluation", "");
    }

    public Set<String> requiredBillingCodes() {
        return requiredBillingCodes;
    }

    public Set<String> requiredEvidenceTypes() {
        return requiredEvidenceTypes;
    }

    public String dbTable() {
        return "evaluation_" + evaluationType();
    }

    public String shortDocumentation(String columnName) {
        return documentation.shortDoc(columnName);
    }

    public String longDocumentation(String columnName) {
        return documentation.longDoc(columnName);
    }

    public Set<String> occurringBillingpositions() {
        return documentation.occuringBillingpositions();
    }

    protected abstract Set<String> evaluationColumns();

    private Set<String> columnsFromAvailableDependencies() {
        Set<String> ret = groupColumnsFromAvailableDependencies();
        ret.addAll(orderColumnsFromAvailableDependencies());
        return ret;
    }

    private Set<String> groupColumnsFromAvailableDependencies() {
        return availableDependencies.dependencies().stream()
                .map(Dependency::groupColumn)
                .collect(Collectors.toSet());
    }

    private Set<String> orderColumnsFromAvailableDependencies() {
        return availableDependencies.dependencies().stream()
                .map(Dependency::orderColumn)
                .collect(Collectors.toSet());
    }

    private Set<String> columnsFromAvailableEvents() {
        Set<String> ret = new HashSet<>();
        availableEvents.events().forEach(e -> {
            ret.add(e.censoredColumn());
            ret.add(e.intervalColumn());
        });
        return ret;
    }

    private Integer countValidCases(String evaluationId) {
        int ret = 0;
        for (List<String> teeth : validCasesRepository.validCases(evaluationId).values()) {
            ret += teeth.size();
        }
        return ret;
    }

    private List<DbRow> evaluatePatient(String patientIndex, List<String> teeth, ProgressListener listener,
                                        Integer totalValidCases, int count) {
        return patientEvaluation.evaluate(this, RawData.instance(this, patientIndex, teeth), teeth, listener,
                totalValidCases, count);
    }

    private void writeDataToDb(List<DbRow> dbRows) {
        rebuildTable();
        db().writeRows(dbTable(), dbRows);
    }

    private void rebuildTable() {
        evaluationsRepository.rebuildTable(dbTable(), requiredColumns());
    }
}