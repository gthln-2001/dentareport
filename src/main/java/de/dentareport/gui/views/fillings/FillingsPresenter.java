package de.dentareport.gui.views.fillings;

// TODO: Test?

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

public class FillingsPresenter {

    private final UiController uiController;

    public FillingsPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.EVALUATION);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }
}
