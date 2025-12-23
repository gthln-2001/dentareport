package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static de.dentareport.evaluations.columns.Helper.options;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static org.assertj.core.api.Assertions.assertThat;

public class EventEndObservationTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData,
                                  @Mocked EventInterface mockEvent) {
        CaseData caseData = testCase();
        new Expectations() {{
            mockEvaluation.eventEndObservation(mockRawData, caseData);
        }};

        EvaluationColumn column = new EventEndObservation(mockEvaluation, options());
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.event("event_end_observation")).isEqualTo(mockEvent);
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation,
                                         @Mocked RawData mockRawData) {
        List<String> dependencies = ImmutableList.of("foo", "bar");
        new Expectations() {{
            mockEvaluation.dependenciesForEventEndObservation();
            result = dependencies;
        }};

        EvaluationColumn column = new EventEndObservation(mockEvaluation, options());

        assertThat(column.requiredColumns()).isEqualTo(dependencies);
    }

    @Test
    public void it_gets_required_billing_positions(@Mocked Evaluation mockEvaluation) {
        Set<String> billingPositions = ImmutableSet.of("foo", "bar");
        new Expectations() {{
            mockEvaluation.requiredBillingPositionsForEventEndObservation();
            result = billingPositions;
        }};

        EvaluationColumn column = new EventEndObservation(mockEvaluation, options());

        assertThat(column.requiredBillingpositions()).isEqualTo(billingPositions);
    }

    @Test
    public void it_gets_required_evidence_types(@Mocked Evaluation mockEvaluation) {
        Set<String> evidenceTypes = ImmutableSet.of("foo", "bar");
        new Expectations() {{
            mockEvaluation.requiredEvidenceTypesForEventEndObservation();
            result = evidenceTypes;
        }};

        EvaluationColumn column = new EventEndObservation(mockEvaluation, options());

        assertThat(column.requiredEvidenceTypes()).isEqualTo(evidenceTypes);
    }

    @Test
    public void it_gets_long_doc(@Mocked Evaluation mockEvaluation) {
        new Expectations() {{
            mockEvaluation.longDocumentationForEndObservation();
            result = "long doc";
        }};

        EvaluationColumn column = new EventEndObservation(mockEvaluation, options());

        assertThat(column.documentationLongDe()).isEqualTo("long doc");
    }
}