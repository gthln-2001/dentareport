package de.dentareport;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.Db;
import de.dentareport.utils.db.DbConnection;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.TestHelper.dbInMemory;
import static de.dentareport.utils.db.DbConnection.db;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MetadataTest {

    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        Db db = dbInMemory();
        new Expectations() {{
            db();
            result = db;
        }};
        Metadata.init();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db().close();
    }

    @Test
    public void it_initialises_metadata() throws Exception {
        Db db = dbInMemory();
        new Expectations() {{
            db();
            result = db;
        }};
        assertThat(db().hasTable(Keys.METADATA_DB_TABLENAME)).isFalse();

        Metadata.init();

        assertThat(db().hasTable(Keys.METADATA_DB_TABLENAME)).isTrue();
    }

    @Test
    public void it_sets_key() {
        assertThat(db().hasEntry(Keys.METADATA_DB_TABLENAME, "key", "foo")).isFalse();

        Metadata.set("foo", "bar");

        assertThat(db().hasEntry(Keys.METADATA_DB_TABLENAME, "key", "foo")).isTrue();
    }

    @Test
    public void it_checks_if_key_exists() {
        assertThat(Metadata.has("foo")).isFalse();

        Metadata.set("foo", "bar");

        assertThat(Metadata.has("foo")).isTrue();
        assertThat(Metadata.has("biz")).isFalse();
    }

    @Test
    public void it_gets_value_for_key() {
        Metadata.set("foo", "bar");

        assertThat(Metadata.get("foo")).isEqualTo("bar");
    }

    @Test
    public void it_throws_exception_when_trying_to_access_key_that_does_not_exist() {
        assertThatExceptionOfType(DentareportSqlException.class).isThrownBy(() ->
                Metadata.get("foo")
        );
    }

    @Test
    public void it_deletes_key() {
        Metadata.set("foo", "bar");

        Metadata.delete("foo");

        assertThat(db().hasEntry(Keys.METADATA_DB_TABLENAME, "key", "foo")).isFalse();
    }
}