package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.RawData;
import de.dentareport.utils.Evidences01;
import de.dentareport.utils.string.NumericStringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CountEvidences01Test {

    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    RawData mockRawData;
    @Mocked
    Evidences01 mockEvidences;
    @Mocked
    AvailableColumns mockAvailableColumns;
    @Mocked
    NumericStringUtils numericStringUtils;

    @BeforeEach
    public void setUp() {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            minTimes = 0;
            result = true;
        }};
    }

    @Test
    public void it_evaluates_data() {
        String columnName = "count_evidences_01";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.count(mockRawData);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_from() {
        String columnName = "count_evidences_01__from__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countFrom(mockRawData, "foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_after() {
        String columnName = "count_evidences_01__after__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countAfter(mockRawData, "foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_before() {
        String columnName = "count_evidences_01__before__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countBefore(mockRawData, "foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_until() {
        String columnName = "count_evidences_01__until__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countUntil(mockRawData, "foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_from_before() {
        String columnName = "count_evidences_01__from__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countFromBefore(mockRawData, "foo", "bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_from_until() {
        String columnName = "count_evidences_01__from__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countFromUntil(mockRawData, "foo", "bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_from_until_per_timeframe() {
        String columnName = "count_evidences_01__from__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countFromUntil(mockRawData, "foo", "bar");
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_after_before() {
        String columnName = "count_evidences_01__after__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countAfterBefore(mockRawData, "foo", "bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_after_until() {
        String columnName = "count_evidences_01__after__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            Evidences01.countAfterUntil(mockRawData, "foo", "bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_gets_required_columns() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of());

        assertThat(column.requiredColumns().size()).isEqualTo(0);
    }

    @Test
    public void it_gets_required_columns_from() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                FROM, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_after() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                AFTER, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_before() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                BEFORE, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_until() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                UNTIL, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_from_before() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                FROM, "date_biz_event",
                BEFORE, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_from_until() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                FROM, "date_biz_event",
                UNTIL, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_from_until_per_timeframe() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                FROM, "date_biz_event",
                UNTIL, "date_baz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_biz_event__until__date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_before() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                AFTER, "date_biz_event",
                BEFORE, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_until() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of(
                AFTER, "date_biz_event",
                UNTIL, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_evidence_types() {
        EvaluationColumn column = new CountEvidences01(mockEvaluation, ImmutableMap.of());

        assertThat(column.requiredEvidenceTypes().size()).isEqualTo(1);
        assertThat(column.requiredEvidenceTypes()).contains(EVIDENCE_TYPE_01);
    }

    private CaseData getResult(String columnName) {
        return new Column(mockEvaluation, columnName)
                .instance()
                .evaluate(mockRawData, testCase(ImmutableMap.of(
                        "date_of_foo", "foo",
                        "date_of_bar", "bar",
                        "count_days__from__date_of_foo__until__date_of_bar", "days_from_foo_until_bar"
                )));
    }
}