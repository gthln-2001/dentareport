package de.dentareport.gui.table_models;

import de.dentareport.utils.Keys;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GeneralPatientInformationGroupedByAge extends AbstractTableModel {

    private final List<TableRowGeneralPatientInformationGroupedByAge> tableRowsGeneralPatientInformationGroupedByAge;

    private final String[] columns = {
            Keys.GUI_TEXT_BLANK,
            Keys.GUI_TEXT_BLANK,
            "0-19",
            "20-29",
            "30-39",
            "40-49",
            "50-59",
            "60-69",
            "70-79",
            "80-89",
            "90-99"
    };

    public GeneralPatientInformationGroupedByAge(
            List<TableRowGeneralPatientInformationGroupedByAge> tableRowGeneralPatientInformationGroupedByAge) {
        this.tableRowsGeneralPatientInformationGroupedByAge = tableRowGeneralPatientInformationGroupedByAge;
    }

    @Override
    public int getRowCount() {
        return tableRowsGeneralPatientInformationGroupedByAge.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        TableRowGeneralPatientInformationGroupedByAge tableRow = tableRowsGeneralPatientInformationGroupedByAge.get(row);

        return switch (column) {
            case 0 -> tableRow.getRowDescription();
            case 1 -> tableRow.getUnit();
            case 2 -> tableRow.getAgeGroup1();
            case 3 -> tableRow.getAgeGroup2();
            case 4 -> tableRow.getAgeGroup3();
            case 5 -> tableRow.getAgeGroup4();
            case 6 -> tableRow.getAgeGroup5();
            case 7 -> tableRow.getAgeGroup6();
            case 8 -> tableRow.getAgeGroup7();
            case 9 -> tableRow.getAgeGroup8();
            case 10 -> tableRow.getAgeGroup9();
            default -> null;
        };
    }
}

