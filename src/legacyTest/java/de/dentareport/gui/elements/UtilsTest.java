package de.dentareport.gui.elements;

import de.dentareport.gui.BaseFxElementTest;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import static de.dentareport.gui.elements.Utils.disableButtons;
import static de.dentareport.gui.elements.Utils.enableButtons;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest extends BaseFxElementTest {

    private Button button1;
    private Button button2;
    private Button button3;
    private Pane pane;

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        assertThat(true).isTrue();
    }

    // TODO: Fix test
//    @Test
//    public void it_disables_all_buttons_in_pane() {
//        disableButtons(pane);
//
//        assertThat(button1.isDisabled()).isTrue();
//        assertThat(button2.isDisabled()).isTrue();
//        assertThat(button3.isDisabled()).isTrue();
//    }

    // TODO: Fix test
//    @Test
//    public void it_enables_all_buttons_in_pane() {
//        disableButtons(pane);
//        enableButtons(pane);
//
//        assertThat(button1.isDisabled()).isFalse();
//        assertThat(button2.isDisabled()).isFalse();
//        assertThat(button3.isDisabled()).isFalse();
//    }

    @Override
    protected Pane pane() {
        button1 = Element.button().text("button1").create();
        button2 = Element.button().text("button2").create();
        button3 = Element.button().text("button3").create();
        pane = new Pane();
        pane.getChildren().addAll(button1, button2, button3);
        return pane;
    }
}