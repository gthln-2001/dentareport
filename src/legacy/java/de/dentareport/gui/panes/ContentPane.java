package de.dentareport.gui.panes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import static de.dentareport.gui.elements.Utils.disableButtons;
import static de.dentareport.gui.elements.Utils.enableButtons;

public abstract class ContentPane {

    private final Pane menu;

    public ContentPane() {
        menu = menu();
    }

    public BorderPane pane() {
        BorderPane pane = new BorderPane();
        pane.setCenter(content());
        pane.setBottom(menu);
        return pane;
    }

    public void finished() {
    }

    protected abstract Pane content();

    protected abstract Pane menu();

    protected void enableMenuButtons() {
        enableButtons(menu);
    }

    protected void disableMenuButtons() {
        disableButtons(menu);
    }
}
