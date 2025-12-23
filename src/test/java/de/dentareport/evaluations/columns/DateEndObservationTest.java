package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.options;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static org.assertj.core.api.Assertions.assertThat;

public class DateEndObservationTest {

    @Test
    public void it_evaluates_data_if_event_end_observation_exists(@Mocked Evaluation mockEvaluation,
                                                                  @Mocked RawData mockRawData) {
        CaseData caseData = testCase();
        caseData.setString("date__of__event_end_observation", "some-date");
        EvaluationColumn column = new DateEndObservation(mockEvaluation, options());

        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("date_end_observation")).isEqualTo("some-date");
    }

    @Test
    public void it_evaluates_data_if_event_end_observation_does_not_exist(@Mocked Evaluation mockEvaluation,
                                                                          @Mocked RawData mockRawData) {
        CaseData caseData = testCase();
        caseData.setString("date__of__event_end_observation", Keys.NO_DATA);
        new Expectations() {{
            mockRawData.lastVisit();
            result = "last-visit";
        }};
        EvaluationColumn column = new DateEndObservation(mockEvaluation, options());

        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("date_end_observation")).isEqualTo("last-visit");
    }
}