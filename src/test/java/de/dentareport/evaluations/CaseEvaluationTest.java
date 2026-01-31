package de.dentareport.evaluations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.RawData;
import de.dentareport.utils.db.DbRow;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class CaseEvaluationTest {

    private CaseEvaluation caseEvaluation;

    @BeforeEach
    public void setUp() {
        this.caseEvaluation = new CaseEvaluation();
    }

    @Test
    public void it_evaluates_columns(@Mocked Evaluation mockEvaluation,
                                     @Mocked ColumnEvaluation mockColumnEvaluation,
                                     @Mocked RawData mockRawData) {
        Column column1 = new Column(mockEvaluation, "foo");
        Column column2 = new Column(mockEvaluation, "bar");
        List<Column> columns = ImmutableList.of(column1, column2);
        Set<String> requiredColumns = ImmutableSet.of("foo", "bar");
        CaseData caseDataStep1 = new CaseData("42");
        CaseData caseDataStep2 = new CaseData("42");
        caseDataStep2.setString("foo", "foo-result");
        caseDataStep2.setString("bar", "bar-result");

        new Expectations() {{
            mockRawData.setTreatmentsOnEvaluatedTooth("42");
            mockEvaluation.columns();
            result = columns;
            mockEvaluation.requiredColumns();
            result = requiredColumns;
            mockColumnEvaluation.evaluate(mockRawData, (CaseData) any, column1);
            result = caseDataStep1;
            mockColumnEvaluation.evaluate(mockRawData, caseDataStep1, column2);
            result = caseDataStep2;
        }};

        DbRow result = caseEvaluation.evaluate(mockEvaluation, mockRawData, "42");

        assertThat(result.cells().size()).isEqualTo(2);
        assertThat(result.value("foo")).isEqualTo("foo-result");
        assertThat(result.value("bar")).isEqualTo("bar-result");
    }
}