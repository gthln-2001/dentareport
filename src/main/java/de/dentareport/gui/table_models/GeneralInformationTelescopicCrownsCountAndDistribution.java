package de.dentareport.gui.table_models;

import de.dentareport.utils.Keys;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GeneralInformationTelescopicCrownsCountAndDistribution extends AbstractTableModel {

    private final List<TableRowGeneralInformationTelescopicCrownsCountAndDistribution> tableRowGeneralPatientInformationAverages;

    private final String[] columns = {
            Keys.GUI_TEXT_ITEM,
            Keys.GUI_TEXT_BLANK
    };

    public GeneralInformationTelescopicCrownsCountAndDistribution(List<TableRowGeneralInformationTelescopicCrownsCountAndDistribution> tableRowGeneralInformationTelescopicCrownsCountAndDistributions) {
        this.tableRowGeneralPatientInformationAverages =
                tableRowGeneralInformationTelescopicCrownsCountAndDistributions;
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
        TableRowGeneralInformationTelescopicCrownsCountAndDistribution tableRow =
                tableRowGeneralPatientInformationAverages.get(row);

        return switch (column) {
            case 0 -> tableRow.getRowDescription();
            case 1 -> tableRow.getValue();
            default -> null;
        };
    }
}

