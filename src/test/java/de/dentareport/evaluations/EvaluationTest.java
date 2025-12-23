package de.dentareport.evaluations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.Documentation;
import de.dentareport.Export;
import de.dentareport.evaluations.meta.dependencies.AvailableDependencies;
import de.dentareport.evaluations.meta.events.AvailableEvents;
import de.dentareport.groups.Groups;
//import de.dentareport.gui.ProgressUpdate;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.repositories.EvaluationsRepository;
import de.dentareport.repositories.ValidCasesRepository;
import de.dentareport.utils.ColumnsService;
import de.dentareport.utils.db.Db;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbConnection;
import de.dentareport.utils.db.DbRow;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class EvaluationTest {

    @Mocked
    ColumnsService mockColumnsService;
    @Mocked
    DbConnection mockDbConnection;
    @Mocked
    Documentation mockDocumentation;
    @Mocked
    EvaluationsRepository mockEvaluationsRepository;
    @Mocked
    Export mockExport;
    @Mocked
    Groups mockGroups;
    @Mocked
    PatientEvaluation mockPatientEvaluation;
//    @Mocked
//    ProgressUpdate mockProgressUpdate;
    @Mocked
    ValidCasesRepository mockValidCasesRepository;
    @Mocked
    AvailableDependencies mockAvailableDependencies;
    @Mocked
    AvailableEvents mockAvailableEvents;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_prepares_column_list() {
        List<Column> columns = ImmutableList.of();
        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = columns;
        }};

        Evaluation evaluation = new Evaluation1234();

        new Verifications() {{
            mockColumnsService.prepare(evaluation.requiredColumns());
        }};

        assertThat(evaluation.columns()).isEqualTo(columns);
    }

    @Test
    public void it_adds_default_columns_to_list_of_required_columns() {
        Evaluation evaluation = new Evaluation1234();

        // Columns defined in Evaluation1234
        assertThat(evaluation.requiredColumns()).contains("foo");
        assertThat(evaluation.requiredColumns()).contains("bar");
        // Default columns
        assertThat(evaluation.requiredColumns()).contains("date_start_observation");
        assertThat(evaluation.requiredColumns()).contains("date_end_observation");
    }

    // TODO: FIx test
