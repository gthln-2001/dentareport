package de.dentareport.evaluations.office;

import de.dentareport.utils.Keys;

public class AgeLast01 implements ItemAverages {

    @Override
    public String item() {
        return "age_last_01";
    }

    @Override
    public String name() {
        return Keys.TEXT_AGE_LAST_01;
    }

    @Override
    public String unit() {
        return Keys.UNIT_YEARS;
    }

    @Override
    public String query() {
        return String.format("SELECT " +
                        "ROUND(AVG(%1$s + 0), 2) AS average, " +
                        "MIN(%1$s + 0) AS minimum, " +
                        "MAX(%1$s + 0) AS maximum " +
                        "FROM %2$s " +
                        "WHERE %1$s !='%3$s'",
                "age_last_01",
                "patients",
                Keys.NO_DATA);
    }

    @Override
    public String queryMedian() {
        return String.format("SELECT AVG(%1$s + 0) as median FROM (" +
                        "SELECT %1$s FROM %2$s " +
                        "WHERE %1$s != '%3$s' " +
                        "ORDER BY (%1$s + 0) " +
                        "LIMIT 2 - (SELECT COUNT(*) FROM %2$s WHERE %1$s != '%3$s') %% 2 " +
                        "OFFSET (SELECT (COUNT(*) - 1) / 2 FROM %2$s WHERE %1$s != '%3$s'))",
                "age_last_01",
                "patients",
                Keys.NO_DATA);
    }
}
