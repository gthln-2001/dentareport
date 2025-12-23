package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static de.dentareport.evaluations.columns.Helper.options;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static org.assertj.core.api.Assertions.assertThat;

public class EventStartObservationTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData,
                                  @Mocked EventInterface mockEvent) {
        CaseData caseData = testCase();
        new Expectations() {{
            mockEvaluation.eventStartObservation(mockRawData, caseData);
        }};

        EvaluationColumn column = new EventStartObservation(mockEvaluation, options());
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.event("event_start_observation")).isEqualTo(mockEvent);
    }

    @Test
    public void it_gets_required_billing_positions(@Mocked Evaluation mockEvaluation) {
        Set<String> billingPositions = ImmutableSet.of("foo", "bar");
        new Expectations() {{
            mockEvaluation.requiredBillingPositionsForEventStartObservation();
            result = billingPositions;
        }};

        EvaluationColumn column = new EventStartObservation(mockEvaluation, options());

        assertThat(column.requiredBillingpositions()).isEqualTo(billingPositions);
    }

    @Test
    public void it_gets_required_evidence_types(@Mocked Evaluation mockEvaluation) {
        Set<String> evidenceTypes = ImmutableSet.of("foo", "bar");
        new Expectations() {{
            mockEvaluation.requiredEvidenceTypesForEventStartObservation();
            result = evidenceTypes;
        }};

        EvaluationColumn column = new EventStartObservation(mockEvaluation, options());

        assertThat(column.requiredEvidenceTypes()).isEqualTo(evidenceTypes);
    }

    @Test
    public void it_gets_long_doc(@Mocked Evaluation mockEvaluation) {
        new Expectations() {{
            mockEvaluation.longDocumentationForStartObservation();
            result = "long doc";
        }};

        EvaluationColumn column = new EventStartObservation(mockEvaluation, options());

        assertThat(column.documentationLongDe()).isEqualTo("long doc");
    }
}