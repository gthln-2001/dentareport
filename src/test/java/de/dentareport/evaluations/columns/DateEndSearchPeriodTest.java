package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Treatments;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.options;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.DATE_END_SEARCH_PERIOD;
import static de.dentareport.utils.Keys.TOOTH_LOSS;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DateEndSearchPeriodTest {

    @Test
    public void it_evaluates_data_if_date_tooth_loss_exists(@Mocked Evaluation mockEvaluation,
                                                            @Mocked RawData mockRawData,
                                                            @Mocked Treatments mockeTreatments) {
        CaseData caseData = testCase();
        caseData.setString("date_start_observation", "some-date");
        new Expectations() {{
            new Treatments(mockRawData, caseData).after("some-date").withBillingposition(TOOTH_LOSS).firstDate();
            result = "result-date";
        }};

        EvaluationColumn column = new DateEndSearchPeriod(mockEvaluation, options());
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string(DATE_END_SEARCH_PERIOD)).isEqualTo("result-date");
    }

    @Test
    public void it_evaluates_data_if_date_tooth_loss_does_not_exist(@Mocked Evaluation mockEvaluation,
                                                                    @Mocked RawData mockRawData,
                                                                    @Mocked Treatments mockeTreatments) {
        CaseData caseData = testCase();
        caseData.setString("date_start_observation", "some-date");
        caseData.setString("date_last_visit", "result-last-visit");
        new Expectations() {{
            new Treatments(mockRawData, caseData).after("some-date").withBillingposition(TOOTH_LOSS).firstDate();
            result = Keys.NO_DATA;
        }};

        EvaluationColumn column = new DateEndSearchPeriod(mockEvaluation, options());
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string(DATE_END_SEARCH_PERIOD)).isEqualTo("result-last-visit");
    }
}