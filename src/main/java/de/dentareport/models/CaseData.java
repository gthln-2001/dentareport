package de.dentareport.models;

import java.util.HashMap;
import java.util.Map;

public class CaseData {

    private final Map<String, String> strings = new HashMap<>();
    private final Map<String, EventInterface> events = new HashMap<>();
    private final Map<String, Eventlist> eventlists = new HashMap<>();
    private final String tooth;

    public CaseData(String tooth) {
        this.tooth = tooth;
    }

    public String tooth() {
        return tooth;
    }

    public String string(String key) {
        if (!strings.containsKey(key)) {
            throw new IllegalArgumentException(key);
        }
        return strings.get(key);
    }

    public void setString(String key, String value) {
        strings.put(key, value);
    }

    public EventInterface event(String key) {
        if (!events.containsKey(key)) {
            throw new IllegalArgumentException(key);
        }
        return events.get(key);
    }

    public String dateOfEvent(String key) {
        return event(key).date();
    }

    public String endstaendigkeitOfEvent(String key, String tooth) {
        return event(key).endstaendigkeit(tooth);
    }

    public String toothcontactsOfEvent(String key, String tooth) {
        return event(key).toothcontacts(tooth);
    }

    public String toothcountInJawOfEvent(String key, String tooth) {
        return event(key).toothcountInJaw(tooth);
    }

    public String insuranceOfEvent(String key) {
        return event(key).insurance();
    }

    public void setEvent(String key, EventInterface value) {
        events.put(key, value);
    }

    public Eventlist eventlist(String key) {
        if (!eventlists.containsKey(key)) {
            throw new IllegalArgumentException(key);
        }
        return eventlists.get(key);
    }

    public void setEventlist(String key, Eventlist value) {
        eventlists.put(key, value);
    }

    public EventInterface eventFromEventlist(String key, String position) {
        return eventlist(key).event(position);
    }
}
