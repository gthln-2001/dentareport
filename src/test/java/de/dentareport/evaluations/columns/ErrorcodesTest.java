package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ErrorcodesTest {

    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    RawData mockRawData;
    @Mocked
    EventInterface mockEventInterface;
    @Mocked
    AvailableColumns mockAvailableColumns;

    @Test
    public void it_evaluates_data_without_errors() {
        CaseData caseData = testCase();
        caseData.setString("tooth", "42");
        caseData.setString("billingcode__of__event_start_observation", "foo");
        caseData.setString("surfaces__of__event_start_observation", "");
        caseData.setEvent("evidence_01__position__last__before__date_start_observation", mockEventInterface);
        new Expectations() {{
            mockEventInterface.status("42");
            result = Keys.EVIDENCE_STATUS__SOUND;
        }};
        EvaluationColumn column = new Column(mockEvaluation, "errorcodes").instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("errorcodes")).isEqualTo("");
    }

    @Test
    public void it_evaluates_error_code_100() {
        CaseData caseData = testCase();
        caseData.setString("tooth", "42");
        caseData.setString("billingcode__of__event_start_observation", "foo");
        caseData.setString("surfaces__of__event_start_observation", "");
        caseData.setEvent("evidence_01__position__last__before__date_start_observation", mockEventInterface);
        new Expectations() {{
            mockEventInterface.status("42");
            result = Keys.EVIDENCE_STATUS__FEHLEND;
        }};

        EvaluationColumn column = new Column(mockEvaluation, "errorcodes").instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("errorcodes")).isEqualTo("100");
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Errorcodes(mockEvaluation, ImmutableMap.of());

        assertThat(column.requiredColumns().size()).isEqualTo(4);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("evidence_01__position__last__before__date_start_observation");
        assertThat(column.requiredColumns()).contains("billingcode__of__event_start_observation");
        assertThat(column.requiredColumns()).contains("surfaces__of__event_start_observation");
    }
}