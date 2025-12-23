package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.options;
import static org.assertj.core.api.Assertions.assertThat;

public class ToothTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData) {
        CaseData caseData = new CaseData("23");

        EvaluationColumn column = new Tooth(mockEvaluation, options());
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("tooth")).isEqualTo("23");
    }
}