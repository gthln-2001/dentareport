package de.dentareport.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventlistTest {

    @Test
    public void it_is_initialized_with_empty_list() {
        Eventlist eventlist = new Eventlist();

        assertThat(eventlist.eventlist()).isEmpty();
    }

    @Test
    public void it_can_add_events_to_list() {
        Eventlist eventlist = new Eventlist();
        eventlist.addEvent(new NullEvent());
        eventlist.addEvent(new NullEvent());

        assertThat(eventlist.eventlist().size()).isEqualTo(2);
    }

    @Test
    public void it_gets_event_by_position() {
        EventInterface event1 = new NullEvent();
        EventInterface event2 = new NullEvent();

        Eventlist eventlist = new Eventlist();
        eventlist.addEvent(event1);
        eventlist.addEvent(event2);

        assertThat(eventlist.event("1")).isEqualTo(event1); // Counting positions starts with 1, not with 0.
        assertThat(eventlist.event("2")).isEqualTo(event2);
    }

    @Test
    public void it_returns_null_event_when_trying_to_access_position_on_empty_list() {
        Eventlist eventlist = new Eventlist();

        assertThat(eventlist.event("42")).isInstanceOf(NullEvent.class);
    }

    @Test
    public void it_returns_null_event_when_trying_to_access_position_that_does_not_exist() {
        Eventlist eventlist = new Eventlist();
        eventlist.addEvent(new NullEvent());
        eventlist.addEvent(new NullEvent());

        assertThat(eventlist.event("3")).isInstanceOf(NullEvent.class);
    }
}