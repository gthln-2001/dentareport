package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.Evaluation;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.utils.Keys.OF;
import static org.assertj.core.api.Assertions.assertThat;

public class FtTest {

    @Test
    public void it_extends_simple_value(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Ft(mockEvaluation, ImmutableMap.of(OF, "foo_event"));

        assertThat(column).isInstanceOf(SimpleValue.class);
    }
}