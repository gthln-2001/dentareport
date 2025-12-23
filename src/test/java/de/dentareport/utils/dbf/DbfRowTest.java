package de.dentareport.utils.dbf;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DbfRowTest {

    @Test
    public void it_adds_a_cell_to_row_and_gets_its_value_back() {
        DbfRow row = new DbfRow();
        row.addCell(new DbfCell("foo", "bar"));
        row.addCell(new DbfCell("biz", "baz"));

        assertThat(row.value("foo")).isEqualTo("bar");
        assertThat(row.value("biz")).isEqualTo("baz");
    }

    @Test
    public void it_throws_an_exception_when_trying_to_access_data_that_does_not_exist() {
        DbfRow row = new DbfRow();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                assertThat(row.value("foo")).isEqualTo("bar")
        );
    }

    @Test
    public void it_assigns_new_value_to_existing_cell() {
        DbfRow row = new DbfRow();
        row.addCell(new DbfCell("foo", "bar"));

        row.setValue("foo", "biz");

        assertThat(row.value("foo")).isEqualTo("biz");
    }

    @Test
    public void it_throws_exception_when_trying_to_assign_new_value_to_cell_that_does_not_exist() {
        DbfRow row = new DbfRow();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                row.setValue("foo", "bar")
        );
    }

    @Test
    public void it_gets_status_of_deleted() {
        DbfRow row = new DbfRow();

        assertThat(row.isDeleted()).isFalse();
    }

    @Test
    public void it_marks_a_row_as_deleted() {
        DbfRow row = new DbfRow();
        row.markDeleted();

        assertThat(row.isDeleted()).isTrue();
    }
}