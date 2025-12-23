package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.OF;
import static org.assertj.core.api.Assertions.assertThat;

public class SurfacesTest {

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
            mockEventInterface.surfaces();
            result = ImmutableList.of("s1", "s2", "s3");
        }};

        EvaluationColumn column = new Column(mockEvaluation, "surfaces__of__foo_event").instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("surfaces__of__foo_event")).isEqualTo("s1,s2,s3");
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Surfaces(mockEvaluation, ImmutableMap.of(OF, "foo_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("foo_event");
    }
}