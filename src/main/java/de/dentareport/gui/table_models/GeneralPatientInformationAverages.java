package de.dentareport.gui.table_models;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GeneralPatientInformationAverages extends AbstractTableModel {

    private final List<TableRowGeneralPatientInformationAverages> tableRowGeneralPatientInformationAverages;

    private final String[] columns = {
            "First Name",
            "Last Name",
            "Birth Date",
            "Insurance"
    };

    public GeneralPatientInformationAverages(List<TableRowGeneralPatientInformationAverages> tableRowGeneralPatientInformationAverages) {
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
        TableRowGeneralPatientInformationAverages p = tableRowGeneralPatientInformationAverages.get(row);

        return switch (column) {
            case 0 -> p.getFirstName();
            case 1 -> p.getLastName();
            case 2 -> p.getBirthDate();
            case 3 -> p.getInsurance();
            default -> null;
        };
    }
}

