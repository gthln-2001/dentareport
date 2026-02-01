package de.dentareport.gui.table_models;

public class TableRowGeneralPatientInformationGroupedByAge {

    private final String rowDescription;
    private final String unit;
    private final String ageGroup1;
    private final String ageGroup2;
    private final String ageGroup3;
    private final String ageGroup4;
    private final String ageGroup5;
    private final String ageGroup6;
    private final String ageGroup7;
    private final String ageGroup8;
    private final String ageGroup9;

    public TableRowGeneralPatientInformationGroupedByAge(String rowDescription, String unit, String ageGroup1,
                                                         String ageGRoup2, String ageGroup3, String ageGroup4,
                                                         String ageGroup5, String ageGroup6, String ageGroup7,
                                                         String ageGroup8, String ageGroup9) {
        this.rowDescription = rowDescription;
        this.unit = unit;
        this.ageGroup1 = ageGroup1;
        this.ageGroup2 = ageGRoup2;
        this.ageGroup3 = ageGroup3;
        this.ageGroup4 = ageGroup4;
        this.ageGroup5 = ageGroup5;
        this.ageGroup6 = ageGroup6;
        this.ageGroup7 = ageGroup7;
        this.ageGroup8 = ageGroup8;
        this.ageGroup9 = ageGroup9;
    }

    public String getRowDescription() {
        return rowDescription;
    }

    public String getUnit() {
        return unit;
    }

    public String getAgeGroup1() {
        return ageGroup1;
    }

    public String getAgeGroup2() {
        return ageGroup2;
    }

    public String getAgeGroup3() {
        return ageGroup3;
    }

    public String getAgeGroup4() {
        return ageGroup4;
    }

    public String getAgeGroup5() {
        return ageGroup5;
    }

    public String getAgeGroup6() {
        return ageGroup6;
    }

    public String getAgeGroup7() {
        return ageGroup7;
    }

    public String getAgeGroup8() {
        return ageGroup8;
    }

    public String getAgeGroup9() {
        return ageGroup9;
    }


}

