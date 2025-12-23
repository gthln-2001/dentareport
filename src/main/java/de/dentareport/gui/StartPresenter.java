package de.dentareport.gui;

public class StartPresenter {

    private final UiController ui;

    public StartPresenter(UiController ui) {
        this.ui = ui;
    }

    public void onOpenReport() {
        ui.showView(ViewId.REPORT);
    }

    public void onExitRequested() {
        ui.closeApplication();
    }
}

