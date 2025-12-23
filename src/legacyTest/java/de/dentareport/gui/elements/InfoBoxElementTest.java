package de.dentareport.gui.elements;

import de.dentareport.gui.BaseFxWindowTest;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static org.assertj.core.api.Assertions.assertThat;

public class InfoBoxElementTest extends BaseFxWindowTest {

    @Override
    public void start(Stage stage) throws Exception {
        new InfoBoxElement()
                .title("some title")
                .text("some text")
                .prepareInfoBox()
                .show();
    }

    // TODO: Fix test
//    @Test
//    public void it_creates_info_box() {
//        assertThat(this.<Node>find("some text")).isInstanceOf(Label.class);
//        clickOn(Keys.GUI_TEXT_OK);
//    }
}