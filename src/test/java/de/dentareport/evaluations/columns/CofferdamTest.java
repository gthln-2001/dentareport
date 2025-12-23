package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.Evaluation;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CofferdamTest {

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Cofferdam(mockEvaluation, ImmutableMap.of(AT, "foo_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("foo_event");
    }

    @Test
    public void it_gets_required_billing_positions(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Cofferdam(mockEvaluation, ImmutableMap.of(AT, "foo_event"));

        assertThat(column.requiredBillingpositions().size()).isEqualTo(1);
        assertThat(column.requiredBillingpositions()).contains(KOFFERDAM);
    }
}