package de.dentareport.gui.table_models;

import de.dentareport.utils.Keys;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GeneralInformationFillingsCountAndDistribution extends AbstractTableModel {

    private final List<TableRowGeneralInformationFillingsCountAndDistribution> tableRowGeneralPatientInformationAverages;

    private final String[] columns = {
            Keys.GUI_TEXT_ITEM,
            Keys.GUI_TEXT_BLANK
    };

    public GeneralInformationFillingsCountAndDistribution(List<TableRowGeneralInformationFillingsCountAndDistribution> tableRowGeneralInformationFillingsCountAndDistributions) {
        this.tableRowGeneralPatientInformationAverages =
                tableRowGeneralInformationFillingsCountAndDistributions;
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
        TableRowGeneralInformationFillingsCountAndDistribution tableRow =
                tableRowGeneralPatientInformationAverages.get(row);

        return switch (column) {
            case 0 -> tableRow.getRowDescription();
            case 1 -> tableRow.getValue();
            default -> null;
        };
    }
}

