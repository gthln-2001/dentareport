package de.dentareport.gui.navigation;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.views.copy_files.CopyFilesPresenter;
import de.dentareport.gui.views.copy_files.CopyFilesView;
import de.dentareport.gui.views.evaluation.EvaluationPresenter;
import de.dentareport.gui.views.evaluation.EvaluationView;
import de.dentareport.gui.views.fillings.*;
import de.dentareport.gui.views.general_patient_information.GeneralPatientInformationPresenter;
import de.dentareport.gui.views.general_patient_information.GeneralPatientInformationView;
import de.dentareport.gui.views.import_data.ImportDataPresenter;
import de.dentareport.gui.views.import_data.ImportDataView;
import de.dentareport.gui.views.start.StartPresenter;
import de.dentareport.gui.views.start.StartView;
import de.dentareport.gui.views.telescopic_crowns.*;

import javax.swing.*;

// TODO: TEST?
public class ViewFactory {

    public JComponent create(ViewId viewId, UiController uiController) {
        return switch (viewId) {
            case COPY_FILES -> new CopyFilesView(new CopyFilesPresenter(uiController));
            case EVALUATION -> new EvaluationView(new EvaluationPresenter(uiController));
            case FILLINGS -> new FillingsView(new FillingsPresenter(uiController));
            case GENERAL_INFORMATION_FILLINGS ->
                    new GeneralInformationFillingsView(new GeneralInformationFillingsPresenter(uiController));
            case GENERAL_INFORMATION_TELESCOPIC_CROWNS ->
                    new GeneralInformationTelescopicCrownsView(new GeneralInformationTelescopicCrownsPresenter(uiController));
            case GENERAL_PATIENT_INFORMATION ->
                    new GeneralPatientInformationView(new GeneralPatientInformationPresenter(uiController));
            case IMPORT_DATA -> new ImportDataView(new ImportDataPresenter(uiController));
            case PROBABILITIES_FILLINGS ->
                    new ProbabilitiesFillingsView(new ProbabilitiesFillingsPresenter(uiController));
            case PROBABILITIES_TELESCOPIC_CROWNS ->
                    new ProbabilitiesTelescopicCrownsView(new ProbabilitiesTelescopicCrownsPresenter(uiController));
            case START -> new StartView(new StartPresenter(uiController));
            case TELESCOPIC_CROWNS -> new TelescopicCrownsView(new TelescopicCrownsPresenter(uiController));
            default -> throw new IllegalStateException("Unexpected value: " + viewId);
        };
    }

    public JComponent create(ViewId viewId, UiController uiController, String event, String dependency) {
        return switch (viewId) {
            case PROBABILITIES_DISPLAY_FILLINGS ->
                    new ProbabilitiesDisplayFillingsView(new ProbabilitiesDisplayFillingsPresenter(uiController),
                            event, dependency);
            case PROBABILITIES_DISPLAY_TELESCOPIC_CROWNS ->
                    new ProbabilitiesDisplayTelescopicCrownsView(new ProbabilitiesDisplayTelescopicCrownsPresenter(uiController), event, dependency);
            default -> throw new IllegalStateException("Unexpected value: " + viewId);
        };
    }
}
