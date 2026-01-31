package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.RawData;
import de.dentareport.utils.Treatments;
import de.dentareport.utils.string.NumericStringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class CountTreatmentsTest {

    // TODO: Check if "date_last_vist" is used correctly. Probalby date_end_search_period is better

    private static final String END_DATE = "end-date";
    @Mocked
    AvailableColumns mockAvailableColumns;
    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    RawData mockRawData;
    @Mocked
    Treatments mockTreatments;
    @Mocked
    NumericStringUtils numericStringUtils;
    private CaseData caseData;

    @BeforeEach
    public void setUp() {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            minTimes = 0;
            result = true;
        }};

        caseData = testCaseData();
    }

    @Test
    public void it_evaluates_data_on_dentition() {
        String columnName = "count_treatments__on__dentition__with__something";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until(END_DATE).withBillingposition("something").countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from() {
        String columnName = "count_treatments__on__dentition__with__something__from__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").withBillingposition("something").countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after() {
        String columnName = "count_treatments__on__dentition__with__something__after__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").withBillingposition("something").countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_before() {
        String columnName = "count_treatments__on__dentition__with__something__before__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).before("foo").withBillingposition("something").countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_until() {
        String columnName = "count_treatments__on__dentition__with__something__until__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until("foo").withBillingposition("something").countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_before() {
        String columnName = "count_treatments__on__dentition__with__something__from__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo")
                                                 .before("bar")
                                                 .withBillingposition("something")
                                                 .countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_until() {
        String columnName = "count_treatments__on__dentition__with__something__from__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo")
                                                 .until("bar")
                                                 .withBillingposition("something")
                                                 .countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_before() {
        String columnName = "count_treatments__on__dentition__with__something__after__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo")
                                                 .before("bar")
                                                 .withBillingposition("something")
                                                 .countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_until() {
        String columnName = "count_treatments__on__dentition__with__something__after__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo")
                                                 .until("bar")
                                                 .withBillingposition("something")
                                                 .countOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth() {
        String columnName = "count_treatments__with__something";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until(END_DATE).withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_from() {
        String columnName = "count_treatments__with__something__from__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after() {
        String columnName = "count_treatments__with__something__after__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_before() {
        String columnName = "count_treatments__with__something__before__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).before("foo").withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_until() {
        String columnName = "count_treatments__with__something__until__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until("foo").withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_from_before() {
        String columnName = "count_treatments__with__something__from__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").before("bar").withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_from_until() {
        String columnName = "count_treatments__with__something__from__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").until("bar").withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after_before() {
        String columnName = "count_treatments__with__something__after__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").before("bar").withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after_until() {
        String columnName = "count_treatments__with__something__after__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").until("bar").withBillingposition("something").count();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until(END_DATE).withBillingposition("something").countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count",
                                                 "some_timeframe",
                                                 "days_from_first_visit_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__from__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").withBillingposition("something").countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__after__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").withBillingposition("something").countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_before_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__before__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).before("foo").withBillingposition("something").countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_first_visit_before_foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_until_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__until__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until("foo").withBillingposition("something").countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_first_visit_until_foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_before_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__from__date_of_foo__before__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo")
                                                 .before("bar")
                                                 .withBillingposition("something")
                                                 .countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_before_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_until_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__from__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo")
                                                 .until("bar")
                                                 .withBillingposition("something")
                                                 .countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_before_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__after__date_of_foo__before__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo")
                                                 .before("bar")
                                                 .withBillingposition("something")
                                                 .countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_before_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_until_per_timeframe() {
        String columnName = "count_treatments__on__dentition__with__something__after__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo")
                                                 .until("bar")
                                                 .withBillingposition("something")
                                                 .countOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_per_timeframe() {
        String columnName = "count_treatments__with__something__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until(END_DATE).withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count",
                                                 "some_timeframe",
                                                 "days_from_first_visit_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_from_per_timeframe() {
        String columnName = "count_treatments__with__something__from__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_after_per_timeframe() {
        String columnName = "count_treatments__with__something__after__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_before_per_timeframe() {
        String columnName = "count_treatments__with__something__before__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).before("foo").withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_first_visit_before_foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_until_per_timeframe() {
        String columnName = "count_treatments__with__something__until__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until("foo").withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_first_visit_until_foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_from_before_per_timeframe() {
        String columnName = "count_treatments__with__something__from__date_of_foo__before__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").before("bar").withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_before_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_from_until_per_timeframe() {
        String columnName = "count_treatments__with__something__from__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").until("bar").withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_after_before_per_timeframe() {
        String columnName = "count_treatments__with__something__after__date_of_foo__before__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").before("bar").withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_before_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_after_until_per_timeframe() {
        String columnName = "count_treatments__with__something__after__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").until("bar").withBillingposition("something").count();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_gets_required_columns_on_dentition() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_from() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                FROM, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_after() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                AFTER, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_before() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                BEFORE, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_until() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                UNTIL, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_before() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                FROM, "date_biz_event",
                BEFORE, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_until() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                FROM, "date_biz_event",
                UNTIL, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_after_before() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                AFTER, "date_biz_event",
                BEFORE, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_after_until() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                AFTER, "date_biz_event",
                UNTIL, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_from() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                FROM, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_after() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                AFTER, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_before() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                BEFORE, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_until() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                UNTIL, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_from_before() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                FROM, "date_biz_event",
                BEFORE, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_from_until() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                FROM, "date_biz_event",
                UNTIL, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_before() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                AFTER, "date_biz_event",
                BEFORE, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_until() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                AFTER, "date_biz_event",
                UNTIL, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__until__date_last_visit");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                FROM, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_biz_event__until__date_last_visit");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_after_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                AFTER, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__after__date_biz_event__until__date_last_visit");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_before_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                BEFORE, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__before__date_biz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_until_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                UNTIL, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__until__date_biz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_before_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                FROM, "date_biz_event",
                BEFORE, "date_baz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_biz_event__before__date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_until_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
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
    public void it_gets_required_columns_on_dentition_after_before_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                AFTER, "date_biz_event",
                BEFORE, "date_baz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
        assertThat(column.requiredColumns()).contains("count_days__after__date_biz_event__before__date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_after_until_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                WITH, "bar",
                AFTER, "date_biz_event",
                UNTIL, "date_baz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
        assertThat(column.requiredColumns()).contains("count_days__after__date_biz_event__until__date_baz_event");
    }

    @Test
    public void it_gets_required_columns_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__until__date_last_visit");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_from_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                FROM, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(4);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_biz_event__until__date_last_visit");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_after_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                AFTER, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(4);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__after__date_biz_event__until__date_last_visit");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_before_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                BEFORE, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__before__date_biz_event");
    }

    @Test
    public void it_gets_required_columns_until_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                UNTIL, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__until__date_biz_event");
    }

    @Test
    public void it_gets_required_columns_from_before_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                FROM, "date_biz_event",
                BEFORE, "date_baz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(4);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_biz_event__before__date_baz_event");
    }

    @Test
    public void it_gets_required_columns_from_until_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                FROM, "date_biz_event",
                UNTIL, "date_baz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(4);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_biz_event__until__date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_before_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                AFTER, "date_biz_event",
                BEFORE, "date_baz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(4);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
        assertThat(column.requiredColumns()).contains("count_days__after__date_biz_event__before__date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_until_per_timeframe() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar",
                AFTER, "date_biz_event",
                UNTIL, "date_baz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(4);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
        assertThat(column.requiredColumns()).contains("count_days__after__date_biz_event__until__date_baz_event");
    }

    @Test
    public void it_gets_required_billing_positions() {
        EvaluationColumn column = new CountTreatments(mockEvaluation, ImmutableMap.of(
                WITH, "bar")
        );

        assertThat(column.requiredBillingpositions().size()).isEqualTo(1);
        assertThat(column.requiredBillingpositions()).contains("bar");
    }

    private CaseData getResult(String columnName) {
        return new Column(mockEvaluation, columnName)
                .instance()
                .evaluate(mockRawData, caseData);
    }

    private CaseData testCaseData() {
        return testCase(ImmutableMap.<String, String>builder()
                                .put("date_of_foo", "foo")
                                .put("date_of_bar", "bar")
                                .put("count_days__from__date_of_foo__before__date_of_bar", "days_from_foo_before_bar")
                                .put("count_days__from__date_of_foo__until__date_of_bar", "days_from_foo_until_bar")
                                .put("count_days__after__date_of_foo__before__date_of_bar", "days_after_foo_before_bar")
                                .put("count_days__after__date_of_foo__until__date_of_bar", "days_after_foo_until_bar")
                                .put("count_days__from__date_of_foo__until__date_last_visit",
                                     "days_from_foo_until_last_visit")
                                .put("count_days__after__date_of_foo__until__date_last_visit",
                                     "days_after_foo_until_last_visit")
                                .put("count_days__from__date_first_visit__before__date_of_foo",
                                     "days_from_first_visit_before_foo")
                                .put("count_days__from__date_first_visit__until__date_of_foo",
                                     "days_from_first_visit_until_foo")
                                .put("count_days__from__date_first_visit__until__date_last_visit",
                                     "days_from_first_visit_until_last_visit")
                                .put(DATE_END_SEARCH_PERIOD, END_DATE)
                                .build()
        );
    }
}