package de.dentareport.gui;

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

