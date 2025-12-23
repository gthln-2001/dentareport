package de.dentareport.utils.dbf;

import java.util.ArrayList;
import java.util.List;

public class DbfRow {

    private List<DbfCell> cells;
    private boolean deleted = false;

    public DbfRow() {
        this.cells = new ArrayList<>();
    }

    public void addCell(DbfCell cell) {
        cells.add(cell);
    }

    public String value(String column) {
        for (DbfCell cell : cells) {
            if (cell.column().equals(column)) {
                return cell.value();
            }
        }
        throw new IllegalArgumentException(column);
    }

    public void setValue(String column, String value) {
        for (DbfCell cell : cells) {
            if (cell.column().equals(column)) {
                cell.setValue(value);
                return;
            }
        }
        throw new IllegalArgumentException(column);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void markDeleted() {
        deleted = true;
    }
}
