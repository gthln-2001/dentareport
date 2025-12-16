package de.dentareport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DentareportTest {
    @Test void appHasAGreeting() {
        Dentareport classUnderTest = new Dentareport();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
