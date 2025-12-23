package de.dentareport.gui.tasks;

import de.dentareport.Metadata;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.MetaEvaluation;
import de.dentareport.exceptions.DentereportInterruptedException;
import de.dentareport.gui.ProgressUpdate;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.DateStringUtils;

import static de.dentareport.utils.db.DbConnection.db;

public class EvaluatorTask extends BaseTask {

    private final String metadataKey;
    private final Class<? extends Evaluation> evaluationClass;

    public EvaluatorTask(String metadataKey, Class<? extends Evaluation> evaluationClass) {
        this.metadataKey = metadataKey;
        this.evaluationClass = evaluationClass;
    }

    @Override
    public Void call() {
        try {
            Metadata.delete(metadataKey);
            ProgressUpdate.setTask(this);
            if (!availableEvaluations().contains(evaluationClass)) {
                throw new IllegalArgumentException();
            }
            Evaluation evaluation = evaluationClass.newInstance();
            evaluation.evaluate();

            MetaEvaluation metaEvaluation = new MetaEvaluation(evaluation);
            metaEvaluation.evaluate();

            updateMessage(Keys.GUI_TEXT_DONE);
            ProgressUpdate.finished();
            Metadata.set(metadataKey, DateStringUtils.now());
            return null;
        } catch (DentereportInterruptedException | IllegalAccessException | InstantiationException e) {
            return null;
        } finally {
            db().close();
        }
    }
}
