package de.dentareport.gui.table_models;

public class TableRowGeneralPatientInformationAverages {

    private final String rowDescription;
    private final String unit;
    private final String average;
    private final String median;
    private final String minimum;
    private final String maximum;

    public TableRowGeneralPatientInformationAverages(String rowDescription, String unit, String average,
                                                     String median, String minimum, String maximum) {
        this.rowDescription = rowDescription;
        this.unit = unit;
        this.average = average;
        this.median = median;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public String getRowDescription() {
        return rowDescription;
    }

    public String getUnit() {
        return unit;
    }

    public String getAverage() {
        return average;
    }

    public String getMedian() {
        return median;
    }

    public String getMinimum() {
        return minimum;
    }

    public String getMaximum() {
        return maximum;
    }
}

