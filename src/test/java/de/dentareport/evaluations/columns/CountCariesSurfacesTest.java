package de.dentareport.evaluations.columns;

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

public class CountCariesSurfacesTest {

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
        final String columnName = "count_caries_surfaces__specification__some_caries_spec__of__foo_event";
        final String tooth = "42";
        final String countCariesSurfaces = "4";

        CaseData caseData = new CaseData(tooth);
        caseData.setEvent("foo_event", mockEventInterface);

        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            mockEventInterface.countCariesSurfaces(tooth, "some_caries_spec");
            result = countCariesSurfaces;
        }};

        EvaluationColumn column = new Column(mockEvaluation, columnName).instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string(columnName)).isEqualTo(countCariesSurfaces);
    }

    @Test
    public void it_gets_required_columns() {
        EvaluationColumn column = new CountCariesSurfaces(mockEvaluation, ImmutableMap.of(OF, "some_evidence"));

        assertThat(column.requiredColumns().size()).isEqualTo(2);
        assertThat(column.requiredColumns()).contains("tooth");
        assertThat(column.requiredColumns()).contains("some_evidence");
    }
}