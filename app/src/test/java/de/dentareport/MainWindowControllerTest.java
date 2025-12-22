package de.dentareport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainWindowControllerTest {

    @Test
    void controllerUpdatesMessage() {
        FakeView view = new FakeView();
        MainWindowController controller = new MainWindowController(view);

        controller.onButtonClicked();

        assertEquals("Button clicked", view.message);
    }

    static class FakeView implements MainWindow {
        String message = "Hello World!";

        @Override
        public String getWindowTitle() {
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
        public boolean isVisible() {
            return false;
        }

        @Override
        public boolean containsMessage() {
            return true;
        }

        @Override
        public void clickButton() {
        }
    }
}
