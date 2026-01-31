package de.dentareport.utils.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class DbRowTest {

    @Test
    public void it_starts_with_empty_list_of_cells() {
        DbRow dbRow = new DbRow();

        List<DbCell> dbCells = dbRow.cells();

        assertThat(dbCells.size()).isEqualTo(0);
    }

    @Test
    public void it_add_and_gets_cells() {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("col1", "val1"));
        dbRow.addCell(new DbCell("col2", "val2"));

        List<DbCell> dbCells = dbRow.cells();

        assertThat(dbCells.size()).isEqualTo(2);
        assertThat(dbCells.get(0).value()).isEqualTo("val1");
        assertThat(dbCells.get(1).value()).isEqualTo("val2");
    }

    @Test
    public void it_removes_cell() {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("col1", "val1"));
        dbRow.addCell(new DbCell("col2", "val2"));

        dbRow.removeCell("col1");

        assertThat(dbRow.cells().size()).isEqualTo(1);
        assertThat(dbRow.value("col2")).isEqualTo("val2");
    }

    @Test
    public void it_adds_a_list_of_cells() {
        List<DbCell> cells = new ArrayList<>();
        cells.add(new DbCell("col1", "val1"));
        cells.add(new DbCell("col2", "val2"));

        DbRow dbRow = new DbRow();
        dbRow.addCells(cells);

        List<DbCell> dbCells = dbRow.cells();

        assertThat(dbCells.size()).isEqualTo(2);
        assertThat(dbCells.get(0).value()).isEqualTo("val1");
        assertThat(dbCells.get(1).value()).isEqualTo("val2");
    }

    @Test
    public void it_gets_a_list_of_column_names_for_all_cells() {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("col1", "val1"));
        dbRow.addCell(new DbCell("col2", "val2"));
        dbRow.addCell(new DbCell("col3", "val3"));

        List<String> columnNames = dbRow.columnnames();

        assertThat(columnNames.size()).isEqualTo(3);
        assertThat(columnNames.get(0)).isEqualTo("col1");
        assertThat(columnNames.get(1)).isEqualTo("col2");
        assertThat(columnNames.get(2)).isEqualTo("col3");
    }

    @Test
    public void it_gets_value_of_column_by_its_name() {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("col1", "val1"));
        dbRow.addCell(new DbCell("col2", "val2"));
        dbRow.addCell(new DbCell("col3", "val3"));

        assertThat(dbRow.value("col1")).isEqualTo("val1");
        assertThat(dbRow.value("col3")).isEqualTo("val3");
    }

    @Test
    public void it_throws_exeption_when_column_name_does_not_exist() {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("col", "val"));

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                dbRow.value("does_not_exist")
        );
    }

    @Test
    public void it_checks_if_column_exists() {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("col1", "val"));

        assertThat(dbRow.hasCell("col1")).isTrue();
        assertThat(dbRow.hasCell("col2")).isFalse();
    }

    @Test
    public void it_copys_existing_row() {
        DbCell dbCell1 = new DbCell("foo", "bar");
        DbCell dbCell2 = new DbCell("biz", "baz");
        DbRow origin = new DbRow();
        origin.addCell(dbCell1);
        origin.addCell(dbCell2);

        DbRow copy = DbRow.copy(origin);

        assertThat(copy).isNotSameAs(origin);
        assertThat(copy.cells().size()).isEqualTo(2);
        assertThat(copy.cells().get(0).value()).isEqualTo("bar");
        assertThat(copy.cells().get(0)).isNotSameAs(dbCell1);
    }

    @Test
    public void it_assigns_new_value_to_existing_cell() {
        DbRow row = new DbRow();
        row.addCell(new DbCell("foo", "bar"));

        row.setValue("foo", "biz");

        assertThat(row.value("foo")).isEqualTo("biz");
    }

    @Test
    public void it_throws_exception_when_trying_to_assign_new_value_to_cell_that_does_not_exist() {
        DbRow row = new DbRow();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                row.setValue("foo", "bar")
        );
    }
}