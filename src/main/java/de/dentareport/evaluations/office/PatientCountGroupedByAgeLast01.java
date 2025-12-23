package de.dentareport.evaluations.office;

import de.dentareport.utils.Keys;

public class PatientCountGroupedByAgeLast01 implements ItemGroupedByAge {

    @Override
    public String item() {
        return "patient_count_grouped_by_age_last_01";
    }

    @Override
    public String name() {
        return Keys.TEXT_PATIENT_COUNT_GROUPED_BY_AGE_LAST_01;
    }

    @Override
    public String unit() {
        return Keys.UNIT_NUMBER;
    }

    @Override
    public String query() {
        return String.format(
                "SELECT group_age_last_01, COUNT(group_age_last_01) AS value " +
                        "FROM patients " +
                        "WHERE group_age_last_01 != '%s' " +
                        "GROUP BY group_age_last_01 ",
                Keys.NO_DATA);
    }

    @Override
    public String defaultValueForEmptyGroups() {
        return "0";
    }
}