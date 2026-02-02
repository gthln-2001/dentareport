package de.dentareport.gui.views.telescopic_crowns;

// TODO: Test?

import de.dentareport.Metadata;
import de.dentareport.evaluations.Evaluation9;
import de.dentareport.evaluations.MetaEvaluation;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.progress.EvaluationUpdate;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.DateStringUtils;

import javax.swing.*;
import java.util.List;

// TODO: TEST?
public class TelescopicCrownsPresenter {

    private final UiController uiController;
    private TelescopicCrownsView view;

    public TelescopicCrownsPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.EVALUATION);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onGeneralInformationTelescopicCrowns() {
        uiController.showView(ViewId.GENERAL_INFORMATION_TELESCOPIC_CROWNS);
    }

    public void onProbabilitiesTelescopicCrowns() {
    }

    public void onStartNewEvaluationTelescopicCrowns() {
        Metadata.delete(Keys.METADATA_KEY_VALID_EVALUATION_TELESCOPIC_CROWN);
        view.startEvaluation();
        evaluate();
    }

    public void setView(TelescopicCrownsView view) {
        this.view = view;
    }

    private void evaluate() {
        SwingWorker<Void, EvaluationUpdate> worker =
                new SwingWorker<>() {

                    @Override
                    protected Void doInBackground() {
                        Evaluation9 evaluation9 = new Evaluation9();
                        evaluation9.evaluate((percent, message) -> publish(new EvaluationUpdate(percent, message)));
                        MetaEvaluation metaEvaluation = new MetaEvaluation(evaluation9);
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
                        Metadata.set(Keys.METADATA_KEY_VALID_EVALUATION_TELESCOPIC_CROWN, DateStringUtils.now());
                        view.evaluationDone();
                    }
                };

        worker.execute();
    }
}
