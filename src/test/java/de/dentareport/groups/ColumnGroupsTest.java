package de.dentareport.groups;

import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

public class ColumnGroupsTest {

    private Db db;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_groups_data_by_thirds() throws Exception {
        prepareTableThirds();
        ColumnGroups grouper = new ColumnGroups();
        grouper.group("test_table", "value_of_foo", "groups__of__value_of_foo");
        Map<String, String> results = resultsThirds();

        assertThat(results.size()).isEqualTo(11);

        assertThat(results.get("1")).isEqualTo("<=3");
        assertThat(results.get("2")).isEqualTo("<=3");
        assertThat(results.get("3")).isEqualTo(">3, <7");
        assertThat(results.get("4")).isEqualTo(">=7");
        assertThat(results.get("5")).isEqualTo(">=7");
        assertThat(results.get("6")).isEqualTo("no_data");
        assertThat(results.get("7")).isEqualTo("<=3");
        assertThat(results.get("8")).isEqualTo(">3, <7");
        assertThat(results.get("9")).isEqualTo(">3, <7");
        assertThat(results.get("10")).isEqualTo(">=7");
        assertThat(results.get("11")).isEqualTo("no_data");
    }

    @Test
    public void it_groups_data_by_age() throws Exception {
        prepareTableAge();
        ColumnGroups grouper = new ColumnGroups();
        grouper.group("test_table", "age__at__event_start_observation", "groups__of__age_at_event_start_observation");
        Map<String, String> results = resultsAge();

        assertThat(results.size()).isEqualTo(10);

        assertThat(results.get("1")).isEqualTo("0-20");
        assertThat(results.get("2")).isEqualTo("0-20");
        assertThat(results.get("3")).isEqualTo("21-40");
        assertThat(results.get("4")).isEqualTo("21-40");
        assertThat(results.get("5")).isEqualTo("41-60");
        assertThat(results.get("6")).isEqualTo("41-60");
        assertThat(results.get("7")).isEqualTo("61-80");
        assertThat(results.get("8")).isEqualTo("61-80");
        assertThat(results.get("9")).isEqualTo(">80");
        assertThat(results.get("10")).isEqualTo("no_data");
    }

    private Map<String, String> resultsThirds() throws Exception {
        Map<String, String> ret = new HashMap<>();
        ResultSet rs = db.query("SELECT * FROM test_table");
        while (rs.next()) {
            ret.put(rs.getString("id"), rs.getString("groups__of__value_of_foo"));
        }
        return ret;
    }

    private void prepareTableThirds() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("id", "integer primary key"));
        dbColumns.add(new DbColumn("value_of_foo", "text"));
        dbColumns.add(new DbColumn("groups__of__value_of_foo", "text"));
        db.rebuildTable("test_table", dbColumns);
        fillTableThirds();
    }

    private void fillTableThirds() {
        List<DbRow> dbRows = new ArrayList<>();
        dbRows.add(createRowThirds("1"));
        dbRows.add(createRowThirds("3"));
        dbRows.add(createRowThirds("5"));
        dbRows.add(createRowThirds("7"));
        dbRows.add(createRowThirds("9"));
        dbRows.add(createRowThirds("no_data"));
        dbRows.add(createRowThirds("2"));
        dbRows.add(createRowThirds("4"));
        dbRows.add(createRowThirds("6"));
        dbRows.add(createRowThirds("8"));
        dbRows.add(createRowThirds("something_else"));
        db.writeRows("test_table", dbRows);
    }

    private DbRow createRowThirds(String value) {
        DbRow ret = new DbRow();
        ret.addCell(new DbCell("value_of_foo", value));
        return ret;
    }

    private Map<String, String> resultsAge() throws Exception {
        Map<String, String> ret = new HashMap<>();
        ResultSet rs = db.query("SELECT * FROM test_table");
        while (rs.next()) {
            ret.put(rs.getString("id"), rs.getString("groups__of__age_at_event_start_observation"));
        }
        return ret;
    }

    private void prepareTableAge() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("id", "integer primary key"));
        dbColumns.add(new DbColumn("age__at__event_start_observation", "text"));
        dbColumns.add(new DbColumn("groups__of__age_at_event_start_observation", "text"));
        db.rebuildTable("test_table", dbColumns);
        fillTableAge();
    }

    private void fillTableAge() {
        List<DbRow> dbRows = new ArrayList<>();
        dbRows.add(createRowAge("0"));
        dbRows.add(createRowAge("20"));
        dbRows.add(createRowAge("21"));
        dbRows.add(createRowAge("40"));
        dbRows.add(createRowAge("41"));
        dbRows.add(createRowAge("60"));
        dbRows.add(createRowAge("61"));
        dbRows.add(createRowAge("80"));
        dbRows.add(createRowAge("81"));
        dbRows.add(createRowAge("no_data"));
        db.writeRows("test_table", dbRows);
    }

    private DbRow createRowAge(String value) {
        DbRow ret = new DbRow();
        ret.addCell(new DbCell("age__at__event_start_observation", value));
        return ret;
    }
}