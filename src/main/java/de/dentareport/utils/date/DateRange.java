package de.dentareport.utils.date;

import static de.dentareport.utils.string.DateStringUtils.reformatSqlToGerman;

public class DateRange {

    private final String start;
    private final String end;

    public DateRange(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String formattedStart() {
        return reformatSqlToGerman(start);
    }

    public String formattedEnd() {
        return reformatSqlToGerman(end);
    }
}
