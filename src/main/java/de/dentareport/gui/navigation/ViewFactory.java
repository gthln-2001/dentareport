package de.dentareport.gui.navigation;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.views.start.StartPresenter;
import de.dentareport.gui.views.start.StartView;

import javax.swing.*;

// TODO: Test
public class ViewFactory {

    public JComponent create(ViewId viewId, UiController uiController) {
        return switch (viewId) {
            case START -> new StartView(new StartPresenter(uiController));
            case REPORT -> new JLabel("Report view (TODO)");
            case SETTINGS -> new JLabel("Settings view (TODO)");
        };
    }
}
