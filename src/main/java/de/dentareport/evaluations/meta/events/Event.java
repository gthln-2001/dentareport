package de.dentareport.evaluations.meta.events;

// TODO: TEST?
public class Event {

    private String name;
    private String censoredColumn;
    private String intervalColumn;

    public Event(String name,
                 String censoredColumn,
                 String intervalColumn) {
        this.name = name;
        this.censoredColumn = censoredColumn;
        this.intervalColumn = intervalColumn;
    }

    public String name() {
        return name;
    }

    public String censoredColumn() {
        return censoredColumn;
    }

    public String intervalColumn() {
        return intervalColumn;
    }
}