package de.dentareport;

import org.junit.jupiter.api.BeforeEach;

import static de.dentareport.testutil.SwingTestHelper.callOnEdt;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: Remove later, this is just an example. Or re-use it
abstract class SwingTest {

    @BeforeEach
    void ensureEdt() throws Exception {
        assertTrue(callOnEdt(() -> true));
    }
}
