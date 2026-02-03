package de.dentareport.gui.views.fillings;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.TableRowGeneralInformationFillingsCountAndDistribution;

import java.util.Map;

// TODO: TEST?
public class ProbabilitiesFillingsPresenter {

    private final UiController uiController;

    public ProbabilitiesFillingsPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.FILLINGS);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onDisplay(String event, String dependency) {
        uiController.showView(ViewId.PROBABILITIES_DISPLAY_FILLINGS, event, dependency);
    }
}
