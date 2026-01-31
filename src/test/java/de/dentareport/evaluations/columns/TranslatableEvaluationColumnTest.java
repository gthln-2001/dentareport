package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class TranslatableEvaluationColumnTest {

    @Test
    public void it_is_translatable(@Mocked Evaluation mockEvaluation) {
        Foo foo = new Foo(mockEvaluation, ImmutableMap.of());

        assertThat(foo.isTranslatable()).isTrue();

    }

    private class Foo extends TranslatableEvaluationColumn {

        public Foo(Evaluation evaluation, Map<String, String> options) {
            super(evaluation, options);
        }

        @Override
        public CaseData evaluate(RawData rawData, CaseData caseData) {
            return null;
        }

        @Override
        public String documentationShortDe() {
            return null;
        }

        @Override
        protected List<String> requiredColumns() {
            return null;
        }
    }
}