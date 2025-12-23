package de.dentareport.gui.views.start;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

// TODO: Test
public class StartPresenter {

    private final UiController ui;

    public StartPresenter(UiController ui) {
        this.ui = ui;
    }

    public void onOpenReport() {
        ui.showView(ViewId.REPORT);
    }

    public void onExitRequested() {
        ui.confirmExit();
    }
}

