package de.dentareport.gui.elements;

import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class LabelElement {

    private final Label label;

    public LabelElement() {
        this.label = new Label();
    }

    public Label create() {
        return label;
    }

    public LabelElement alignment(TextAlignment value) {
        label.setTextAlignment(value);
        return this;
    }

    public LabelElement styleClass(String value) {
        label.getStyleClass().add(value);
        return this;
    }

    public LabelElement text(String value) {
        label.setText(value);
        return this;
    }
}
