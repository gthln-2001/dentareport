package de.dentareport.utils.db;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DbCellTest {

    @Test
    public void it_belongs_to_a_column() {
        DbCell dbCell = new DbCell("foo", "bar");

        assertThat(dbCell.column()).isEqualTo("foo");
    }

    @Test
    public void it_has_a_value() {
        DbCell dbCell = new DbCell("foo", "bar");

        assertThat(dbCell.value()).isEqualTo("bar");
    }

    @Test
    public void it_sets_new_value() {
        DbCell dbCell = new DbCell("foo", "bar");

        dbCell.setValue("biz");

        assertThat(dbCell.value()).isEqualTo("biz");
    }

    @Test
    public void it_copys_existing_cell() {
        DbCell origin = new DbCell("foo", "bar");
        DbCell copy = new DbCell(origin);

        assertThat(copy).isNotSameAs(origin);
        assertThat(copy.column()).isEqualTo("foo");
        assertThat(copy.value()).isEqualTo("bar");
    }
}