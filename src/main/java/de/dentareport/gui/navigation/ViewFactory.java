package de.dentareport.gui.navigation;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.views.start.StartPresenter;
import de.dentareport.gui.views.start.StartView;

import javax.swing.*;

public class ViewFactory {

    public JComponent create(ViewId viewId, UiController uiController) {
        return switch (viewId) {
            case START -> new StartView(new StartPresenter(uiController));
            case IMPORT_DATA -> new JLabel("Import Data view (TODO)");
            case EVALUATION -> new JLabel("Evaluation view (TODO)");
        };
    }
}
