package de.dentareport.evaluations;

//import de.dentareport.gui.ProgressUpdate;
import de.dentareport.models.RawData;
import de.dentareport.utils.db.DbRow;

import java.util.List;
import java.util.stream.Collectors;

public class PatientEvaluation {

    private final CaseEvaluation caseEvaluation;

    public PatientEvaluation() {
        this.caseEvaluation = new CaseEvaluation();
    }

    public List<DbRow> evaluate(Evaluation evaluation,
                                RawData rawData,
                                List<String> teeth) {
        return teeth.stream()
                .map(this::tick)
                .map(tooth -> caseEvaluation.evaluate(evaluation, rawData, tooth))
                .collect(Collectors.toList());
    }

    private String tick(String tooth) {
//        ProgressUpdate.tick();
        return tooth;
    }
}
