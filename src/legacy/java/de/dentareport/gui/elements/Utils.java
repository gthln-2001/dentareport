package de.dentareport.gui.elements;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Utils {

    public static void disableButtons(Pane pane) {
        setDisabledStatusForButtons(pane, true);
    }

    public static void enableButtons(Pane pane) {
        setDisabledStatusForButtons(pane, false);
    }

    private static void setDisabledStatusForButtons(Pane pane, boolean status) {
        for (Node component : pane.getChildren()) {
            if (component instanceof Button) {
                component.setDisable(status);
            }
        }
    }
}
