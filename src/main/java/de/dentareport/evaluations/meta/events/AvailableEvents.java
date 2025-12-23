package de.dentareport.evaluations.meta.events;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;

import java.util.List;
import java.util.Map;

public class AvailableEvents {

    private final Events events;
    private String evaluationType;

    public AvailableEvents(String evaluationType) {
        this.evaluationType = evaluationType;
        this.events = new Events();
    }

    public List<Event> events() {
        return availableEvents().get(evaluationType);
    }

    private Map<String, List<Event>> availableEvents() {
        return ImmutableMap.of(
                Keys.EVALUATION_TYPE_FILLING, ImmutableList.of(
                        events.event(Keys.GUI_TEXT_TOOTHLOSS),
                        events.event(Keys.GUI_TEXT_REZEMENTIERUNG),
                        events.event(Keys.GUI_TEXT_ENDODONTICS),
                        events.event(Keys.GUI_TEXT_WURZELSTIFT)
                ),
                Keys.EVALUATION_TYPE_TELESCOPIC_CROWN, ImmutableList.of(
                        events.event(Keys.GUI_TEXT_TOOTHLOSS),
                        events.event(Keys.GUI_TEXT_REZEMENTIERUNG),
                        events.event(Keys.GUI_TEXT_ENDODONTICS),
                        events.event(Keys.GUI_TEXT_WURZELSTIFT)
                )
        );
    }
}
