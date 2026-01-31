package de.dentareport.repositories;

import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static de.dentareport.TestHelper.dbInMemory;
import static de.dentareport.utils.db.DbConnection.db;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class AfrRepositoryTest {

    @Mocked
    DbConnection mockDbConnection;
    private Db db;
    private AfrRepository afrRepository;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            db();
            result = db;
        }};
        setUpTestData();
        afrRepository = new AfrRepository();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db().close();
    }

    @Test
    public void it_gets_afr_table() {
        ImmutableMap<String, String> options = ImmutableMap.of(
                "evaluationId", "test",
                "event", "event1",
                "dependency", "dependency1");
        LinkedHashMap<String, Map<String, String>> result = afrRepository.afrTable(options);

        assertThat(result.size()).isEqualTo(3);

        Iterator<Map.Entry<String, Map<String, String>>> keys = result.entrySet().iterator();
        assertThat(keys.next().getKey()).isEqualTo("group2");
        assertThat(keys.next().getKey()).isEqualTo("group1");
        assertThat(keys.next().getKey()).isEqualTo("group3");

        assertThat(result.get("group1").size()).isEqualTo(3);
        assertThat(result.get("group1").get("1")).isEqualTo("1.0");
        assertThat(result.get("group1").get("2")).isEqualTo("3.0");
        assertThat(result.get("group1").get("3")).isEqualTo("2.0");

        assertThat(result.get("group2").size()).isEqualTo(2);
        assertThat(result.get("group2").get("4")).isEqualTo("4.0");
        assertThat(result.get("group2").get("6")).isEqualTo("6.0");

        assertThat(result.get("group3").size()).isEqualTo(1);
        assertThat(result.get("group3").get("5")).isEqualTo("5.0");
    }

    private List<DbRow> testData() {
        List<DbRow> dbRows = new ArrayList<>();

        // event, dependency, group_name, group_order, interval, afr
        dbRows.add(dbRow("event1", "dependency1", "group1", "2", "1", "1.0"));
        dbRows.add(dbRow("event1", "dependency1", "group1", "2", "3", "2.0"));
        dbRows.add(dbRow("event1", "dependency1", "group1", "2", "2", "3.0"));
        dbRows.add(dbRow("event1", "dependency1", "group2", "1", "4", "4.0"));
        dbRows.add(dbRow("event1", "dependency1", "group3", "3", "5", "5.0"));
        dbRows.add(dbRow("event1", "dependency1", "group2", "1", "6", "6.0"));
        dbRows.add(dbRow("event1", "dependency2", "group1", "1", "7", "7.0")); // should be ignored
        dbRows.add(dbRow("event2", "dependency1", "group1", "1", "8", "8.0")); // should be ignored
        dbRows.add(dbRow("event2", "dependency2", "group1", "1", "9", "9.0")); // should be ignored

        return dbRows;
    }

    private void setUpTestData() {
        String evaluationTable = "evaluation_test";
        db.rebuildTable(String.format("%s%s", evaluationTable, Keys.DB_TABLE_SUFFIX_AFR), columns());
        db.writeRows(String.format("%s%s", evaluationTable, Keys.DB_TABLE_SUFFIX_AFR), testData());
    }

    private List<DbColumn> columns() {
        List<DbColumn> dbColumns = new ArrayList<>();

        dbColumns.add(new DbColumn("event", "text"));
        dbColumns.add(new DbColumn("dependency", "text"));
        dbColumns.add(new DbColumn("group_name", "text"));
        dbColumns.add(new DbColumn("group_order", "text"));
        dbColumns.add(new DbColumn("interval", "text"));
        dbColumns.add(new DbColumn("afr", "text"));

        return dbColumns;
    }

    private DbRow dbRow(String event,
                        String dependency,
                        String groupName,
                        String groupOrder,
                        String interval,
                        String afr) {
        DbRow row = new DbRow();

        row.addCell(new DbCell("event", event));
        row.addCell(new DbCell("dependency", dependency));
        row.addCell(new DbCell("group_name", groupName));
        row.addCell(new DbCell("group_order", groupOrder));
        row.addCell(new DbCell("interval", interval));
        row.addCell(new DbCell("afr", afr));

        return row;
    }
}