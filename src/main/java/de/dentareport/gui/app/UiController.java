package de.dentareport.gui.app;

import de.dentareport.gui.navigation.ViewId;

public interface UiController {

    void showView(ViewId viewId);

    void showError(String message);

    void showInfo(String message);

    void confirmExit();

    void closeApplication();

    void setWindowTitle(String title);
}

