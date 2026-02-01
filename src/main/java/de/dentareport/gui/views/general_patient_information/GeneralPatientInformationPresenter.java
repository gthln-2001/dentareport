package de.dentareport.gui.views.general_patient_information;

// TODO: Test?

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.TableRowGeneralPatientInformationAverages;
import de.dentareport.gui.table_models.GeneralPatientInformationAverages;

import java.util.List;

// TODO: TEST?
public class GeneralPatientInformationPresenter {

    private final UiController uiController;

    public GeneralPatientInformationPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public GeneralPatientInformationAverages getGeneralPatientInformationAverages() {

        // Normally from DB / service:
        List<TableRowGeneralPatientInformationAverages> tableRows = List.of(
                new TableRowGeneralPatientInformationAverages("Anna", "Schmidt", "1987-02-10", "AOK"),
                new TableRowGeneralPatientInformationAverages("Peter", "Müller", "1975-06-21", "TK")
        );

        return new GeneralPatientInformationAverages(tableRows);
    }

    public void onBack() {
        uiController.showView(ViewId.EVALUATION);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }
}
