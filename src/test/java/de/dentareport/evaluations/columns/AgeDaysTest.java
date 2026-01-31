package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.utils.string.DateStringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.AT;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class AgeDaysTest {
    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData,
                                  @Mocked EventInterface mockEventInterface,
                                  @Mocked DateStringUtils mockDateStringUtils,
                                  @Mocked AvailableColumns mockAvailableColumns) {
        CaseData caseData = testCase();
        caseData.setString("date_of_birth", "dob");
        caseData.setEvent("foo_event", mockEventInterface);
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            mockEventInterface.date();
            result = "some-date";
            DateStringUtils.daysBetween("dob", "some-date");
            result = "some-days";
        }};

        EvaluationColumn column = new Column(mockEvaluation, "age_days__at__foo_event").instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("age_days__at__foo_event")).isEqualTo("some-days");
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new AgeDays(mockEvaluation, ImmutableMap.of(AT, "foo_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date__of__foo_event");
        assertThat(column.requiredColumns()).contains("date_of_birth");
    }
}