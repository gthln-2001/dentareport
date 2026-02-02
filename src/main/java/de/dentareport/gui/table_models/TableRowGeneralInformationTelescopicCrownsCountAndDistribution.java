package de.dentareport.gui.table_models;

public class TableRowGeneralInformationTelescopicCrownsCountAndDistribution {

    private final String rowDescription;
    private final String value;

    public TableRowGeneralInformationTelescopicCrownsCountAndDistribution(String rowDescription, String value) {
        this.rowDescription = rowDescription;
        this.value = value;
    }

    public String getRowDescription() {
        return rowDescription;
    }

    public String getValue() {
        return value;
    }
}

