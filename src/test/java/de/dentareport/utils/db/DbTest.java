package de.dentareport.utils.db;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.dentareport.utils.db.DbTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DbTest {

    private Connection connection;
    private Db db;

    @BeforeEach
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        db = new Db(connection);
    }

    @AfterEach
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void it_closes_the_db_connection() throws Exception {
        assertThat(connection.isValid(10)).isTrue();
        db.close();
        assertThat(connection.isValid(10)).isFalse();
    }

    @Test
    public void it_checks_if_db_connection_is_valid() {
        assertThat(db.isDisconnected()).isFalse();
        db.close();
        assertThat(db.isDisconnected()).isTrue();
    }

    @Test
    public void it_checks_if_a_table_exists() throws Exception {
        assertThat(db.hasTable("foo")).isFalse();
        createTable(connection, "foo");
        assertThat(db.hasTable("foo")).isTrue();
        assertThat(db.hasTable("does_not_exist")).isFalse();
    }

    @Test
    public void it_checks_if_entry_with_given_columnname_and_value_exists_in_given_table() throws Exception {
        assertThat(db.hasEntry("foo", "column", "foobar")).isFalse();

        createTable(connection, "foo");
        assertThat(db.hasEntry("foo", "column", "foobar")).isFalse();

        db.writeRow("foo", createTestRow("some_value"));
        assertThat(db.hasEntry("foo", "column", "foobar")).isFalse();

        db.writeRow("foo", createTestRow("foobar"));
        assertThat(db.hasEntry("foo", "column", "foobar")).isTrue();
        assertThat(db.hasEntry("foo", "column", "bizbaz")).isFalse();

        assertThat(db.hasEntry("foo", "some_other_column", "foobar")).isFalse();
        assertThat(db.hasEntry("some_toher_table", "column", "foobar")).isFalse();
    }

    // TODO: Fix tests

//    @Test
//    public void it_rebuilds_a_table() throws Exception {
//        List<DbColumn> dbColumns = new ArrayList<>();
//        dbColumns.add(new DbColumn("col1", "text"));
//        dbColumns.add(new DbColumn("col2", "text"));
//
//        db.rebuildTable("foo_table", dbColumns);
//
//        assertThat(tableExists(connection, "foo_table")).isTrue();
//        assertThat(tableHasColumn(connection, "foo_table", "col1")).isTrue();
//        assertThat(tableHasColumn(connection, "foo_table", "col2")).isTrue();
//        assertThat(columnIsType(connection, "foo_table", "col1", "text")).isTrue();
//        assertThat(columnIsType(connection, "foo_table", "col2", "text")).isTrue();
//    }

    @Test
    public void it_writes_a_row_of_data() throws Exception {
        createTestTable(connection);
        DbRow dbRow = createTestRow("valueOf");

        db.writeRow("test", dbRow);

        assertThat(tableContainsRow(connection, "test", dbRow)).isTrue();
    }

    @Test
    public void it_writes_multiple_rows_of_data() throws Exception {
        createTestTable(connection);
        DbRow dbRow1 = createTestRow("foo");
        DbRow dbRow2 = createTestRow("bar");
        List<DbRow> dbRows = new ArrayList<>();
        dbRows.add(dbRow1);
        dbRows.add(dbRow2);

        db.writeRows("test", dbRows);

        assertThat(tableContainsRow(connection, "test", dbRow1)).isTrue();
        assertThat(tableContainsRow(connection, "test", dbRow2)).isTrue();
    }

    @Test
    public void it_does_not_write_rows_if_no_data_is_provided() throws Exception {
        createTestTable(connection);

        db.writeRows("test", new ArrayList<>());

        assertThat(tableIsEmpty(connection, "test")).isTrue();
    }

    @Test
    public void it_updates_multiple_rows() throws Exception {
        Statement db = connection.createStatement();
        db.executeUpdate("CREATE TABLE foo_table (id integer primary key, col1 text)");
        db.executeUpdate("INSERT INTO foo_table (id, col1) VALUES (1, 'a')");
        db.executeUpdate("INSERT INTO foo_table (id, col1) VALUES (23, 'd')");
        db.executeUpdate("INSERT INTO foo_table (id, col1) VALUES (42, 'g')");

        DbRow new1 = new DbRow();
        new1.addCell(new DbCell("id", "1"));
        new1.addCell(new DbCell("col1", "foo"));
        DbRow new2 = new DbRow();
        new2.addCell(new DbCell("id", "42"));
        new2.addCell(new DbCell("col1", "biz"));

        List<DbRow> dbRows = new ArrayList<>();
        dbRows.add(new1);
        dbRows.add(new2);

        this.db.updateRows("foo_table", dbRows);

        assertThat(tableContainsRow(connection, "foo_table", new1)).isTrue();
        assertThat(tableContainsRow(connection, "foo_table", new2)).isTrue();

        DbRow old1 = new DbRow();
        old1.addCell(new DbCell("id", "1"));
        old1.addCell(new DbCell("col1", "a"));

        assertThat(tableContainsRow(connection, "foo_table", old1)).isFalse();
    }

    @Test
    public void it_does_not_update_if_no_data_is_provided() throws Exception {
        createTestTable(connection);

        db.updateRows("test", new ArrayList<>());

        assertThat(tableIsEmpty(connection, "test")).isTrue();
    }

    @Test
    public void it_sends_a_raw_query_to_the_database(@Mocked Connection mockConnection,
                                                     @Mocked Statement mockStatement,
                                                     @Mocked ResultSet mockResultSet) throws Exception {
        new Expectations() {{
            mockConnection.createStatement();
            mockStatement.executeQuery("some query");
        }};

        Db wrapper = new Db(mockConnection);

        assertThat(wrapper.query("some query")).isEqualTo(mockResultSet);
    }

    @Test
    public void it_sends_a_raw_query_without_result_to_the_database(@Mocked Connection mockConnection,
                                                                     @Mocked Statement mockStatement) throws Exception {
        new Expectations() {{
            mockConnection.createStatement();
            mockStatement.execute("some delete query");
        }};

        Db wrapper = new Db(mockConnection);

        wrapper.execute("some delete query");
    }

    @Test
    public void it_gets_first_and_last_visit_from_one_table() throws Exception {
        createTestTableForFirstAndLastVisit(db, connection);

        Map<String, Map<String, String>> result = db.firstAndLastVisits("test");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get("42").get("first_visit")).isEqualTo("2010-10-01");
        assertThat(result.get("42").get("last_visit")).isEqualTo("2010-12-01");
        assertThat(result.get("23").get("first_visit")).isEqualTo("2010-11-01");
        assertThat(result.get("23").get("last_visit")).isEqualTo("2010-11-01");
    }

    @Test
    public void it_gets_first_and_last_visit_from_one_table_with_conditions() throws Exception {
        createTestTableForFirstAndLastVisit(db, connection);

        Map<String, Map<String, String>> result = db.firstAndLastVisits("test", "foo='bar'");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get("42").get("first_visit")).isEqualTo("2010-11-01");
        assertThat(result.get("42").get("last_visit")).isEqualTo("2010-11-01");
    }
}