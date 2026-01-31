package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.utils.Treatments;
import de.dentareport.utils.string.NumericStringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.TEST_TOOTH;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class CountFillingSurfacesTest {

    private static final String END_DATE = "end-date";
    // TODO: Check if "date_last_vist" is used correctly. Probalby date_end_search_period is better
    @Mocked
    AvailableColumns mockAvailableColumns;
    @Mocked
    RawData mockRawData;
    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    Treatments mockTreatments;
    @Mocked
    NumericStringUtils numericStringUtils;
    @Mocked
    EventInterface mockEventInterface;

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
    public void it_evaluates_data_of_event() {
        String columnName = "count_filling_surfaces__of__some_event";
        caseData.setEvent("some_event", mockEventInterface);
        String expectedResult = "4";

        new Expectations() {{
            mockEventInterface.countFillingSurfaces(TEST_TOOTH);
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition() {
        String columnName = "count_filling_surfaces__on__dentition";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until(END_DATE).countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from() {
        String columnName = "count_filling_surfaces__on__dentition__from__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after() {
        String columnName = "count_filling_surfaces__on__dentition__after__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_before() {
        String columnName = "count_filling_surfaces__on__dentition__before__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).before("foo").countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_until() {
        String columnName = "count_filling_surfaces__on__dentition__until__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until("foo").countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_before() {
        String columnName = "count_filling_surfaces__on__dentition__from__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").before("bar").countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_until() {
        String columnName = "count_filling_surfaces__on__dentition__from__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").until("bar").countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_before() {
        String columnName = "count_filling_surfaces__on__dentition__after__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").before("bar").countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_until() {
        String columnName = "count_filling_surfaces__on__dentition__after__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").until("bar").countFillingSurfacesOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth() {
        String columnName = "count_filling_surfaces";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until(END_DATE).countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_from() {
        String columnName = "count_filling_surfaces__from__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after() {
        String columnName = "count_filling_surfaces__after__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_before() {
        String columnName = "count_filling_surfaces__before__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).before("foo").countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_until() {
        String columnName = "count_filling_surfaces__until__date_of_foo";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until("foo").countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_from_before() {
        String columnName = "count_filling_surfaces__from__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").before("bar").countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_from_until() {
        String columnName = "count_filling_surfaces__from__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").until("bar").countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after_before() {
        String columnName = "count_filling_surfaces__after__date_of_foo__before__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").before("bar").countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after_until() {
        String columnName = "count_filling_surfaces__after__date_of_foo__until__date_of_bar";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").until("bar").countFillingSurfaces();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_per_timeframe() {
        String columnName = "count_filling_surfaces__on__dentition__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until(END_DATE).countFillingSurfacesOnDentition();
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
        String columnName = "count_filling_surfaces__on__dentition__from__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").countFillingSurfacesOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_per_timeframe() {
        String columnName = "count_filling_surfaces__on__dentition__after__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").countFillingSurfacesOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_before_per_timeframe() {
        String columnName = "count_filling_surfaces__on__dentition__before__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).before("foo").countFillingSurfacesOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_first_visit_before_foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_until_per_timeframe() {
        String columnName = "count_filling_surfaces__on__dentition__until__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until("foo").countFillingSurfacesOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_first_visit_until_foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_before_per_timeframe() {
        String columnName = "count_filling_surfaces__on__dentition__from__date_of_foo__before__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").before("bar").countFillingSurfacesOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_before_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_from_until_per_timeframe() {
        String columnName = "count_filling_surfaces__on__dentition__from__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").until("bar").countFillingSurfacesOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_before_per_timeframe() {
        String columnName = "count_filling_surfaces__on__dentition__after__date_of_foo__before__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").before("bar").countFillingSurfacesOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_before_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_dentition_after_until_per_timeframe() {
        String columnName = "count_filling_surfaces__on__dentition__after__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").until("bar").countFillingSurfacesOnDentition();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_per_timeframe() {
        String columnName = "count_filling_surfaces__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until(END_DATE).countFillingSurfaces();
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
    public void it_evaluates_data_on_tooth_from_per_timeframe() {
        String columnName = "count_filling_surfaces__from__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").countFillingSurfaces();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after_per_timeframe() {
        String columnName = "count_filling_surfaces__after__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").countFillingSurfaces();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_until_last_visit");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_before_per_timeframe() {
        String columnName = "count_filling_surfaces__before__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).before("foo").countFillingSurfaces();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_first_visit_before_foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_until_per_timeframe() {
        String columnName = "count_filling_surfaces__until__date_of_foo__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).until("foo").countFillingSurfaces();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_first_visit_until_foo");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_from_before_per_timeframe() {
        String columnName = "count_filling_surfaces__from__date_of_foo__before__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").before("bar").countFillingSurfaces();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_before_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_from_until_per_timeframe() {
        String columnName = "count_filling_surfaces__from__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).from("foo").until("bar").countFillingSurfaces();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_from_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after_before_per_timeframe() {
        String columnName = "count_filling_surfaces__after__date_of_foo__before__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").before("bar").countFillingSurfaces();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_before_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_on_tooth_after_until_per_timeframe() {
        String columnName = "count_filling_surfaces__after__date_of_foo__until__date_of_bar__per__some_timeframe";
        String expectedResult = "23";

        new Expectations() {{
            new Treatments(mockRawData, caseData).after("foo").until("bar").countFillingSurfaces();
            result = "some_count";
            NumericStringUtils.countPerTimeframe("some_count", "some_timeframe", "days_after_foo_until_bar");
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.string(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_gets_required_columns_for_event() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                OF, "some_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("some_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_from() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                FROM, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_after() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                AFTER, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_before() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                BEFORE, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_until() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                UNTIL, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_before() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                FROM, "date_biz_event",
                BEFORE, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_until() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                FROM, "date_biz_event",
                UNTIL, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_after_before() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                AFTER, "date_biz_event",
                BEFORE, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_after_until() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                AFTER, "date_biz_event",
                UNTIL, "date_baz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of());

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_from() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                FROM, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_after() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                AFTER, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_before() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                BEFORE, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_until() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                UNTIL, "date_biz_event")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_from_before() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__until__date_last_visit");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_per_timeframe() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                BEFORE, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__before__date_biz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_until_per_timeframe() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
                UNTIL, "date_biz_event",
                PER, "some_timeframe")
        );

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__until__date_biz_event");
    }

    @Test
    public void it_gets_required_columns_on_dentition_from_before_per_timeframe() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                ON, "foo",
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
                PER, "some_timeframe"));

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("count_days__from__date_first_visit__until__date_last_visit");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_from_per_timeframe() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(
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
    public void it_gets_required_billing_positions_on_tooth() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of());

        assertThat(column.requiredBillingpositions().size()).isEqualTo(1);
        assertThat(column.requiredBillingpositions()).contains(FUELLUNG);
    }

    @Test
    public void it_gets_required_billing_positions_on_dentition() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(ON, "something"));

        assertThat(column.requiredBillingpositions().size()).isEqualTo(1);
        assertThat(column.requiredBillingpositions()).contains(FUELLUNG);
    }

    @Test
    public void it_gets_required_billing_positions_of_event() {
        EvaluationColumn column = new CountFillingSurfaces(mockEvaluation, ImmutableMap.of(OF, "foo_event"));

        assertThat(column.requiredBillingpositions().size()).isEqualTo(0);
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
                                .put("count_days__from__date_first_visit__until__date_last_visit",
                                     "days_from_first_visit_until_last_visit")
                                .put("count_days__from__date_of_foo__until__date_last_visit",
                                     "days_from_foo_until_last_visit")
                                .put("count_days__after__date_of_foo__until__date_last_visit",
                                     "days_after_foo_until_last_visit")
                                .put("count_days__from__date_first_visit__before__date_of_foo",
                                     "days_from_first_visit_before_foo")
                                .put("count_days__from__date_first_visit__until__date_of_foo",
                                     "days_from_first_visit_until_foo")
                                .put("count_days__from__date_of_foo__before__date_of_bar",
                                     "days_from_foo_before_bar")
                                .put("count_days__from__date_of_foo__until__date_of_bar",
                                     "days_from_foo_until_bar")
                                .put("count_days__after__date_of_foo__before__date_of_bar",
                                     "days_after_foo_before_bar")
                                .put("count_days__after__date_of_foo__until__date_of_bar",
                                     "days_after_foo_until_bar")
                                .put(DATE_END_SEARCH_PERIOD, END_DATE)
                                .build()
        );
    }
}