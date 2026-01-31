package de.dentareport.models;

import java.util.ArrayList;
import java.util.List;

// TODO: TEST?
public class Eventlist {

    private List<EventInterface> eventlist = new ArrayList<>();

    public EventInterface event(String position) {
        Integer key = Integer.valueOf(position) - 1;
        if (eventlist.size() <= key) {
            return new NullEvent();
        }
        return eventlist.get(key);
    }

    public List<EventInterface> eventlist() {
        return eventlist;
    }

    public void addEvent(EventInterface event) {
        eventlist.add(event);
    }
}