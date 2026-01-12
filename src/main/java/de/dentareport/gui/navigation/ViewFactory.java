package de.dentareport.gui.navigation;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.views.evaluation.EvaluationPresenter;
import de.dentareport.gui.views.evaluation.EvaluationView;
import de.dentareport.gui.views.import_data.ImportDataPresenter;
import de.dentareport.gui.views.import_data.ImportDataView;
import de.dentareport.gui.views.start.StartPresenter;
import de.dentareport.gui.views.start.StartView;

import javax.swing.*;

public class ViewFactory {

    public JComponent create(ViewId viewId, UiController uiController) {
        return switch (viewId) {
            case START -> new StartView(new StartPresenter(uiController));
            case IMPORT_DATA -> new ImportDataView(new ImportDataPresenter(uiController));
            case EVALUATION -> new EvaluationView(new EvaluationPresenter(uiController));
        };
    }
}
