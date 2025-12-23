package de.dentareport.gui.views.start;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

public class StartPresenter {

    private final UiController uiController;

    public StartPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onOpenReport() {
        uiController.showView(ViewId.REPORT);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }
}

