package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.Evaluation;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.dentareport.utils.Keys.OF;
import static org.assertj.core.api.Assertions.assertThat;

public class YearTest {

    @Test
    public void it_extends_simple_value(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Year(mockEvaluation, ImmutableMap.of(OF, "foo_event"));

        assertThat(column).isInstanceOf(SimpleValue.class);
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Year(mockEvaluation, ImmutableMap.of(OF, "foo_event"));

        List<String> requiredColumns = column.requiredColumns();
        assertThat(requiredColumns.size()).isEqualTo(1);
        assertThat(requiredColumns).contains("foo_event");
    }

}