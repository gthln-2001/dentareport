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
    private final Translate translate;
    private Map<String, TableRowGeneralInformationFillingsCountAndDistribution> count;
    private Map<String, TableRowGeneralInformationFillingsCountAndDistribution> distribution;

    public ProbabilitiesFillingsPresenter(UiController uiController) {
        this.uiController = uiController;
        this.translate = new Translate();
    }

    public void onBack() {
        uiController.showView(ViewId.TELESCOPIC_CROWNS);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onDisplay(String event, String dependency) {
        System.out.println(event);
        System.out.println(dependency);
    }
}
