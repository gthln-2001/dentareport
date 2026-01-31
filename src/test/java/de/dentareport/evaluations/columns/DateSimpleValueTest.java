package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.Evaluation;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DateSimpleValueTest {

    @Test
    public void it_is_date(@Mocked Evaluation mockEvaluation) {
        Foo foo = new Foo(mockEvaluation, ImmutableMap.of());

        assertThat(foo.isDate()).isTrue();
    }

    private class Foo extends DateSimpleValue {

        public Foo(Evaluation evaluation, Map<String, String> options) {
            super(evaluation, options);
        }

        @Override
        public String documentationShortDe() {
            return null;
        }
    }
}