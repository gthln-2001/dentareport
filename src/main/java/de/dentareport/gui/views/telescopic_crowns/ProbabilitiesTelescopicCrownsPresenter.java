package de.dentareport.gui.views.telescopic_crowns;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.TableRowGeneralInformationTelescopicCrownsCountAndDistribution;

import java.util.Map;

// TODO: TEST?
public class ProbabilitiesTelescopicCrownsPresenter {

    private final UiController uiController;

    public ProbabilitiesTelescopicCrownsPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.TELESCOPIC_CROWNS);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onDisplay(String event, String dependency) {
        uiController.showView(ViewId.PROBABILITIES_DISPLAY_TELESCOPIC_CROWNS, event, dependency);
    }
}
