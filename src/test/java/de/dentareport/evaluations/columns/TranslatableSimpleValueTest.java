package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.Evaluation;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TranslatableSimpleValueTest {

    @Test
    public void it_is_translatable(@Mocked Evaluation mockEvaluation) {
        Foo foo = new Foo(mockEvaluation, ImmutableMap.of());

        assertThat(foo.isTranslatable()).isTrue();

    }

    private class Foo extends TranslatableSimpleValue {

        public Foo(Evaluation evaluation, Map<String, String> options) {
            super(evaluation, options);
        }

        @Override
        public String documentationShortDe() {
            return null;
        }
    }
}