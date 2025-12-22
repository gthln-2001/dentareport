package de.dentareport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.testutil.SwingTestHelper.callOnEdt;
import static de.dentareport.testutil.SwingTestHelper.runOnEdt;
import static org.junit.jupiter.api.Assertions.*;

class MainWindowImplTest extends SwingTest {

    private MainWindowImpl window;

    @BeforeEach
    void setUp() throws Exception {
        runOnEdt(() -> window = new MainWindowImpl());
    }

    @AfterEach
    void tearDown() throws Exception {
        runOnEdt(window::dispose);
    }

    @Test
    void it_has_the_correct_window_title() throws Exception {
        assertEquals(
                "Hello FlatLaf",
                callOnEdt(window::getWindowTitle),
                "Frame title does not match."
        );
    }

    @Test
    void it_has_the_correct_message_text() {
        assertAll(
                () -> assertTrue(
                        callOnEdt(window::containsMessage),
                        "Frame should have label"),
                () -> assertEquals(
                        "Hello World!",
                        callOnEdt(window::getMessageText),
                        "Label text does not match.")
        );
    }

    @Test
    void it_changes_the_message_text_when_clicking_the_button() throws Exception {
        runOnEdt(window::clickButton);

        assertEquals(
                "Button clicked",
                callOnEdt(window::getMessageText),
                "Clicking the button should update the message"
        );
    }
}
