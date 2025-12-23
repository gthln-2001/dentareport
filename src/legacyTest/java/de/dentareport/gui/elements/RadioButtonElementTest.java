package de.dentareport.gui.elements;

import de.dentareport.gui.BaseFxElementTest;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RadioButtonElementTest extends BaseFxElementTest {

    private RadioButtonElement radioButtonElement;

    @BeforeEach
    public void setUp() {
        radioButtonElement = new RadioButtonElement();
    }

    @Test
    public void it_creates_radio_button() {
        RadioButton radioButton = radioButtonElement.create();

        assertThat(radioButton).isInstanceOf(RadioButton.class);
        assertThat(radioButton.isSelected()).isFalse();
    }

    @Test
    public void it_sets_radio_button_text() {
        RadioButton radioButton = radioButtonElement.text("foobar").create();

        assertThat(radioButton.getText()).isEqualTo("foobar");
    }

    @Test
    public void it_sets_radio_button_toggle_group() {
        ToggleGroup someGroup = new ToggleGroup();
        RadioButton radioButton = radioButtonElement.group(someGroup).create();

        assertThat(radioButton.getToggleGroup()).isEqualTo(someGroup);
    }

    @Test
    public void it_selects_radio_button_if_text_matches() {
        RadioButton radioButton = radioButtonElement.text("foo", "foo").create();

        assertThat(radioButton.isSelected()).isTrue();
    }

    @Test
    public void it_does_not_select_radio_button_if_text_does_not_match() {
        RadioButton radioButton = radioButtonElement.text("foo", "bar").create();

        assertThat(radioButton.isSelected()).isFalse();
    }

    @Test
    public void it_can_chain_multiple_values() {
        ToggleGroup fooGroup = new ToggleGroup();
        RadioButton radioButton = radioButtonElement
                .group(fooGroup)
                .text("biz", "biz")
                .create();

        assertThat(radioButton.getToggleGroup()).isEqualTo(fooGroup);
        assertThat(radioButton.getText()).isEqualTo("biz");
        assertThat(radioButton.isSelected()).isTrue();
    }
}