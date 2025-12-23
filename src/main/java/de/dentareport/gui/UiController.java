package de.dentareport.gui;

public interface UiController {

    void showView(ViewId viewId);

    void showError(String message);

    void confirmExit();

    void closeApplication();

    void setWindowTitle(String title);
}

