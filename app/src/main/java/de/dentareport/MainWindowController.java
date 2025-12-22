package de.dentareport;

import java.util.Objects;

public class MainWindowController {

    private final MainWindow view;

    public MainWindowController(MainWindow view) {
        this.view = Objects.requireNonNull(view, "view must not be null");
    }

    public void onButtonClicked() {
        view.setMessageText("Button clicked");
    }
}
