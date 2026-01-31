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

import static de.dentareport.utils.Keys.OF;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class CariesSurfacesTest {

    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    EventInterface mockEventInterface;
    @Mocked
    RawData mockRawData;
    @Mocked
    AvailableColumns mockAvailableColumns;

    @Test
    public void it_evaluates_data() {
        final String columnName = "caries_surfaces__specification__some_caries_spec__of__foo_event";
        final String tooth = "42";
        final ImmutableList<String> cariesSurfaces = ImmutableList.of("a", "b", "c");

        CaseData caseData = new CaseData(tooth);
        caseData.setEvent("foo_event", mockEventInterface);

        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            mockEventInterface.cariesSurfaces(tooth, "some_caries_spec");
            result = cariesSurfaces;
        }};

        EvaluationColumn column = new Column(mockEvaluation, columnName).instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string(columnName)).isEqualTo("a,b,c");
    }

    @Test
    public void it_gets_required_columns() {
        EvaluationColumn column = new CountCariesSurfaces(mockEvaluation, ImmutableMap.of(OF, "some_evidence"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("some_evidence");
    }
}