package de.dentareport.evaluations.meta.annual_failure_rates;

import de.dentareport.evaluations.meta.dependencies.Dependency;
import de.dentareport.evaluations.meta.events.Event;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.*;

import static de.dentareport.TestHelper.dbInMemory;
import static de.dentareport.utils.db.DbConnection.db;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class PerItemCombinationTest {

    private PerItemCombination afr;
    private String evaluationTable = "evaluation";
    private String kaplanMeierTable = String.format(
            "%s%s",
            evaluationTable,
            Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER);
    private String afrTable = String.format(
            "%s%s",
            evaluationTable,
            Keys.DB_TABLE_SUFFIX_AFR);
    private Event event = event();
    private Dependency dependency = dependency();
    private Db db;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            db();
            result = db;
        }};
        afr = new PerItemCombination();
        setUpTestDb();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_evaluates_annual_failure_rate() throws Exception {
        afr.evaluate(evaluationTable, event, dependency);

        LinkedHashMap<String, Map<String, String>> afr = getResultsFromDb();

        assertThat(afr.size()).isEqualTo(3);

        Iterator<Map.Entry<String, Map<String, String>>> keys = afr.entrySet().iterator();

        assertThat(keys.next().getKey()).isEqualTo("group2");
        assertThat(keys.next().getKey()).isEqualTo("group1");
        assertThat(keys.next().getKey()).isEqualTo("group3");

        assertThat(afr.get("group1").size()).isEqualTo(1);
        assertThat(afr.get("group1").get("5")).isEqualTo("0,00");

        assertThat(afr.get("group2").size()).isEqualTo(2);
        assertThat(afr.get("group2").get("5")).isEqualTo("12,94");
        assertThat(afr.get("group2").get("10")).isEqualTo("8,76");

        assertThat(afr.get("group3").size()).isEqualTo(3);
        assertThat(afr.get("group3").get("5")).isEqualTo("12,94");
        assertThat(afr.get("group3").get("10")).isEqualTo("6,70");
        assertThat(afr.get("group3").get("15")).isEqualTo("4,52");
    }

    @Test
    public void it_clears_old_data_for_current_event_and_dependency_before_evaluating() throws Exception {
        addRowToKaplanMeierTableForOtherEvent();
        afr.evaluate(evaluationTable, new Event("foo", "", ""), new Dependency("bar", "", ""));

        afr.evaluate(evaluationTable, event, dependency);
        afr.evaluate(evaluationTable, event, dependency);

        ResultSet rs = db.query("SELECT COUNT(*) AS total FROM " + afrTable);
        assertThat(rs.getInt("total")).isEqualTo(7);
    }

    private void fillKaplanMeierTable() {
        List<DbRow> dbRows = new ArrayList<>();
        dbRows.add(createRow("group1", "2", "1826", "0.5")); // 5 * 365 + 1

        dbRows.add(createRow("group2", "1", "1824", "0.5")); // 5 * 365 - 1
        dbRows.add(createRow("group2", "1", "1826", "0.4")); // 5 * 365 + 1
        dbRows.add(createRow("group2", "1", "3651", "0.3")); // 10 * 365 + 1

        dbRows.add(createRow("group3", "3", "1824", "0.5")); // 5 * 365 - 1
        dbRows.add(createRow("group3", "3", "5476", "0.4")); // 15 * 365 + 1

        db.writeRows(kaplanMeierTable, dbRows);
    }

    private Event event() {
        return new Event("event_name", "", "");
    }

    private Dependency dependency() {
        return new Dependency("dependency_name", "", "");
    }

    private void setUpTestDb() {
        createKaplanMeierTable();
        createAfrTable();
        fillKaplanMeierTable();
    }

    private void createKaplanMeierTable() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("event", "text"));
        dbColumns.add(new DbColumn("dependency", "text"));
        dbColumns.add(new DbColumn("group_name", "text"));
        dbColumns.add(new DbColumn("group_order", "text"));
        dbColumns.add(new DbColumn("x", "text"));
        dbColumns.add(new DbColumn("y", "text"));
        db.rebuildTable(kaplanMeierTable, dbColumns);
    }

    private DbRow createRow(String group, String groupOrder, String x, String y) {
        DbRow ret = new DbRow();
        ret.addCell(new DbCell("event", event.name()));
        ret.addCell(new DbCell("dependency", dependency.name()));
        ret.addCell(new DbCell("group_name", group));
        ret.addCell(new DbCell("group_order", groupOrder));
        ret.addCell(new DbCell("x", x));
        ret.addCell(new DbCell("y", y));
        return ret;
    }

    private void createAfrTable() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("event", "text"));
        dbColumns.add(new DbColumn("dependency", "text"));
        dbColumns.add(new DbColumn("group_name", "text"));
        dbColumns.add(new DbColumn("group_order", "text"));
        dbColumns.add(new DbColumn("interval", "text"));
        dbColumns.add(new DbColumn("afr", "text"));
        db.rebuildTable(afrTable, dbColumns);
    }

    private LinkedHashMap<String, Map<String, String>> getResultsFromDb() throws Exception {
        LinkedHashMap<String, Map<String, String>> ret = new LinkedHashMap<>();

        ResultSet rs = db.query(String.format(
                "SELECT * FROM %s WHERE event = '%s' AND dependency ='%s' ORDER BY group_order",
                afrTable,
                event.name(),
                dependency.name()
        ));

        while (rs.next()) {
            ret.putIfAbsent(rs.getString("group_name"), new HashMap<>());
            ret.get(rs.getString("group_name")).put(rs.getString("interval"), rs.getString("afr"));
        }
        return ret;
    }

    private void addRowToKaplanMeierTableForOtherEvent() {
        DbRow row = new DbRow();
        row.addCell(new DbCell("event", "foo"));
        row.addCell(new DbCell("dependency", "bar"));
        row.addCell(new DbCell("group_name", "biz"));
        row.addCell(new DbCell("x", "1826"));
        row.addCell(new DbCell("y", "0.5"));

        List<DbRow> dbRows = new ArrayList<>();
        dbRows.add(row);

        db.writeRows(kaplanMeierTable, dbRows);
    }
}