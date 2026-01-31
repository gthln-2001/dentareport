package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.Eventlist;
import de.dentareport.models.RawData;
import de.dentareport.utils.Treatments;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.AFTER;
import static de.dentareport.utils.Keys.DATE_END_SEARCH_PERIOD;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class EventsOfInterestTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData,
                                  @Mocked AvailableColumns mockAvailableColumns,
                                  @Mocked Eventlist mockEventlist,
                                  @Mocked Treatments mockTreatments) {
        CaseData caseData = testCase();
        caseData.setString("date_of_foo", "some-date");
        caseData.setString(DATE_END_SEARCH_PERIOD, "some-other-date");
        Set<String> billingpositions = ImmutableSet.of("foo", "bar");
        new Expectations() {{
            mockEvaluation.requiredBillingPositionsForEventsOfInterest(withAny(new HashMap<>()));
            result = billingpositions;
            mockAvailableColumns.has(anyString);
            result = true;
            new Treatments(mockRawData, caseData).after("some-date").until("some-other-date").withBillingpositions(billingpositions).list();
        }};

        EvaluationColumn column = new Column(mockEvaluation, "events_of_interest__after__date_of_foo").instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.eventlist("events_of_interest__after__date_of_foo")).isEqualTo(mockEventlist);
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column;

        column = new EventsOfInterest(mockEvaluation, ImmutableMap.of(AFTER, "date_of_foo_event"));
        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("date_of_foo_event");
        assertThat(column.requiredColumns()).contains(DATE_END_SEARCH_PERIOD);
    }

    @Test
    public void it_gets_required_billing_positions(@Mocked Evaluation mockEvaluation) {
        Set<String> billingPositions = ImmutableSet.of("foo", "bar");
        new Expectations() {{
            mockEvaluation.requiredBillingPositionsForEventsOfInterest(ImmutableMap.of(AFTER, "date_of_foo_event"));
            result = billingPositions;
        }};

        EvaluationColumn column = new EventsOfInterest(mockEvaluation, ImmutableMap.of(AFTER, "date_of_foo_event"));

        assertThat(column.requiredBillingpositions()).isEqualTo(billingPositions);
    }
}