//    @Test
//    public void it_adds_columns_from_available_events_and_dependencies_to_list_of_required_column() {
//        new Expectations() {{
//            new AvailableDependencies("1234");
//            mockAvailableDependencies.dependencies();
//            result = ImmutableList.of(
//                    new Dependency("a", "", ""),
//                    new Dependency("b", "x", "p"),
//                    new Dependency("c", "x", "p"),
//                    new Dependency("d", "y", "q")
//            );
//
//            new AvailableEvents("1234");
//            mockAvailableEvents.events();
//            result = ImmutableList.of(
//                    new Event("e", "", ""),
//                    new Event("f", "x", "y"),
//                    new Event("g", "z", "z")
//            );
//        }};
//
//        Evaluation evaluation = new Evaluation1234();
//
//        Set<String> requiredColumns = evaluation.requiredColumns();
//
//        assertThat(requiredColumns.size()).isEqualTo(9); // 2 evaluation columns + 5 dependencies + 2 default columns
//        assertThat(requiredColumns).contains("foo");
//        assertThat(requiredColumns).contains("bar");
//        assertThat(requiredColumns).contains("p");
//        assertThat(requiredColumns).contains("q");
//        assertThat(requiredColumns).contains("x");
//        assertThat(requiredColumns).contains("y");
//        assertThat(requiredColumns).contains("z");
//        assertThat(requiredColumns).contains("date_start_observation"); // default column, must be present in all evaluations
//        assertThat(requiredColumns).contains("date_end_observation"); // default column, must be present in all evaluations
//    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_prepares_documentation() {
        List<Column> columns = ImmutableList.of();
        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = columns;
            new Documentation(columns);
        }};

        Evaluation evaluation = new Evaluation1234();
        evaluation.evaluate();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_evaluates_patients_and_calls_grouper(@Mocked RawData mockRawData1,
                                                        @Mocked RawData mockRawData2) {
        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = ImmutableList.of();
            mockValidCasesRepository.validCases("1234");
            result = testValidCases();
        }};

        Evaluation evaluation = new Evaluation1234();

        new Verifications() {{
            new Groups(evaluation);
            times = 1;
            new Export(evaluation);
            times = 1;
        }};

        new Expectations() {{
            RawData.instance(evaluation, "23", new ArrayList<>(Arrays.asList("11", "22")));
            result = mockRawData1;
            RawData.instance(evaluation, "42", new ArrayList<>(Arrays.asList("33", "44")));
            result = mockRawData2;
            mockPatientEvaluation.evaluate(evaluation, mockRawData1, testValidCases().get("23"));
            mockPatientEvaluation.evaluate(evaluation, mockRawData2, testValidCases().get("42"));
        }};

        evaluation.evaluate();

        new Verifications() {{
            mockGroups.group();
            times = 1;
            mockExport.export();
            times = 1;
        }};
    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_rebuilds_table() {
        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = ImmutableList.of();
        }};
        Evaluation evaluation = new Evaluation1234();

        evaluation.evaluate();

        new Verifications() {{
            mockEvaluationsRepository.rebuildTable("evaluation_1234", evaluation.requiredColumns());
        }};
    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_writes_evaluation_to_db(@Mocked Db mockDb,
                                           @Mocked RawData mockRawData1,
                                           @Mocked RawData mockRawData2
    ) {
        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = ImmutableList.of();
            mockValidCasesRepository.validCases("1234");
            result = testValidCases();
        }};

        Evaluation evaluation = new Evaluation1234();

        new Expectations() {{
            RawData.instance(evaluation, "23", new ArrayList<>(Arrays.asList("11", "22")));
            result = mockRawData1;
            mockPatientEvaluation.evaluate(evaluation, mockRawData1, testValidCases().get("23"));
            result = resultsPatient23();
            RawData.instance(evaluation, "42", new ArrayList<>(Arrays.asList("33", "44")));
            result = mockRawData2;
            mockPatientEvaluation.evaluate(evaluation, mockRawData2, testValidCases().get("42"));
            result = resultsPatient42();
        }};

        evaluation.evaluate();

        new Verifications() {{
            List<DbRow> expectedRows;
            mockDb.writeRows("evaluation_1234", expectedRows = withCapture());
            assertThat(expectedRows.size()).isEqualTo(2);
        }};
    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_gets_evaluator_type() {
        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = ImmutableList.of();
        }};
        Evaluation evaluation = new Evaluation1234();

        assertThat(evaluation.evaluationType()).isEqualTo("1234");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_gets_name_of_db_table() {
        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = ImmutableList.of();
        }};
        Evaluation evaluation = new Evaluation1234();

        assertThat(evaluation.dbTable()).isEqualTo("evaluation_1234");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_gets_required_billing_codes() {
        List<Column> columns = ImmutableList.of();
        Set<String> billingCodes = ImmutableSet.of("bar", "biz");

        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = columns;
            mockColumnsService.requiredBillingCodes((Evaluation) any, columns);
            result = billingCodes;
        }};

        Evaluation evaluation = new Evaluation1234();

        assertThat(evaluation.requiredBillingCodes()).isEqualTo(billingCodes);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void it_gets_required_evidence_types() {
        List<Column> columns = ImmutableList.of();
        Set<String> evidenceTypes = ImmutableSet.of("bar", "biz", "baz");

        new Expectations() {{
            mockColumnsService.prepare((Set<String>) any);
            result = columns;
            mockColumnsService.requiredEvidenceTypes((Evaluation) any, columns);
            result = evidenceTypes;
        }};

        Evaluation evaluation = new Evaluation1234();

        assertThat(evaluation.requiredEvidenceTypes()).isEqualTo(evidenceTypes);
    }


    // TODO: Fix test
//    @Test
//    @SuppressWarnings("unchecked")
//    public void it_gets_list_of_all_xls_columns_sorted_for_xls_export(@Mocked Column mockColumn1,
//                                                                      @Mocked Column mockColumn2,
//                                                                      @Mocked XlsColumn xlsColumn1,
//                                                                      @Mocked XlsColumn xlsColumn2) {
//
//        new Expectations() {{
//            mockColumnsService.prepare((Set<String>) any);
//            result = ImmutableList.of(mockColumn1, mockColumn2);
//
//            new XlsColumn(mockColumn1, (Set<String>) any);
//            times = 1;
//            result = xlsColumn1;
//            xlsColumn1.index();
//            result = "2.3";
//
//            new XlsColumn(mockColumn2, (Set<String>) any);
//            times = 1;
//            result = xlsColumn2;
//            xlsColumn2.index();
//            result = "1.5";
//        }};
//        Evaluation evaluation = new Evaluation1234();
//
//        List<XlsColumn> result = evaluation.xlsColumns();
//
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.get(0).index()).isEqualTo("1.5");
//        assertThat(result.get(1).index()).isEqualTo("2.3");
//    }

    // TODO: Fix test
