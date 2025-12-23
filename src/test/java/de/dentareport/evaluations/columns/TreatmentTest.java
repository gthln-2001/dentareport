package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.*;
import de.dentareport.utils.Keys;
import de.dentareport.utils.TreatmentFilters;
import de.dentareport.utils.Treatments;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TreatmentTest {

    // TODO: Check if "date_last_vist" is used correctly. Probalby date_end_search_period is better

    private static final String FOO_DATE = "foo-date";
    private static final String BAR_DATE = "bar-date";
    private static final String END_DATE = "end-date";
    private static final String SOME_BILLINGPOSITION = "some-billingposition";

    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    RawData mockRawData;
    @Mocked
    AvailableColumns mockAvailableColumns;
    private CaseData testCase;

    @BeforeEach
    public void setUp() {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            minTimes = 0;
            result = true;
        }};
        testCase = testCaseData();
    }

    @Test
    public void it_evaluates_data_for_first_treatment(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(END_DATE).withBillingposition(SOME_BILLINGPOSITION).first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_from(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__from__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_after(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__after__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__before__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).before(FOO_DATE).withBillingposition(SOME_BILLINGPOSITION).first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__until__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(FOO_DATE).withBillingposition(SOME_BILLINGPOSITION).first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_from_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__from__date_of_foo_event__before__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .before(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_from_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__from__date_of_foo_event__until__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .until(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_after_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__after__date_of_foo_event__before__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .before(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_after_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__after__date_of_foo_event__until__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .until(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(END_DATE).withBillingposition(SOME_BILLINGPOSITION).last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_from(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition__from__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_after(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition__after__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition__before__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).before(FOO_DATE).withBillingposition(SOME_BILLINGPOSITION).last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition__until__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(FOO_DATE).withBillingposition(SOME_BILLINGPOSITION).last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_from_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition__from__date_of_foo_event__before__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .before(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_from_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition__from__date_of_foo_event__until__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .until(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_after_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition__after__date_of_foo_event__before__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .before(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_after_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__last__with__some-billingposition__after__date_of_foo_event__until__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .until(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .last();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition_from(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition__from__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition_after(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition__after__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase)
                    .after(FOO_DATE)
                    .until(END_DATE)
                    .withBillingposition(SOME_BILLINGPOSITION)
                    .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition__before__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).before(FOO_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition__until__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(FOO_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition_from_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition__from__date_of_foo_event__before__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .before(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition_from_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition__from__date_of_foo_event__until__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .until(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition_after_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition__after__date_of_foo_event__before__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .before(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_first_treatment_on_dentition_after_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__first__with__some-billingposition__after__date_of_foo_event__until__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .until(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .firstOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition_from(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition__from__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition_after(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition__after__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition__before__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).before(FOO_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition__until__date_of_foo_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(FOO_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition_from_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition__from__date_of_foo_event__before__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .before(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition_from_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition__from__date_of_foo_event__until__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).from(FOO_DATE)
                                                 .until(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition_after_before(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition__after__date_of_foo_event__before__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .before(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_evaluates_data_for_last_treatment_on_dentition_after_until(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__on__dentition__position__last__with__some-billingposition__after__date_of_foo_event__until__date_of_bar_event";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).after(FOO_DATE)
                                                 .until(BAR_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .lastOnDentition();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_applies_given_filter(@Mocked Treatments mockTreatments) {
        String columnName = "treatment__position__first__with__some-billingposition__filter_only__some-filter";
        TreatmentInterface expectedResult = new NullTreatment();

        new Expectations() {{
            new Treatments(mockRawData, testCase).until(END_DATE)
                                                 .withBillingposition(SOME_BILLINGPOSITION)
                                                 .filterOnly("some-filter")
                                                 .first();
            result = expectedResult;
        }};

        CaseData result = getResult(columnName);
        assertThat(result.event(columnName)).isEqualTo(expectedResult);
    }

    @Test
    public void it_gets_required_columns() {
        EvaluationColumn column = new Treatment(mockEvaluation, ImmutableMap.of());

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_from() {
        EvaluationColumn column = new Treatment(mockEvaluation, ImmutableMap.of(FROM, "date_biz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_after() {
        EvaluationColumn column = new Treatment(mockEvaluation, ImmutableMap.of(AFTER, "date_biz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_columns_before() {
        EvaluationColumn column = new Treatment(mockEvaluation, ImmutableMap.of(BEFORE, "date_biz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_until() {
        EvaluationColumn column = new Treatment(mockEvaluation, ImmutableMap.of(UNTIL, "date_biz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_biz_event");
    }

    @Test
    public void it_gets_required_columns_from_before() {
        EvaluationColumn column = new Treatment(mockEvaluation,
                                                ImmutableMap.of(FROM, "date_biz_event", BEFORE, "date_baz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_from_until() {
        EvaluationColumn column = new Treatment(mockEvaluation,
                                                ImmutableMap.of(FROM, "date_biz_event", UNTIL, "date_baz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_before() {
        EvaluationColumn column = new Treatment(mockEvaluation,
                                                ImmutableMap.of(AFTER, "date_biz_event", BEFORE, "date_baz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_columns_after_until() {
        EvaluationColumn column = new Treatment(mockEvaluation,
                                                ImmutableMap.of(AFTER, "date_biz_event", UNTIL, "date_baz_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("date_baz_event");
    }

    @Test
    public void it_gets_required_for_filter(@Mocked TreatmentFilters treatmentFilters) {
        new Expectations() {{
            TreatmentFilters.requiredColumns("some-filter");
            result = ImmutableList.of("column1", "column2");
        }};

        EvaluationColumn column = new Treatment(mockEvaluation, ImmutableMap.of(
                UNTIL, "date_biz_event",
                FILTER_ONLY, "some-filter"));

        assertThat(column.requiredColumns().size()).isEqualTo(3);
        assertThat(column.requiredColumns()).contains("date_biz_event");
        assertThat(column.requiredColumns()).contains("column1");
        assertThat(column.requiredColumns()).contains("column2");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__" + Keys.SPLINT).instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung ~~" + Keys.SPLINT + "~~");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_from() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__" + Keys.SPLINT + "__from__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung ~~"
                                                            + Keys.SPLINT
                                                            + "~~ ab ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_after() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__" + Keys.SPLINT + "__after__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung ~~"
                                                            + Keys.SPLINT
                                                            + "~~ nach ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__" + Keys.SPLINT + "__before__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung ~~"
                                                            + Keys.SPLINT
                                                            + "~~ vor ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__" + Keys.SPLINT + "__until__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung ~~"
                                                            + Keys.SPLINT
                                                            + "~~ bis ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_from_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__"
                + Keys.SPLINT
                + "__from__date_of_foo_event__before__date_of_biz_event")
                .instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung ~~" + Keys.SPLINT + "~~ ab ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_from_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__" + Keys.SPLINT +
                "__from__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung ~~" + Keys.SPLINT + "~~ ab ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_after_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__" + Keys.SPLINT +
                "__after__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung ~~" + Keys.SPLINT + "~~ nach ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_after_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__"
                + Keys.SPLINT
                + "__after__date_of_foo_event__until__date_of_biz_event")
                .instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung ~~" + Keys.SPLINT + "~~ nach ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__" + Keys.SPLINT).instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung ~~" + Keys.SPLINT + "~~");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_from() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__" + Keys.SPLINT + "__from__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung ~~"
                                                            + Keys.SPLINT
                                                            + "~~ ab ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_after() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__" + Keys.SPLINT + "__after__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung ~~"
                                                            + Keys.SPLINT
                                                            + "~~ nach ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__" + Keys.SPLINT + "__before__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung ~~"
                                                            + Keys.SPLINT
                                                            + "~~ vor ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__" + Keys.SPLINT + "__until__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung ~~"
                                                            + Keys.SPLINT
                                                            + "~~ bis ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_from_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__"
                + Keys.SPLINT
                + "__from__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzte Behandlung ~~" + Keys.SPLINT + "~~ ab ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_from_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__"
                + Keys.SPLINT
                + "__from__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzte Behandlung ~~" + Keys.SPLINT + "~~ ab ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_after_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__"
                + Keys.SPLINT
                + "__after__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzte Behandlung ~~" + Keys.SPLINT + "~~ nach ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_after_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__last__with__"
                + Keys.SPLINT
                + "__after__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzte Behandlung ~~" + Keys.SPLINT + "~~ nach ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__" + Keys.SPLINT).instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung Gebiss ~~" + Keys.SPLINT + "~~");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition_from() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__"
                + Keys.SPLINT
                + "__from__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung Gebiss ~~"
                                                            + Keys.SPLINT
                                                            + "~~ ab ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition_after() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__"
                + Keys.SPLINT
                + "__after__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung Gebiss ~~"
                                                            + Keys.SPLINT
                                                            + "~~ nach ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__"
                + Keys.SPLINT
                + "__before__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung Gebiss ~~"
                                                            + Keys.SPLINT
                                                            + "~~ vor ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__"
                + Keys.SPLINT
                + "__until__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Erste Behandlung Gebiss ~~"
                                                            + Keys.SPLINT
                                                            + "~~ bis ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition_from_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__"
                + Keys.SPLINT
                + "__from__date_of_foo_event__before__date_of_biz_event")
                .instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung Gebiss ~~" + Keys.SPLINT + "~~ ab ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition_from_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__" + Keys.SPLINT +
                "__from__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung Gebiss ~~" + Keys.SPLINT + "~~ ab ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition_after_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__" + Keys.SPLINT +
                "__after__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung Gebiss ~~" + Keys.SPLINT + "~~ nach ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_first_treatment_on_dentition_after_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__first__with__"
                + Keys.SPLINT
                + "__after__date_of_foo_event__until__date_of_biz_event")
                .instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung Gebiss ~~" + Keys.SPLINT + "~~ nach ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__" + Keys.SPLINT).instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung Gebiss ~~" + Keys.SPLINT + "~~");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition_from() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__"
                + Keys.SPLINT
                + "__from__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung Gebiss ~~"
                                                            + Keys.SPLINT
                                                            + "~~ ab ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition_after() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__"
                + Keys.SPLINT
                + "__after__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung Gebiss ~~"
                                                            + Keys.SPLINT
                                                            + "~~ nach ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__"
                + Keys.SPLINT
                + "__before__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung Gebiss ~~"
                                                            + Keys.SPLINT
                                                            + "~~ vor ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__"
                + Keys.SPLINT
                + "__until__date_of_foo_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo("Letzte Behandlung Gebiss ~~"
                                                            + Keys.SPLINT
                                                            + "~~ bis ##date_of_foo_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition_from_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__"
                + Keys.SPLINT
                + "__from__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzte Behandlung Gebiss ~~" + Keys.SPLINT + "~~ ab ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition_from_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__"
                + Keys.SPLINT
                + "__from__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzte Behandlung Gebiss ~~" + Keys.SPLINT + "~~ ab ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition_after_before() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__"
                + Keys.SPLINT
                + "__after__date_of_foo_event__before__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzte Behandlung Gebiss ~~"
                + Keys.SPLINT
                + "~~ nach ##date_of_foo_event## vor ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_for_last_treatment_on_dentition_after_until() {
        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__on__dentition__position__last__with__"
                + Keys.SPLINT
                + "__after__date_of_foo_event__until__date_of_biz_event").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Letzte Behandlung Gebiss ~~"
                + Keys.SPLINT
                + "~~ nach ##date_of_foo_event## bis ##date_of_biz_event##");
    }

    @Test
    public void it_gets_short_doc_with_filter(@Mocked TreatmentFilters mockTreatmentsFilters) {
        new Expectations() {{
            TreatmentFilters.shortDoc("some-filter");
            result = "short doc for some filter";
        }};

        EvaluationColumn column = new Column(
                mockEvaluation,
                "treatment__position__first__with__"
                + Keys.SPLINT
                + "__until__date_of_foo_event__filter_only__some-filter").instance();

        assertThat(column.documentationShortDe()).isEqualTo(
                "Erste Behandlung ~~"
                + Keys.SPLINT
                + "~~ bis ##date_of_foo_event## (short doc for some filter)");
    }

    @Test
    public void it_gets_required_billing_positions(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column;

        column = new Treatment(mockEvaluation, ImmutableMap.of(WITH, SOME_BILLINGPOSITION));
        assertThat(column.requiredBillingpositions().size()).isEqualTo(1);
        assertThat(column.requiredBillingpositions()).contains(SOME_BILLINGPOSITION);
    }

    private CaseData getResult(String columnName) {
        return new Column(mockEvaluation, columnName).instance().evaluate(mockRawData, testCase);
    }

    private CaseData testCaseData() {
        return testCase(ImmutableMap.of(
                "date_of_foo_event", FOO_DATE,
                "date_of_bar_event", BAR_DATE,
                DATE_END_SEARCH_PERIOD, END_DATE
        ));
    }
}