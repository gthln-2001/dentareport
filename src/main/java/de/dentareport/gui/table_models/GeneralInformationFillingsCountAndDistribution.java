package de.dentareport.gui.table_models;

import de.dentareport.utils.Keys;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GeneralInformationFillingsCountAndDistribution extends AbstractTableModel {

    private final List<TableRowGeneralPatientInformationAverages> tableRowGeneralPatientInformationAverages;

    private final String[] columns = {
            Keys.GUI_TEXT_BLANK,
            Keys.GUI_TEXT_BLANK,
            Keys.GUI_TEXT_AVERAGE,
            Keys.GUI_TEXT_MEDIAN,
            Keys.GUI_TEXT_MINIMUM,
            Keys.GUI_TEXT_MAXIMUM
    };

    public GeneralInformationFillingsCountAndDistribution(List<TableRowGeneralPatientInformationAverages> tableRowGeneralPatientInformationAverages) {
        this.tableRowGeneralPatientInformationAverages = tableRowGeneralPatientInformationAverages;
    }

    @Override
    public int getRowCount() {
        return tableRowGeneralPatientInformationAverages.size();
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
        TableRowGeneralPatientInformationAverages tableRow = tableRowGeneralPatientInformationAverages.get(row);

        return switch (column) {
            case 0 -> tableRow.getRowDescription();
            case 1 -> tableRow.getUnit();
            case 2 -> tableRow.getAverage();
            case 3 -> tableRow.getMedian();
            case 4 -> tableRow.getMinimum();
            case 5 -> tableRow.getMaximum();
            default -> null;
        };
    }
}

