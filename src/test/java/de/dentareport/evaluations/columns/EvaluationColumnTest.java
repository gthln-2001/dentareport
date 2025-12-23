package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.Config;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.Column;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EvaluationColumnTest {

    @Test
    public void it_gets_dependencies_if_they_exist_as_evaluation_column(
            @Mocked Evaluation mockEvaluation,
            @Mocked Config mockConfig,
            @Mocked AvailableColumns mockAvailableColumns) {

        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            Config.evaluationColumnsPath();
            result = "de.dentareport.testclasses.columns.";
        }};

        assertThat(new Column(mockEvaluation, "b").dependencies())
                .isEqualTo(ImmutableList.of("c", "d"));
    }

    @Test
    public void it_gets_dependencies_if_they_have_to_be_transformed_to_related_columns(
            @Mocked Evaluation mockEvaluation,
            @Mocked Config mockConfig,
            @Mocked AvailableColumns mockAvailableColumns) {

        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = false;
            mockAvailableColumns.relatedColumn("c");
            result = ImmutableList.of("related_c");
            mockAvailableColumns.relatedColumn("d");
            result = ImmutableList.of("related_d");
            Config.evaluationColumnsPath();
            result = "de.dentareport.testclasses.columns.";
        }};

        assertThat(new Column(mockEvaluation, "b").dependencies())
                .isEqualTo(ImmutableList.of("related_c", "related_d"));
    }
}