package de.dentareport.gui.elements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonElement {

    private final Button button;

    public ButtonElement() {
        this.button = new Button();
    }

    public Button create() {
        return button;
    }

    public ButtonElement action(EventHandler<ActionEvent> value) {
        button.setOnAction(value);
        return this;
    }

    public ButtonElement disabled() {
        button.setDisable(true);
        return this;
    }

    public ButtonElement id(String value) {
        button.setId(value);
        return this;
    }

    public ButtonElement styleClass(String value) {
        button.getStyleClass().add(value);
        return this;
    }

    public ButtonElement text(String value) {
        button.setText(value);
        return this;
    }

    public ButtonElement width(int value) {
        button.setPrefWidth(value);
        return this;
    }
}
