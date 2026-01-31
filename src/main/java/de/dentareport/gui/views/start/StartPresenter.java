package de.dentareport.gui.views.start;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

// TODO: Test?
// TODO: TEST?
public class StartPresenter {

    private final UiController uiController;

    public StartPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onCopyFiles() {
        uiController.showView(ViewId.COPY_FILES);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onEvaluations() {
        uiController.showView(ViewId.EVALUATION);
    }
}

