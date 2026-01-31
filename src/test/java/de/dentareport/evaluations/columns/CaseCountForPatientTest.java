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

// TODO: TEST?
public class CaseCountForPatientTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData) {
        new Expectations() {{
            mockRawData.caseCount();
            result = "23";
        }};
        EvaluationColumn column = new CaseCountForPatient(mockEvaluation, options());

        CaseData result = column.evaluate(mockRawData, testCase());

        assertThat(result.string("case_count_for_patient")).isEqualTo("23");
    }
}