package de.dentareport.gui;

import javax.swing.*;

// TODO: Test
public class ViewFactory {

    private final UiController uiController;

    public ViewFactory(UiController uiController) {
        this.uiController = uiController;
    }

    public JComponent create(ViewId viewId  ) {
        return switch (viewId) {
            case START -> new StartView(new StartPresenter(uiController));
            case REPORT -> new JLabel("Report view (TODO)");
            case SETTINGS -> new JLabel("Settings view (TODO)");
        };
    }
}
