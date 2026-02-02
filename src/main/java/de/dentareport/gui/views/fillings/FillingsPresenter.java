package de.dentareport.gui.views.fillings;

// TODO: Test?

import de.dentareport.Metadata;
import de.dentareport.evaluations.Evaluation7;
import de.dentareport.evaluations.MetaEvaluation;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.progress.EvaluationUpdate;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.DateStringUtils;

import javax.swing.*;
import java.util.List;

// TODO: TEST?
public class FillingsPresenter {

    private final UiController uiController;
    private FillingsView view;

    public FillingsPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.EVALUATION);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onGeneralInformationFillings() {
        uiController.showView(ViewId.GENERAL_INFORMATION_FILLINGS);
    }

    public void onProbabilitiesFillings() {
    }

    public void onStartNewEvaluationFillings() {
        Metadata.delete(Keys.METADATA_KEY_VALID_EVALUATION_FILLING);
        view.startEvaluation();
        evaluate();
    }

    public void setView(FillingsView view) {
        this.view = view;
    }

    private void evaluate() {
        SwingWorker<Void, EvaluationUpdate> worker =
                new SwingWorker<>() {

                    @Override
                    protected Void doInBackground() {
                        Evaluation7 evaluation7 = new Evaluation7();
                        evaluation7.evaluate((percent, message) -> publish(new EvaluationUpdate(percent, message)));
                        MetaEvaluation metaEvaluation = new MetaEvaluation(evaluation7);
                        metaEvaluation.evaluate();
                        return null;
                    }

                    @Override
                    protected void process(List<EvaluationUpdate> chunks) {
                        EvaluationUpdate last = chunks.getLast();
                        view.setEvaluationProgress(last.percent());
                        view.setEvaluationProgressText(last.message());
                    }

                    @Override
                    protected void done() {
                        // TODO: Check: do we really need this?
                        try {
                            get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        view.setEvaluationProgress(100);
                        view.setEvaluationProgressText(Keys.GUI_TEXT_DONE);
                        Metadata.set(Keys.METADATA_KEY_VALID_EVALUATION_FILLING, DateStringUtils.now());
                        view.evaluationDone();
                    }
                };

        worker.execute();
    }
}
