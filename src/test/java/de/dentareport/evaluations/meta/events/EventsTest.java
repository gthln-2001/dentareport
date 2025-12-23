package de.dentareport.evaluations.meta.events;

import de.dentareport.utils.Keys;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventsTest {

    @Test
    public void it_gets_event() {
        Events events = new Events();

        assertThat(events.event(Keys.GUI_TEXT_TOOTHLOSS)).isInstanceOf(Event.class);
    }
}