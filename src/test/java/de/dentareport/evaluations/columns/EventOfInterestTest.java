package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.AFTER;
import static de.dentareport.utils.Keys.POSITION;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class EventOfInterestTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData,
                                  @Mocked AvailableColumns mockAvailableColumns,
                                  @Mocked Eventlist mockEventlist,
                                  @Mocked EventInterface mockEventInterface) {
        CaseData caseData = testCase();
        caseData.setEventlist("events_of_interest__after__date_of_foo", mockEventlist);
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            mockEventlist.event("42");
        }};

        EvaluationColumn column = new Column(
                mockEvaluation,
                "event_of_interest__after__date_of_foo__position__42").instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.event("event_of_interest__after__date_of_foo__position__42"))
                .isEqualTo(mockEventInterface);
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column;

        column = new EventOfInterest(mockEvaluation,
                                     ImmutableMap.of(AFTER, "date_of_foo_event", POSITION, "42"));
        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns())
                .contains("events_of_interest__after__date_of_foo_event");
    }
}