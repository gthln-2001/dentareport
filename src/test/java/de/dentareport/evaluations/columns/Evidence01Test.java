package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.*;
import de.dentareport.utils.Evidences01;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Evidence01Test {

    private static final String FOO_DATE = "foo-date";
    private static final String BAR_DATE = "bar-date";
    private static final String END_DATE = "end-date";
    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    RawData mockRawData;
    @Mocked
    AvailableColumns mockAvailableColumns;

    @BeforeEach
    public void setUp() {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            minTimes = 0;
            result = true;
        }};
    }

    @Test
    public void it_evaluates_data_for_first_01(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstUntil(mockRawData, END_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_01_from(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first__from__date_of_foo_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstFromUntil(mockRawData, FOO_DATE, END_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_01_after(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first__after__date_of_foo_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstAfterUntil(mockRawData, FOO_DATE, END_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_01_before(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first__before__date_of_foo_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstBefore(mockRawData, FOO_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_01_until(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first__until__date_of_foo_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstUntil(mockRawData, FOO_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_01_from_before(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first__from__date_of_foo_event__before__date_of_bar_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstFromBefore(mockRawData, FOO_DATE, BAR_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_01_from_until(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first__from__date_of_foo_event__until__date_of_bar_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstFromUntil(mockRawData, FOO_DATE, BAR_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_01_after_before(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first__after__date_of_foo_event__before__date_of_bar_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstAfterBefore(mockRawData, FOO_DATE, BAR_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_01_after_until(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__first__after__date_of_foo_event__until__date_of_bar_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.firstAfterUntil(mockRawData, FOO_DATE, BAR_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastUntil(mockRawData, END_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01_from(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last__from__date_of_foo_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastFromUntil(mockRawData, FOO_DATE, END_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01_after(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last__after__date_of_foo_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastAfterUntil(mockRawData, FOO_DATE, END_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01_before(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last__before__date_of_foo_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastBefore(mockRawData, FOO_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01_until(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last__until__date_of_foo_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastUntil(mockRawData, FOO_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01_from_before(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last__from__date_of_foo_event__before__date_of_bar_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastFromBefore(mockRawData, FOO_DATE, BAR_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01_from_until(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last__from__date_of_foo_event__until__date_of_bar_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastFromUntil(mockRawData, FOO_DATE, BAR_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01_after_before(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last__after__date_of_foo_event__before__date_of_bar_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastAfterBefore(mockRawData, FOO_DATE, BAR_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_01_after_until(@Mocked Evidences01 mockEvidences01) {
        String columnName = "evidence_01__position__last__after__date_of_foo_event__until__date_of_bar_event";
        Evidence01Interface expectedResult = new NullEvidence01();

        new Expectations() {{
            Evidences01.lastAfterUntil(mockRawData, FOO_DATE, BAR_DATE);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_gets_required_columns() {
        EvaluationColumn column = new Evidence01(mockEvaluation, ImmutableMap.of());

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_from() {
        EvaluationColumn column = new Evidence01(mockEvaluation, ImmutableMap.of(FROM, "date_biz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_after() {
        EvaluationColumn column = new Evidence01(mockEvaluation, ImmutableMap.of(AFTER, "date_biz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_before() {
        EvaluationColumn column = new Evidence01(mockEvaluation, ImmutableMap.of(BEFORE, "date_biz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_until() {
        EvaluationColumn column = new Evidence01(mockEvaluation, ImmutableMap.of(UNTIL, "date_biz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_from_before() {
        EvaluationColumn column = new Evidence01(mockEvaluation,
                                                 ImmutableMap.of(FROM, "date_biz_event", BEFORE, "date_baz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_from_until() {
        EvaluationColumn column = new Evidence01(mockEvaluation,
                                                 ImmutableMap.of(FROM, "date_biz_event", UNTIL, "date_baz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_before() {
        EvaluationColumn column = new Evidence01(mockEvaluation,
                                                 ImmutableMap.of(AFTER, "date_biz_event", BEFORE, "date_baz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_until() {
        EvaluationColumn column = new Evidence01(mockEvaluation,
                                                 ImmutableMap.of(AFTER, "date_biz_event", UNTIL, "date_baz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_short_doc_for_first_01() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erster 01");
    }

    @Test
    public void it_gets_short_doc_for_first_01_from() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first__from__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erster 01 ab ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_01_after() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first__after__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erster 01 nach ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_01_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first__before__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erster 01 vor ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_01_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first__until__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erster 01 bis ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_01_from_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first__from__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erster 01 ab ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_01_from_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first__from__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erster 01 ab ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_01_after_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first__after__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erster 01 nach ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_01_after_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__first__after__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erster 01 nach ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_01() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzter 01");
    }

    @Test
    public void it_gets_short_doc_for_last_01_from() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last__from__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzter 01 ab ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_01_after() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last__after__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzter 01 nach ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_01_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last__before__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzter 01 vor ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_01_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last__until__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzter 01 bis ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_01_from_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last__from__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzter 01 ab ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_01_from_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last__from__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzter 01 ab ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_01_after_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last__after__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzter 01 nach ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_01_after_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "evidence_01__position__last__after__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzter 01 nach ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_required_evidence_types() {
        EvaluationColumn column = new Evidence01(mockEvaluation, ImmutableMap.of());

        assertThat(column.requiredEvidenceTypes().size()).isEqualTo(1);
        assertThat(column.requiredEvidenceTypes()).contains(EVIDENCE_TYPE_01);
    }

    private CaseData getResult(String columnName) {
        return new Column(mockEvaluation, columnName)
                .instance().evaluate(mockRawData, testCase(ImmutableMap.of(
                        "date_of_foo_event", FOO_DATE,
                        "date_of_bar_event", BAR_DATE,
                        DATE_END_SEARCH_PERIOD, END_DATE
                )));
    }
}