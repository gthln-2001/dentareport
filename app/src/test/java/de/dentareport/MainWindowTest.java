package de.dentareport;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class MainWindowTest {

    @Test
    void mainWindowHasCorrectLabel() throws Exception {
        MainWindow[] windowHolder = new MainWindow[1];

        SwingUtilities.invokeAndWait(() -> {
            windowHolder[0] = new MainWindow();
            windowHolder[0].show();
        });

        MainWindow window = windowHolder[0];

        assertNotNull(window.getFrame(), "Frame should exist.");
        assertEquals("Hello FlatLaf", window.getFrame().getTitle(), "Frame title does not match.");
        assertTrue(window.getFrame().isVisible(), "Frame should be visible.");

        assertNotNull(window.getLabel(), "Label should exist.");
        assertEquals("Hello World!", window.getLabel().getText(), "Label text does not match.");

        window.getFrame().dispose();
    }
}
