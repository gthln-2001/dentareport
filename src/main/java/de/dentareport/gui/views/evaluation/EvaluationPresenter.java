package de.dentareport.gui.views.evaluation;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;

import static de.dentareport.utils.Keys.GUI_TEXT_FUNCTION_NOT_AVAILABLE_IN_DEMO_VERSION;

// TODO: Test?
public class EvaluationPresenter {

    private final UiController uiController;

    public EvaluationPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onNotAvailableInDemo() {
        uiController.showInfo(GUI_TEXT_FUNCTION_NOT_AVAILABLE_IN_DEMO_VERSION);
    }

    public void onBack() {
        uiController.showView(ViewId.START);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onShowFillings() {
        uiController.showView(ViewId.FILLINGS);
    }

    public void onShowTelescopicCrowns() {
        uiController.showView(ViewId.TELESCOPIC_CROWNS);

    }

    public void onShowGeneralPatientInformation() {
        uiController.showView(ViewId.GENERAL_PATIENT_INFORMATION);
    }
}

