package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.RawData;
import de.dentareport.utils.GroupUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static de.dentareport.utils.Keys.OF;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupsTest {

    @Test
    public void it_evaluates_data(@Mocked Evaluation mockEvaluation,
                                  @Mocked RawData mockRawData,
                                  @Mocked AvailableColumns mockAvailableColumns) {
        CaseData caseData = testCase();

        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
        }};

        EvaluationColumn column = new Column(mockEvaluation, "groups__of__date_of_foo_event")
                .instance();
        CaseData result = column.evaluate(mockRawData, caseData);

        assertThat(result.string("groups__of__date_of_foo_event")).isEqualTo("");
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation) {
        EvaluationColumn column = new Groups(mockEvaluation, ImmutableMap.of(OF, "date_of_foo_event"));

        assertThat(column.requiredColumns().size()).isEqualTo(1);
        assertThat(column.requiredColumns()).contains("date_of_foo_event");
    }

    @Test
    public void it_gets_long_doc(@Mocked Evaluation mockEvaluation,
                                 @Mocked AvailableColumns mockAvailableColumns,
                                 @Mocked GroupUtils mockGroupUtils) {

        new Expectations() {{
            mockEvaluation.dbTable();
            result = "test_table";
            mockAvailableColumns.has(anyString);
            result = true;
            GroupUtils.valuesForColumn(
                    "test_table",
                    "groups__of__date_of_foo_event",
                    "date_of_foo_event");
            result = "x, y, z";
        }};

        EvaluationColumn column = new Column(mockEvaluation, "groups__of__date_of_foo_event")
                .instance();

        assertThat(column.documentationLongDe()).isEqualTo("x, y, z");
    }
}