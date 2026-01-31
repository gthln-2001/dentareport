package de.dentareport.gui.elements;

import com.google.common.collect.ImmutableList;
import de.dentareport.gui.BaseFxElementTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class TableElementTest extends BaseFxElementTest {

    private TableElement tableElement;

    @BeforeEach
    public void setUp() {
        tableElement = new TableElement();
    }

    @Test
    public void it_creates_table_view() {
        TableView<TableRow> table = tableElement.create();

        assertThat(table).isInstanceOf(TableView.class);
    }

    @Test
    public void it_sets_table_data() {
        ObservableList<TableRow> data = FXCollections.observableArrayList();

        TableView<TableRow> table = tableElement.data(data).create();

        assertThat(table.getItems()).isEqualTo(data);
    }

    @Test
    public void it_sets_table_columns() {
        List<String> columns = ImmutableList.of("c1", "c2");

        TableView<TableRow> table = tableElement.columns(columns).create();

        assertThat(table.getColumns().size()).isEqualTo(columns.size());
    }

    @Test
    public void it_sets_table_column_width() {
        List<String> columns = ImmutableList.of("c1", "c2", "c3");
        List<Integer> columnWidth = ImmutableList.of(200, 50); // width of c3 should be set to default value

        TableView<TableRow> table = tableElement.columns(columns).columnWidth(columnWidth).create();

        assertThat(table.getColumns().get(0).getMinWidth()).isEqualTo(200);
        assertThat(table.getColumns().get(1).getMinWidth()).isEqualTo(50);
        assertThat(table.getColumns().get(2).getMinWidth()).isEqualTo(100);
    }

    @Test
    public void it_sets_table_height() {
        TableView<TableRow> table = tableElement.height(567).create();

        assertThat(table.getPrefHeight()).isEqualTo(567);
    }

    @Test
    public void it_sets_table_id() {
        TableView<TableRow> table = tableElement.id("table-id").create();

        assertThat(table.getId()).isEqualTo("table-id");
    }
}