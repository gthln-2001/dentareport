package de.dentareport.utils.dbf;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DbfCellTest {

    @Test
    public void it_belongs_to_a_column() {
        DbfCell dbfCell = new DbfCell("foo", "bar");

        assertThat(dbfCell.column()).isEqualTo("foo");
    }

    @Test
    public void it_has_a_value() {
        DbfCell dbfCell = new DbfCell("foo", "bar");

        assertThat(dbfCell.value()).isEqualTo("bar");
    }

    @Test
    public void it_sets_new_value() {
        DbfCell dbfCell = new DbfCell("foo", "bar");

        dbfCell.setValue("biz");

        assertThat(dbfCell.value()).isEqualTo("biz");
    }
}