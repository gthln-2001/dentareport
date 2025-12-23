package de.dentareport.gui.elements;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;


import java.util.Objects;

public class RadioButtonElement {

    private final RadioButton radioButton;

    public RadioButtonElement() {
        this.radioButton = new RadioButton();
    }

    public RadioButton create() {
        return radioButton;
    }

    public RadioButtonElement text(String value) {
        radioButton.setText(value);
        return this;
    }

    public RadioButtonElement text(String value, String selected) {
        return text(value).selected(selected);
    }

    public RadioButtonElement group(ToggleGroup value) {
        radioButton.setToggleGroup(value);
        return this;
    }

    private RadioButtonElement selected(String value) {
        if (Objects.equals(value, radioButton.getText())) {
            radioButton.setSelected(true);
        }
        return this;
    }
}
