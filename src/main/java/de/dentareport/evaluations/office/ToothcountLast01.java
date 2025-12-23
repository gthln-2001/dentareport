package de.dentareport.evaluations.office;

import de.dentareport.utils.Keys;

public class ToothcountLast01 implements ItemAverages {

    @Override
    public String item() {
        return "toothcount_last_01";
    }

    @Override
    public String name() {
        return Keys.TEXT_TOOTHCOUNT_LAST_01;
    }

    @Override
    public String unit() {
        return Keys.UNIT_NUMBER;
    }

    @Override
    public String query() {
        return String.format("SELECT " +
                        "ROUND(AVG(%1$s), 2) AS average, " +
                        "MIN(%1$s) AS minimum, " +
                        "MAX(%1$s) AS maximum " +
                        "FROM %2$s AS left INNER JOIN ( " +
                        "SELECT patient_index, MAX(date) AS latest " +
                        "FROM %2$s " +
                        "GROUP BY patient_index " +
                        ") AS right " +
                        "ON left.date = right.latest AND left.patient_index = right.patient_index",
                "(left.tooth_count_q1 + 0) + (left.tooth_count_q2 + 0) + (left.tooth_count_q3 + 0) + (left.tooth_count_q4 + 0)",
                "evidences_01");
    }

    @Override
    public String queryMedian() {
        return String.format("SELECT AVG(toothcount) as median FROM (" +
                        "SELECT %1$s AS toothcount FROM %2$s AS left INNER JOIN ( " +
                        "SELECT patient_index, MAX(date) AS latest " +
                        "FROM %2$s " +
                        "GROUP BY patient_index " +
                        ") AS right " +
                        "ON left.date = right.latest AND left.patient_index = right.patient_index " +
                        "ORDER BY %1$s + 0 " +
                        "LIMIT 2 - (SELECT COUNT(DISTINCT patient_index) FROM %2$s) %% 2 " +
                        "OFFSET (SELECT (COUNT(DISTINCT patient_index) - 1) / 2 FROM %2$s))",
                "(left.tooth_count_q1 + 0) + (left.tooth_count_q2 + 0) + (left.tooth_count_q3 + 0) + (left.tooth_count_q4 + 0)",
                "evidences_01");
    }
}
