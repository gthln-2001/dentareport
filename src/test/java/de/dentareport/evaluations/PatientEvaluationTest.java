package de.dentareport.evaluations;

import com.google.common.collect.ImmutableList;
//import de.dentareport.gui.ProgressUpdate;
//import de.dentareport.gui.tasks.EvaluatorTask;
import de.dentareport.models.RawData;
import de.dentareport.utils.db.DbRow;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class PatientEvaluationTest {

//    @Test
//    public void it_evaluates_cases(@Mocked Evaluation mockEvaluation,
//                                   @Mocked RawData mockRawData,
//                                   @Mocked CaseEvaluation mockCaseEvaluation,
//                                   @Mocked EvaluatorTask mockTask,
//                                   @Mocked ProgressUpdate mockProgressUpdate) {
//        DbRow dbRow1 = new DbRow();
//        DbRow dbRow2 = new DbRow();
//        new Expectations() {{
//            mockCaseEvaluation.evaluate(mockEvaluation, mockRawData, "11");
//            result = dbRow1;
//            mockCaseEvaluation.evaluate(mockEvaluation, mockRawData, "22");
//            result = dbRow2;
//        }};
//
//        PatientEvaluation patientEvaluation = new PatientEvaluation();
//        List<DbRow> result = patientEvaluation.evaluate(mockEvaluation, mockRawData, testCases());
//
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result).contains(dbRow1);
//        assertThat(result).contains(dbRow2);
//
//        new Verifications() {{
//            ProgressUpdate.tick();
//            times = 2;
//        }};
//    }

    private List<String> testCases() {
        return ImmutableList.of("11", "22");
    }
}