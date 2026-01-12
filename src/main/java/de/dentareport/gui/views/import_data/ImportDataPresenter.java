package de.dentareport.gui.views.import_data;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

// TODO: Test?
public class ImportDataPresenter {

    private final UiController uiController;

    public ImportDataPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.START);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }


}

