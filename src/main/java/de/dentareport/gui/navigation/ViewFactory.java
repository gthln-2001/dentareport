package de.dentareport.gui.navigation;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.views.copy_files.CopyFilesPresenter;
import de.dentareport.gui.views.copy_files.CopyFilesView;
import de.dentareport.gui.views.evaluation.EvaluationPresenter;
import de.dentareport.gui.views.evaluation.EvaluationView;
import de.dentareport.gui.views.fillings.FillingsPresenter;
import de.dentareport.gui.views.fillings.FillingsView;
import de.dentareport.gui.views.general_patient_information.GeneralPatientInformationPresenter;
import de.dentareport.gui.views.general_patient_information.GeneralPatientInformationView;
import de.dentareport.gui.views.import_data.ImportDataPresenter;
import de.dentareport.gui.views.import_data.ImportDataView;
import de.dentareport.gui.views.start.StartPresenter;
import de.dentareport.gui.views.start.StartView;
import de.dentareport.gui.views.telescopic_crowns.TelescopicCrownsPresenter;
import de.dentareport.gui.views.telescopic_crowns.TelescopicCrownsView;

import javax.swing.*;

public class ViewFactory {

    public JComponent create(ViewId viewId, UiController uiController) {
        return switch (viewId) {
            case COPY_FILES -> new CopyFilesView(new CopyFilesPresenter(uiController));
            case EVALUATION -> new EvaluationView(new EvaluationPresenter(uiController));
            case FILLINGS -> new FillingsView(new FillingsPresenter(uiController));
            case GENERAL_PATIENT_INFORMATION ->
                    new GeneralPatientInformationView(new GeneralPatientInformationPresenter(uiController));
            case IMPORT_DATA -> new ImportDataView(new ImportDataPresenter(uiController));
            case START -> new StartView(new StartPresenter(uiController));
            case TELESCOPIC_CROWNS -> new TelescopicCrownsView(new TelescopicCrownsPresenter(uiController));
        };
    }
}
