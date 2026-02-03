package de.dentareport.gui.table_models;

import de.dentareport.utils.Keys;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableAfr extends AbstractTableModel {

    private final List<TableRowAfr> tableRowAfr;

    private final String[] columns = {
            Keys.GUI_TEXT_ITEM,
            Keys.GUI_TEXT_AFR_5_YEARS,
            Keys.GUI_TEXT_AFR_10_YEARS
    };

    public TableAfr(List<TableRowAfr> tableRowAfr) {
        this.tableRowAfr = tableRowAfr;
    }

    @Override
    public int getRowCount() {
        return tableRowAfr.size();
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
        TableRowAfr tableRow = tableRowAfr.get(row);

        return switch (column) {
            case 0 -> tableRow.getItem();
            case 1 -> tableRow.getAfr5();
            case 2 -> tableRow.getAfr10();
            default -> null;
        };
    }
}

