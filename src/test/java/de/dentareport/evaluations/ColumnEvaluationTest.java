package de.dentareport.evaluations;

import de.dentareport.models.CaseData;
import de.dentareport.models.Column;
import de.dentareport.models.RawData;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ColumnEvaluationTest {

    private ColumnEvaluation columnEvaluation;

    @BeforeEach
    public void setUp() {
        this.columnEvaluation = new ColumnEvaluation();
    }

    @Test
    public void it_evaluates_column(@Mocked Column mockColumn,
                                    @Mocked RawData mockRawData) {
        CaseData caseDataIn = new CaseData("42");
        CaseData caseDataOut = new CaseData("42");
        new Expectations() {{
            mockColumn.evaluate(mockRawData, caseDataIn);
            result = caseDataOut;
        }};

        assertThat(columnEvaluation.evaluate(mockRawData, caseDataIn, mockColumn))
                .isEqualTo(caseDataOut);
    }
}