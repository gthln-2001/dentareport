package de.dentareport.evaluations;

//import de.dentareport.gui.ProgressUpdate;

import de.dentareport.gui.util.ProgressListener;
import de.dentareport.models.RawData;
import de.dentareport.utils.db.DbRow;

import java.util.List;
import java.util.stream.Collectors;

// TODO: TEST?
public class PatientEvaluation {

    private final CaseEvaluation caseEvaluation;
    private int count;

    public PatientEvaluation() {
        this.caseEvaluation = new CaseEvaluation();
    }

    public List<DbRow> evaluate(Evaluation evaluation, RawData rawData, List<String> teeth,
                                ProgressListener listener, Integer totalValidCases, int count) {
        this.count = count;
        return teeth.stream()
                .map(tooth -> tick(tooth, listener, totalValidCases))
                .map(tooth -> caseEvaluation.evaluate(evaluation, rawData, tooth))
                .collect(Collectors.toList());
    }

    private String tick(String tooth, ProgressListener listener, Integer totalValidCases) {
        double percent = (double) (count++ * 100) / totalValidCases;
        listener.onProgress((int) percent, String.format("%.1f%%", percent));
        return tooth;
    }
}
