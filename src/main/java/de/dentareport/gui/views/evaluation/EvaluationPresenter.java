package de.dentareport.gui.views.evaluation;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

// TODO: Test?
public class EvaluationPresenter {

    private final UiController uiController;

    public EvaluationPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.START);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }


}

