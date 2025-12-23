package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.options;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static org.assertj.core.api.Assertions.assertThat;

public class DateFirstVisitTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData) {
        new Expectations() {{
            mockRawData.firstVisit();
            result = "foobar";
        }};
        EvaluationColumn column = new DateFirstVisit(mockEvaluation, options());

        CaseData result = column.evaluate(mockRawData, testCase());

        assertThat(result.string("date_first_visit")).isEqualTo("foobar");
    }
}