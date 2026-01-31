package de.dentareport.evaluations.meta.kaplan_meier;

import de.dentareport.evaluations.meta.dependencies.Dependency;
import de.dentareport.evaluations.meta.events.Event;
import de.dentareport.models.Point;
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

    private PerItemCombination kaplanMeier;
    private String evaluationTable = "evaluation";
    private String kaplanMeierTable = String.format(
            "%s%s",
            evaluationTable,
            Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER
    );
    private Event event = new Event(
            "event_name",
            "censored_column",
            "interval_column"
    );
    private Dependency dependency = new Dependency(
            "dependency_name",
            "dependency_column",
            "dependency_order"
    );
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
        kaplanMeier = new PerItemCombination();
        setUpTestDb();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_evaluates_kaplan_meier() throws Exception {
        kaplanMeier.evaluate(evaluationTable, event, dependency);

        LinkedHashMap<String, List<Point>> result = getResultsFromDb(dependency.name());

        assertThat(result.size()).isEqualTo(3);

        Iterator<Map.Entry<String, List<Point>>> keys = result.entrySet().iterator();
        assertThat(keys.next().getKey()).isEqualTo("group2");
        assertThat(keys.next().getKey()).isEqualTo("group1");
        assertThat(keys.next().getKey()).isEqualTo("group3");

        // Group with no censored events, we record only the first and last point
        List<Point> group1 = result.get("group1");
        assertThat(group1.size()).isEqualTo(2);
        assertThat(group1.get(0).x()).isEqualTo(0);
        assertThat(group1.get(0).y()).isEqualTo(1);
        assertThat(group1.get(1).x()).isEqualTo(900);
        assertThat(group1.get(1).y()).isEqualTo(1);

        // Group with two censored events
        List<Point> group2 = result.get("group2");
        assertThat(group2.size()).isEqualTo(4);
        assertThat(group2.get(0).x()).isEqualTo(0);
        assertThat(group2.get(0).y()).isEqualTo(1);
        assertThat(group2.get(1).x()).isEqualTo(600);
        assertThat(group2.get(1).y()).isEqualTo(0.75);
        assertThat(group2.get(2).x()).isEqualTo(1200);
        assertThat(group2.get(2).y()).isEqualTo(0.375);
        assertThat(group2.get(3).x()).isEqualTo(1500);
        assertThat(group2.get(3).y()).isEqualTo(0.375);

        // Group with censored last event, graph drops to zero
        List<Point> group3 = result.get("group3");
        assertThat(group3.size()).isEqualTo(2);
        assertThat(group3.get(0).x()).isEqualTo(0);
        assertThat(group3.get(0).y()).isEqualTo(1);
        assertThat(group3.get(1).x()).isEqualTo(600);
        assertThat(group3.get(1).y()).isEqualTo(0);
    }

    @Test
    public void it_works_when_no_dependency_is_selected() throws Exception {
        kaplanMeier.evaluate(evaluationTable, event, new Dependency(Keys.GUI_TEXT_NO_DEPENDENCY, "", ""));

        Map<String, List<Point>> result = getResultsFromDb(Keys.GUI_TEXT_NO_DEPENDENCY);

        assertThat(result.size()).isEqualTo(1);
        List<Point> group = result.get(Keys.GUI_TEXT_TOTAL);
        assertThat(group.size()).isEqualTo(5);
        assertThat(group.get(0).x()).isEqualTo(0);
        assertThat(group.get(0).y()).isEqualTo(1);
        assertThat(group.get(1).x()).isEqualTo(600);
        assertThat(group.get(1).y()).isEqualTo(0.8333333333333334);
        assertThat(group.get(2).x()).isEqualTo(600);
        assertThat(group.get(2).y()).isEqualTo(0.6666666666666667);
        assertThat(group.get(3).x()).isEqualTo(1200);
        assertThat(group.get(3).y()).isEqualTo(0.33333333333333337);
        assertThat(group.get(4).x()).isEqualTo(1500);
        assertThat(group.get(4).y()).isEqualTo(0.33333333333333337);
    }

    @Test
    public void it_clears_old_data_for_current_event_and_dependency_before_evaluating() throws Exception {
        addRowToEvaluationTableForOtherEvent();
        kaplanMeier.evaluate(evaluationTable,
                new Event("other", "other_censored_column", "other_interval_column"),
                new Dependency("another_one", "other_dependency_column", "other_dependency_order")
        );

        kaplanMeier.evaluate(evaluationTable, event, dependency);
        kaplanMeier.evaluate(evaluationTable, event, dependency);

        ResultSet rs = db.query("SELECT COUNT(*) AS total FROM " + kaplanMeierTable);
        assertThat(rs.getInt("total")).isEqualTo(10);
    }

    private void addRowToEvaluationTableForOtherEvent() {
        DbRow row = new DbRow();
        row.addCell(new DbCell("other_censored_column", "1"));
        row.addCell(new DbCell("other_interval_column", "300"));
        row.addCell(new DbCell("other_dependency_column", "foobar"));

        List<DbRow> dbRows = new ArrayList<>();
        dbRows.add(row);

        db.writeRows(evaluationTable, dbRows);
    }

    private LinkedHashMap<String, List<Point>> getResultsFromDb(String dependency) throws Exception {
        LinkedHashMap<String, List<Point>> ret = new LinkedHashMap<>();

        ResultSet rs = db.query(String.format(
                "SELECT * FROM %s WHERE event = '%s' AND dependency ='%s' ORDER BY group_order",
                kaplanMeierTable,
                event.name(),
                dependency
        ));

        while (rs.next()) {
            ret.putIfAbsent(rs.getString("group_name"), new ArrayList<>());
            ret.get(rs.getString("group_name")).add(new Point(rs.getDouble("x"), rs.getDouble("y")));
        }
        return ret;
    }

    private void setUpTestDb() {
        createEvaluationTable();
        createKaplanMeierTable();
        fillEvaluationTable();
    }

    private void fillEvaluationTable() {
        List<DbRow> dbRows = new ArrayList<>();

        dbRows.add(createRow("0", "300", "group1", "10"));
        dbRows.add(createRow("0", "600", "group1", "20"));
        dbRows.add(createRow("0", "900", "group1", "30"));

        dbRows.add(createRow("0", "1500", "group2", "1"));
        dbRows.add(createRow("1", "600", "group2", "2"));
        dbRows.add(createRow("1", "1200", "group2", "3"));
        dbRows.add(createRow("0", "900", "group2", "4"));
        dbRows.add(createRow("0", "300", "group2", "5"));

        dbRows.add(createRow("0", "300", "group3", "100"));
        dbRows.add(createRow("1", "600", "group3", "200"));

        db.writeRows(evaluationTable, dbRows);
    }

    private DbRow createRow(String censored,
                            String interval,
                            String dependency,
                            String orderColumn) {
        DbRow ret = new DbRow();
        ret.addCell(new DbCell("censored_column", censored));
        ret.addCell(new DbCell("interval_column", interval));
        ret.addCell(new DbCell("dependency_column", dependency));
        ret.addCell(new DbCell("dependency_order", orderColumn));
        return ret;
    }

    private void createEvaluationTable() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("censored_column", "text"));
        dbColumns.add(new DbColumn("interval_column", "text"));
        dbColumns.add(new DbColumn("dependency_column", "text"));
        dbColumns.add(new DbColumn("dependency_order", "text"));
        dbColumns.add(new DbColumn("other_censored_column", "text"));
        dbColumns.add(new DbColumn("other_interval_column", "text"));
        dbColumns.add(new DbColumn("other_dependency_column", "text"));
        dbColumns.add(new DbColumn("other_dependency_order", "text"));
        db.rebuildTable(evaluationTable, dbColumns);
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
}