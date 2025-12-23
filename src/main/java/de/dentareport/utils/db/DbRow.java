package de.dentareport.utils.db;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbRow {

    private List<DbCell> cells;

    public DbRow() {
        this.cells = new ArrayList<>();
    }

    public static DbRow copy(DbRow origin) {
        DbRow newRow = new DbRow();
        copyCells(origin, newRow);
        return newRow;
    }

    public void addCell(DbCell cell) {
        cells.add(cell);
    }

    public void addCells(List<DbCell> newCells) {
        cells.addAll(newCells);
    }

    public List<DbCell> cells() {
        return cells;
    }

    public List<String> columnnames() {
        return cells.stream()
                .map(DbCell::column)
                .collect(Collectors.toList());
    }

    public String value(String column) {
        for (DbCell cell : cells) {
            if (cell.column().equals(column)) {
                return cell.value();
            }
        }
        throw new IllegalArgumentException(column);
    }

    public void setValue(String column,
                         String value) {
        for (DbCell cell : cells) {
            if (cell.column().equals(column)) {
                cell.setValue(value);
                return;
            }
        }
        throw new IllegalArgumentException(column);
    }

    public boolean hasCell(String cellName) {
        for (DbCell cell : cells) {
            if (cell.column().equals(cellName)) {
                return true;
            }
        }
        return false;
    }

    public void removeCell(String cellName) {
        cells.removeIf(cell -> cell.column().equals(cellName));
    }

    private static void copyCells(DbRow origin,
                                  DbRow target) {
        for (DbCell cell : origin.cells()) {
            target.addCell(new DbCell(cell));
        }
    }
}
