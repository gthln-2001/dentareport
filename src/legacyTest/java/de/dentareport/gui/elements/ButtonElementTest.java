package de.dentareport.gui.elements;

import de.dentareport.gui.BaseFxElementTest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ButtonElementTest extends BaseFxElementTest {

    private ButtonElement buttonElement;

    @BeforeEach
    public void setUp() {
        buttonElement = new ButtonElement();
    }

    @Test
    public void it_creates_button() {
        Button button = buttonElement.create();

        assertThat(button).isInstanceOf(Button.class);
        assertThat(button.isDisable()).isFalse();
    }

    @Test
    public void it_sets_button_text() {
        Button button = buttonElement.text("foo").create();

        assertThat(button.getText()).isEqualTo("foo");
    }

    @Test
    public void it_sets_button_action() {
        EventHandler<ActionEvent> action = e -> {
        };
        Button button = buttonElement.action(action).create();

        assertThat(button.getOnAction()).isEqualTo(action);
    }

    @Test
    public void it_sets_button_styleclass() {
        Button button = buttonElement.styleClass("foo-class").create();

        assertThat(button.getStyleClass()).contains("foo-class");
    }

    @Test
    public void it_sets_button_width() {
        Button button = buttonElement.width(123).create();

        assertThat(button.getPrefWidth()).isEqualTo(123);
    }

    @Test
    public void it_sets_button_id() {
        Button button = buttonElement.id("foo-id").create();

        assertThat(button.getId()).isEqualTo("foo-id");
    }

    @Test
    public void it_disables_button() {
        Button button = buttonElement.disabled().create();

        assertThat(button.isDisable()).isTrue();
    }

    @Test
    public void it_can_chain_multiple_values() {
        EventHandler<ActionEvent> action = e -> {
        };
        Button button = buttonElement
                .text("some-text")
                .id("some-id")
                .action(action)
                .styleClass("some-class")
                .width(567)
                .disabled()
                .create();

        assertThat(button.getText()).isEqualTo("some-text");
        assertThat(button.getId()).isEqualTo("some-id");
        assertThat(button.getOnAction()).isEqualTo(action);
        assertThat(button.getStyleClass()).contains("some-class");
        assertThat(button.getPrefWidth()).isEqualTo(567);
        assertThat(button.isDisable()).isTrue();
    }
}