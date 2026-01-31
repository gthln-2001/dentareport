package de.dentareport.utils.db;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class DbColumnTest {

    @Test
    public void it_has_a_name() {
        DbColumn dbColumn = new DbColumn("foobar", "text");

        assertThat(dbColumn.name()).isEqualTo("foobar");
    }

    @Test
    public void it_has_a_type() {
        DbColumn dbColumn = new DbColumn("foobar", "text");

        assertThat(dbColumn.type()).isEqualTo("text");
    }

    @Test
    public void it_throws_exception_for_unsupported_type() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                new DbColumn("foobar", "not_supported")
        );
    }
}