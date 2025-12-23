package de.dentareport.evaluations.office;

import de.dentareport.utils.Keys;

public class DmftLast01 implements ItemAverages {

    @Override
    public String item() {
        return "dmft_last_01";
    }

    @Override
    public String name() {
        return Keys.TEXT_DMFT_LAST_01;
    }

    @Override
    public String unit() {
        return Keys.UNIT_DMFT;
    }

    @Override
    public String query() {
        return String.format("SELECT " +
                        "ROUND(AVG(left.%1$s + 0), 2) AS average, " +
                        "MIN(left.%1$s + 0) AS minimum, " +
                        "MAX(left.%1$s + 0) AS maximum " +
                        "FROM %2$s AS left INNER JOIN ( " +
                        "SELECT patient_index, MAX(date) AS latest " +
                        "FROM %2$s " +
                        "GROUP BY patient_index " +
                        ") AS right " +
                        "ON left.date = right.latest AND left.patient_index = right.patient_index",
                "dmft",
                "evidences_01");
    }

    @Override
    public String queryMedian() {
        return String.format("SELECT AVG(%1$s) as median FROM (" +
                        "SELECT left.%1$s FROM %2$s AS left INNER JOIN ( " +
                        "SELECT patient_index, MAX(date) AS latest " +
                        "FROM %2$s " +
                        "GROUP BY patient_index " +
                        ") AS right " +
                        "ON left.date = right.latest AND left.patient_index = right.patient_index " +
                        "ORDER BY %1$s + 0 " +
                        "LIMIT 2 - (SELECT COUNT(DISTINCT patient_index) FROM %2$s) %% 2 " +
                        "OFFSET (SELECT (COUNT(DISTINCT patient_index) - 1) / 2 FROM %2$s))",
                "dmft",
                "evidences_01");
    }
}
