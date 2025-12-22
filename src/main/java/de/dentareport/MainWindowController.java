package de.dentareport;

import java.util.Objects;

public class MainWindowController {

    private final MainWindow view;

    public MainWindowController(MainWindow view) {
        this.view = Objects.requireNonNull(view, "View must not be null");
        view.setMessageText(UiText.INITIAL_MESSAGE);
    }

    public void onButtonClicked() {
        view.setMessageText(UiText.BUTTON_CLICKED);
    }

    public void onResetClicked() {
        view.setMessageText(UiText.INITIAL_MESSAGE);
    }
}
