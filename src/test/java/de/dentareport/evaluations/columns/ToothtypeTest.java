package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.string.ToothStringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.options;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ToothtypeTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData,
                                  @Mocked ToothStringUtils mockToothStringUtils) {
        CaseData caseData = testCase();
        caseData.setString("tooth", "foo");
        new Expectations() {{
            ToothStringUtils.type("foo");
            result = "bar";
        }};

        EvaluationColumn column = new Toothtype(mockEvaluation, options());
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("toothtype")).isEqualTo("bar");
    }
}