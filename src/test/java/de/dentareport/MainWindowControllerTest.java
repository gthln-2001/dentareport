package de.dentareport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class MainWindowControllerTest {

    @Test
    void it_updates_the_message_text() {
        MainWindow view = new StubMainWindow();
        MainWindowController controller = new MainWindowController(view);
        assertEquals("Hello World!", view.getMessageText());

        controller.onButtonClicked();

        assertEquals("Button clicked", view.getMessageText());
    }

    static final class StubMainWindow implements MainWindow {
        private String message = "Hello World!";

        @Override
        public String getWindowTitle() {
            fail("Controller must not access window title");
            return null;
        }

        @Override
        public String getMessageText() {
            return message;
        }

        @Override
        public void setMessageText(String text) {
            message = text;
        }

        @Override
        public boolean containsMessage() {
            fail("Controller must not inspect UI structure");
            return false;
        }

        @Override
        public void clickButton() {
            fail("Controller must not trigger UI events");
        }

        @Override
        public void clickResetButton() {
            fail("Controller must not trigger UI events");
        }
    }
}
