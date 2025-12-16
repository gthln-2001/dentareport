package de.dentareport;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DentareportTest {

    @Test
    void guiCanBeCreated() throws Exception {
        JFrame[] frameHolder = new JFrame[1];

        SwingUtilities.invokeAndWait(() -> {
            frameHolder[0] = Dentareport.createAndShowGui();
        });

        JFrame frame = frameHolder[0];

        assertNotNull(frame, "Frame should exist.");
        assertEquals("Hello FlatLaf", frame.getTitle(), "Frame title does not match.");
        assertTrue(frame.isVisible(), "Frame should be visible.");

        frame.dispose();
    }

    @Test
    void guiContainsHelloWorldLabel() throws Exception {
        JFrame[] frameHolder = new JFrame[1];

        SwingUtilities.invokeAndWait(() -> {
            frameHolder[0] = Dentareport.createAndShowGui();
        });

        JFrame frame = frameHolder[0];
        JLabel label = findLabel(frame);

        assertNotNull(label, "Label should exist.");
        assertEquals("Hello World!", label.getText(), "Label text does not match.");

        frame.dispose();
    }

    private static JLabel findLabel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel label) {
                return label;
            }
            if (component instanceof Container child) {
                JLabel result = findLabel(child);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
