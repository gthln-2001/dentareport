package de.dentareport.evaluations.meta.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class EventTest {

    private Event event;

    @BeforeEach
    public void setUp() {
        event = new Event("name_of_event", "name_of_censored_column", "name_of_interval_column");
    }

    @Test
    public void it_has_name() {
        assertThat(event.name()).isEqualTo("name_of_event");
    }

    @Test
    public void it_has_censored_column() {
        assertThat(event.censoredColumn()).isEqualTo("name_of_censored_column");
    }

    @Test
    public void it_has_interval_column() {
        assertThat(event.intervalColumn()).isEqualTo("name_of_interval_column");
    }
}