//    @Test
//    @SuppressWarnings("unchecked")
//    public void it_gets_list_of_xls_columns_in_evaluation_sorted_for_xls_export(@Mocked Column mockColumn1,
//                                                                                @Mocked Column mockColumn2,
//                                                                                @Mocked Column mockColumn3,
//                                                                                @Mocked XlsColumn xlsColumn1,
//                                                                                @Mocked XlsColumn xlsColumn2,
//                                                                                @Mocked XlsColumn xlsColumn3) {
//
//        new Expectations() {{
//            mockColumnsService.prepare((Set<String>) any);
//            result = ImmutableList.of(mockColumn1, mockColumn2, mockColumn3);
//
//            new XlsColumn(mockColumn1, (Set<String>) any);
//            times = 1;
//            result = xlsColumn1;
//            xlsColumn1.index();
//            result = "2.3";
//            xlsColumn1.isInEvaluation();
//            result = true;
//
//            new XlsColumn(mockColumn2, (Set<String>) any);
//            times = 1;
//            result = xlsColumn2;
//            xlsColumn2.index();
//            result = "3.5";
//            xlsColumn2.isInEvaluation();
//            result = false;
//
//            new XlsColumn(mockColumn3, (Set<String>) any);
//            times = 1;
//            result = xlsColumn3;
//            xlsColumn3.index();
//            result = "1.5";
//            xlsColumn3.isInEvaluation();
//            result = true;
//        }};
//        Evaluation evaluation = new Evaluation1234();
//
//        List<XlsColumn> result = evaluation.xlsColumnsInEvaluation();
//
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.get(0).index()).isEqualTo("1.5");
//        assertThat(result.get(1).index()).isEqualTo("2.3");
//    }

    @Test
    public void it_gets_short_documentation_for_column() {
        new Expectations() {{
            mockDocumentation.shortDoc("some_column");
            result = "Short Documentation";
        }};

        Evaluation evaluation = new Evaluation1234();

        assertThat(evaluation.shortDocumentation("some_column")).isEqualTo("Short Documentation");
    }

    @Test
    public void it_gets_long_documentation_for_column() {
        new Expectations() {{
            mockDocumentation.longDoc("some_column");
            result = "Long Documentation";
        }};

        Evaluation evaluation = new Evaluation1234();

        assertThat(evaluation.longDocumentation("some_column")).isEqualTo("Long Documentation");
    }

    @Test
    public void it_gets_billingpositions_needed_for_glossary() {
        Set<String> billingpositions = ImmutableSet.of("a", "b", "c");
        new Expectations() {{
            mockDocumentation.occuringBillingpositions();
            result = billingpositions;
        }};

        Evaluation evaluation = new Evaluation1234();

        assertThat(evaluation.occuringBillingpositions()).isEqualTo(billingpositions);
    }

    private List<DbRow> resultsPatient23() {
        DbRow row = new DbRow();
        row.addCell(new DbCell("foo", "bar"));
        return new ArrayList<>(Collections.singletonList(row));
    }

    private List<DbRow> resultsPatient42() {
        DbRow row = new DbRow();
        row.addCell(new DbCell("biz", "baz"));
        return new ArrayList<>(Collections.singletonList(row));
    }

    private Map<String, List<String>> testValidCases() {
        Map<String, List<String>> ret = new HashMap<>();
        ret.put("23", new ArrayList<>(Arrays.asList("11", "22")));
        ret.put("42", new ArrayList<>(Arrays.asList("33", "44")));
        return ret;
    }

    private static class Evaluation1234 extends Evaluation {

        public Evaluation1234() {
            super();
        }

        @Override
        public String evaluationName() {
            return null;
        }

        @Override
        public EventInterface eventStartObservation(RawData rawData, CaseData caseData) {
            return null;
        }

        @Override
        public String longDocumentationForStartObservation() {
            return null;
        }

        @Override
        public Set<String> requiredBillingPositionsForEventStartObservation() {
            return null;
        }

        @Override
        public Set<String> requiredEvidenceTypesForEventStartObservation() {
            return null;
        }

        @Override
        public EventInterface eventEndObservation(RawData rawData, CaseData caseData) {
            return null;
        }

        @Override
        public String longDocumentationForEndObservation() {
            return null;
        }

        @Override
        public Set<String> requiredBillingPositionsForEventEndObservation() {
            return null;
        }

        @Override
        public Set<String> requiredEvidenceTypesForEventEndObservation() {
            return null;
        }

        @Override
        public List<String> dependenciesForEventEndObservation() {
            return null;
        }

        @Override
        public Set<String> requiredBillingPositionsForEventsOfInterest(Map<String, String> options) {
            return null;
        }

        @Override
        protected Set<String> evaluationColumns() {
            return new HashSet<>(Arrays.asList("foo", "bar"));
        }
    }
}