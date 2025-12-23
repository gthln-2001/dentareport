package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.RawData;
import de.dentareport.utils.string.DateStringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CountDaysTest {

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
    public void it_evaluates_data_from_until(@Mocked Evaluation mockEvaluation,
                                             @Mocked RawData mockRawData,
                                             @Mocked DateStringUtils mockDateStringUtils) {
        CaseData caseData = testCase();
        caseData.setString("date_of_foo", "foo");
        caseData.setString("date_of_bar", "bar");
        new Expectations() {{
            DateStringUtils.daysBetween("foo", "bar");
            result = "biz";
        }};

        EvaluationColumn column = new Column(mockEvaluation,
                                             "count_days__from__date_of_foo__until__date_of_bar").instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("count_days__from__date_of_foo__until__date_of_bar")).isEqualTo("biz");
    }

    @Test
    public void it_applies_given_format(@Mocked Evaluation mockEvaluation,
                                             @Mocked RawData mockRawData,
                                             @Mocked DateStringUtils mockDateStringUtils) {
        CaseData caseData = testCase();
        caseData.setString("date_of_foo", "foo");
        caseData.setString("date_of_bar", "bar");
        new Expectations() {{
            DateStringUtils.daysBetween("foo", "bar");
            result = "0";
        }};

        // without format
        EvaluationColumn column = new Column(mockEvaluation, "count_days__from__date_of_foo__until__date_of_bar").instance();
        CaseData result = column.evaluate(mockRawData, caseData);
        assertThat(result.string("count_days__from__date_of_foo__until__date_of_bar")).isEqualTo("0");

        // with format
        column = new Column(mockEvaluation, "count_days__from__date_of_foo__until__date_of_bar__format__epi").instance();
        result = column.evaluate(mockRawData, caseData);
        assertThat(result.string("count_days__from__date_of_foo__until__date_of_bar__format__epi")).isEqualTo("0.01");
    }

    @Test
    public void it_evaluates_data_from_before(@Mocked Evaluation mockEvaluation,
                                             @Mocked RawData mockRawData,
                                             @Mocked DateStringUtils mockDateStringUtils) {
        CaseData caseData = testCase();
        caseData.setString("date_of_foo", "foo");
        caseData.setString("date_of_bar", "bar");
        new Expectations() {{
            DateStringUtils.daysBetweenWithOffset("foo", "bar", -1);
            result = "biz";
        }};

        EvaluationColumn column = new Column(mockEvaluation,
                                             "count_days__from__date_of_foo__before__date_of_bar").instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("count_days__from__date_of_foo__before__date_of_bar")).isEqualTo("biz");
    }

    @Test
    public void it_evaluates_data_from_until_or_until(@Mocked Evaluation mockEvaluation,
                                                      @Mocked RawData mockRawData,
                                                      @Mocked DateStringUtils mockDateStringUtils) {
        CaseData caseData = testCase();
        caseData.setString("date_of_foo", "foo");
        caseData.setString("date_of_bar", "bar");
        caseData.setString("date_of_biz", "biz");
        new Expectations() {{
            DateStringUtils.daysBetween("foo", "bar", "biz");
            result = "baz";
        }};

        EvaluationColumn column = new Column(mockEvaluation,
                                             "count_days__from__date_of_foo__until__date_of_bar__or_until__date_of_biz")
                .instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("count_days__from__date_of_foo__until__date_of_bar__or_until__date_of_biz")).isEqualTo(
                "baz");
    }

    @Test
    public void it_gets_required_columns_from_until(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new CountDays(mockEvaluation,
                                                ImmutableMap.of(FROM, "date_of_something",
                                                                UNTIL, "date_of_something_else"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_of_something");
        assertThat(column.requiredColumns()).contains("date_of_something_else");
    }

    @Test
    public void it_gets_required_columns_from_before(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new CountDays(mockEvaluation,
                                                ImmutableMap.of(FROM, "date_of_something",
                                                                BEFORE, "date_of_something_else"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_of_something");
        assertThat(column.requiredColumns()).contains("date_of_something_else");
    }

    @Test
    public void it_gets_required_columns_from_until_or_until(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new CountDays(mockEvaluation,
                                                ImmutableMap.of(FROM, "date_of_something",
                                                                UNTIL, "date_of_something_else",
                                                                OR_UNTIL, "date_of_something_different"));

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("date_of_something");
        assertThat(column.requiredColumns()).contains("date_of_something_else");
        assertThat(column.requiredColumns()).contains("date_of_something_different");
    }
}