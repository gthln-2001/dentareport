package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.TEST_TOOTH;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class StatusTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData,
                                  @Mocked EventInterface mockEventInterface,
                                  @Mocked AvailableColumns mockAvailableColumns) {
        CaseData caseData = testCase();
        caseData.setEvent("foo_event", mockEventInterface);
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            mockEventInterface.hasStatus(TEST_TOOTH, IMPLANT);
            result = true;
            mockEventInterface.hasStatus(TEST_TOOTH, CROWN);
            result = false;
        }};

        EvaluationColumn column1 = new Column(mockEvaluation, "status__is__" + IMPLANT + "__of__foo_event").instance();
        caseData = column1.evaluate(mockRawData, caseData);
        EvaluationColumn column2 = new Column(mockEvaluation, "status__is__" + CROWN + "__of__foo_event").instance();
        caseData = column2.evaluate(mockRawData, caseData);

        assertThat(caseData.string("status__is__" + IMPLANT + "__of__foo_event")).isEqualTo(CENSORED_YES);
        assertThat(caseData.string("status__is__" + CROWN + "__of__foo_event")).isEqualTo(CENSORED_NO);
    }

    @Test
    public void it_evaluates_data_if_evidence_is_null_evidence(@Mocked Evaluation mockEvaluation,
                                                               @Mocked RawData mockRawData,
                                                               @Mocked AvailableColumns mockAvailableColumns) {
        CaseData caseData = testCase();
        caseData.setEvent("foo_event", new NullEvidence01());
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
        }};

        EvaluationColumn column1 = new Column(mockEvaluation, "status__is__" + IMPLANT + "__of__foo_event").instance();
        caseData = column1.evaluate(mockRawData, caseData);

        assertThat(caseData.string("status__is__" + IMPLANT + "__of__foo_event")).isEqualTo(NO_DATA);
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Status(mockEvaluation, ImmutableMap.of(OF, "foo_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("foo_event");
    }
}