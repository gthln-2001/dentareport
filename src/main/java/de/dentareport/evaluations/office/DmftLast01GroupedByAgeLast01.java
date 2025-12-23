package de.dentareport.evaluations.office;

import de.dentareport.utils.Keys;

public class DmftLast01GroupedByAgeLast01 implements ItemGroupedByAge {

    @Override
    public String item() {
        return "average_dmft_grouped_by_age_last_01";
    }

    @Override
    public String name() {
        return Keys.TEXT_AVERAGE_DMFT_GROUPED_BY_AGE_LAST_01;
    }

    @Override
    public String unit() {
        return Keys.UNIT_DMFT;
    }

    @Override
    public String query() {
        return String.format("SELECT " +
                        "patients.group_age_last_01, " +
                        "ROUND(AVG(left.%1$s + 0), 2) AS value " +
                        "FROM %2$s AS left INNER JOIN ( " +
                        "SELECT patient_index, MAX(date) AS latest " +
                        "FROM %2$s " +
                        "GROUP BY patient_index " +
                        ") AS right " +
                        "ON left.date = right.latest AND left.patient_index = right.patient_index " +
                        "JOIN patients " +
                        "ON left.patient_index = patients.patient_index " +
                        "WHERE patients.group_age_last_01 != '%3$s' " +
                        "GROUP BY patients.group_age_last_01",
                "dmft",
                "evidences_01",
                Keys.NO_DATA);
    }
}
