package de.dentareport.gui.elements;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

// TODO: TEST?
public class TableElement {

    private ObservableList<TableRow> data;
    private List<String> columns = new ArrayList<>();
    private List<Integer> columnWidth = new ArrayList<>();
    private int height = 160;
    private String id;

    public TableView<TableRow> create() {
        TableView<TableRow> table = new TableView<>(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setSelectionModel(null);
        table.setMaxWidth(960);
        table.setPrefHeight(height);
        table.setId(id);
        for (int i = 0; i < columns.size(); i++) {
            table.getColumns().add(column(i));
        }
        return table;
    }

    public TableElement data(ObservableList<TableRow> value) {
        data = value;
        return this;
    }

    public TableElement columns(List<String> value) {
        columns = value;
        return this;
    }

    public TableElement columnWidth(List<Integer> value) {
        columnWidth = value;
        return this;
    }

    public TableElement height(int value) {
        height = value;
        return this;
    }

    public TableElement id(String value) {
        id = value;
        return this;
    }

    private TableColumn<TableRow, String> column(int i) {
        TableColumn<TableRow, String> column = new TableColumn<>(columns.get(i));
        column.setMinWidth(minColumnWidth(i));
        column.setSortable(false);
        column.setCellValueFactory(new PropertyValueFactory<>("column" + String.valueOf(i + 1)));
        return column;
    }

    private double minColumnWidth(int i) {
        if (columnWidth.size() < i + 1) {
            return 100;
        }
        return columnWidth.get(i);
    }
}
