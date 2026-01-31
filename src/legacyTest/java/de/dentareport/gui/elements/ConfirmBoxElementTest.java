package de.dentareport.gui.elements;

import de.dentareport.gui.BaseFxWindowTest;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ConfirmBoxElementTest extends BaseFxWindowTest {

    @Override
    public void start(Stage stage) throws Exception {
        new ConfirmBoxElement().title("some title").text("some text").prepareConfirmBox().show();
    }

    // TODO: Fix test
//    @Test
//    public void it_creates_confirm_box() {
//        assertThat(this.<Node>find("some text")).isInstanceOf(Label.class);
//    }

    // TODO: Fix test
//    @Test
//    public void it_confirms_message() {
//        clickOn(Keys.GUI_TEXT_YES);
//        assertThat(ConfirmBoxElement.answer()).isTrue();
//    }

// TODO: Fix test
//    @Test
//    public void it_discards_message() {
//        clickOn(Keys.GUI_TEXT_NO);
//        assertThat(ConfirmBoxElement.answer()).isFalse();
//    }
}