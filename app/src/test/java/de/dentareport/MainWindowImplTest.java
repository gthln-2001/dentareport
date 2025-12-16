package de.dentareport;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class MainWindowImplTest {

    @Test
    void mainWindowHasCorrectLabel() throws Exception {
        MainWindowImpl[] windowHolder = new MainWindowImpl[1];

        SwingUtilities.invokeAndWait(() -> {
            windowHolder[0] = new MainWindowImpl();
            windowHolder[0].show();
        });

        MainWindowImpl window = windowHolder[0];

        assertNotNull(window, "MainWindow must exist");
        assertTrue(window.isVisible(), "Frame should be visible.");
        assertEquals("Hello FlatLaf", window.getWindowTitle(), "Frame title does not match.");
        assertTrue(window.containsMessage(), "Frame should have label");
        assertEquals("Hello World!", window.getMessageText(), "Label text does not match.");

        window.dispose();
    }
}
