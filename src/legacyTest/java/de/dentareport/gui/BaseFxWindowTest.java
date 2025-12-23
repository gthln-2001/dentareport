package de.dentareport.gui;

import de.dentareport.utils.Keys;
import javafx.scene.layout.Pane;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

public abstract class BaseFxWindowTest extends BaseFxTest {

    @Test
    public void it_has_quit_button() {
        if (shouldHaveQuitButton()) {
            clickOn(Keys.GUI_TEXT_QUIT);
            new Verifications() {{
                gui().closeProgram();
                times = 1;
            }};
        }
    }

    @Test
    public void it_has_back_button() {
        if (backButtonTarget() != null) {
            clickOn(Keys.GUI_TEXT_BACK);
            new Verifications() {{
                gui().setContentPane(backButtonTarget());
                times = 1;
            }};
        }
    }

    @Override
    protected Pane pane() {
        return null;
    }
}
