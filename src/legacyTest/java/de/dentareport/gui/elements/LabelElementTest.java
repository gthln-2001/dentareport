package de.dentareport.gui.elements;

import de.dentareport.gui.BaseFxElementTest;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LabelElementTest extends BaseFxElementTest {

    private LabelElement labelElement;

    @BeforeEach
    public void setUp() {
        labelElement = new LabelElement();
    }

    @Test
    public void it_creates_label() {
        Label label = labelElement.create();

        assertThat(label).isInstanceOf(Label.class);
        assertThat(label.getTextAlignment()).isEqualTo(TextAlignment.LEFT);
    }

    @Test
    public void it_sets_label_text() {
        Label label = labelElement.text("foo").create();

        assertThat(label.getText()).isEqualTo("foo");
    }

    @Test
    public void it_sets_label_styleclass() {
        Label label = labelElement.styleClass("foo-class").create();

        assertThat(label.getStyleClass()).contains("foo-class");
    }

    @Test
    public void it_sets_label_alignment() {
        Label label = labelElement.alignment(TextAlignment.JUSTIFY).create();

        assertThat(label.getTextAlignment()).isEqualTo(TextAlignment.JUSTIFY);
    }

    @Test
    public void it_can_chain_multiple_values() {
        Label label = labelElement
                .alignment(TextAlignment.CENTER)
                .styleClass("some-class")
                .text("some-text")
                .create();

        assertThat(label.getTextAlignment()).isEqualTo(TextAlignment.CENTER);
        assertThat(label.getStyleClass()).contains("some-class");
        assertThat(label.getText()).isEqualTo("some-text");
    }
}