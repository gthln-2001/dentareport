package de.dentareport.gui.views.start;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

// TODO: Test?
public class StartPresenter {

    private final UiController uiController;

    public StartPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onImportData() {
        uiController.showView(ViewId.IMPORT_DATA);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onEvaluations() {
        uiController.showView(ViewId.EVALUATION);
    }
}

