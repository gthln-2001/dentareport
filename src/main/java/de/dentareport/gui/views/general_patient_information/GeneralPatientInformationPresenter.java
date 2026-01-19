package de.dentareport.gui.views.general_patient_information;

// TODO: Test?

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

public class GeneralPatientInformationPresenter {

    private final UiController uiController;

    public GeneralPatientInformationPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.EVALUATION);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }
}
