package de.dentareport.gui.table_models;

import de.dentareport.utils.Keys;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableGroupSizes extends AbstractTableModel {

    private final List<TableRowGroupSizes> tableRowGroupSizes;

    private final String[] columns = {
            Keys.GUI_TEXT_ITEM,
            Keys.GUI_TEXT_COUNT
    };

    public TableGroupSizes(List<TableRowGroupSizes> tableRowGroupSizes) {
        this.tableRowGroupSizes = tableRowGroupSizes;
    }

    @Override
    public int getRowCount() {
        return tableRowGroupSizes.size();
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
        TableRowGroupSizes tableRow = tableRowGroupSizes.get(row);

        return switch (column) {
            case 0 -> tableRow.getItem();
            case 1 -> tableRow.getItemCount();
            default -> null;
        };
    }
}

