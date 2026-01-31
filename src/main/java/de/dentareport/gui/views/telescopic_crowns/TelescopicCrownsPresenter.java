package de.dentareport.gui.views.telescopic_crowns;

// TODO: Test?

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

// TODO: TEST?
public class TelescopicCrownsPresenter {

    private final UiController uiController;

    public TelescopicCrownsPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.EVALUATION);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }
}
