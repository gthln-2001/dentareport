package de.dentareport.evaluations.meta.events;

import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AvailableEventsTest {

    @Test
    public void it_gets_list_of_available_events(@Mocked Events mockEvents,
                                                 @Mocked Event mockEvent) {

        new Expectations() {{
            mockEvents.event(anyString);
        }};

        AvailableEvents availableEvents = new AvailableEvents(Keys.EVALUATION_TYPE_TELESCOPIC_CROWN);

        assertThat(availableEvents.events()).isInstanceOf(List.class);
        assertThat(availableEvents.events().get(0)).isInstanceOf(Event.class);
    }
}