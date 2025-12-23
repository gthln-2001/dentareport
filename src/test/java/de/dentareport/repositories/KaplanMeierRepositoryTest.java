package de.dentareport.repositories;

import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.*;
//import javafx.scene.chart.XYChart;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static de.dentareport.TestHelper.dbInMemory;
import static de.dentareport.utils.db.DbConnection.db;
import static org.assertj.core.api.Assertions.assertThat;

public class KaplanMeierRepositoryTest {

    private Db db;
    private KaplanMeierRepository kaplanMeierRepository;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
//        new Expectations() {{
//            db();
//            result = db;
//        }};
        setUpTestData();
        kaplanMeierRepository = new KaplanMeierRepository();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db().close();
    }

    @Test
    public void it_gets_list_of_kaplan_meier_data() {
        ImmutableMap<String, String> options = ImmutableMap.of(
                "evaluationId", "test",
                "event", "event1",
                "dependency", "dependency1");

//        List<XYChart.Series<Number, Number>> result = kaplanMeierRepository.lines(options);
//
//        assertThat(result.size()).isEqualTo(3);
//
//        assertThat(result.get(0).getName()).isEqualTo("group2");
//        assertThat(result.get(0).getData().size()).isEqualTo(4);
//        assertThat(result.get(0).getData().get(0).getXValue()).isEqualTo(4.0);
//        assertThat(result.get(0).getData().get(0).getYValue()).isEqualTo(1.0);
//        assertThat(result.get(0).getData().get(1).getXValue()).isEqualTo(4.0);
//        assertThat(result.get(0).getData().get(1).getYValue()).isEqualTo(4.0);
//        assertThat(result.get(0).getData().get(2).getXValue()).isEqualTo(5.498630136986302);
//        assertThat(result.get(0).getData().get(2).getYValue()).isEqualTo(4.0);
//        assertThat(result.get(0).getData().get(3).getXValue()).isEqualTo(5.498630136986302);
//        assertThat(result.get(0).getData().get(3).getYValue()).isEqualTo(6.0);
//
//        assertThat(result.get(1).getName()).isEqualTo("group1");
//        assertThat(result.get(1).getData().size()).isEqualTo(6);
//        assertThat(result.get(1).getData().get(0).getXValue()).isEqualTo(1.0);
//        assertThat(result.get(1).getData().get(0).getYValue()).isEqualTo(1.0);
//        assertThat(result.get(1).getData().get(1).getXValue()).isEqualTo(1.0);
//        assertThat(result.get(1).getData().get(1).getYValue()).isEqualTo(1.0);
//        assertThat(result.get(1).getData().get(2).getXValue()).isEqualTo(2.0);
//        assertThat(result.get(1).getData().get(2).getYValue()).isEqualTo(1.0);
//        assertThat(result.get(1).getData().get(3).getXValue()).isEqualTo(2.0);
//        assertThat(result.get(1).getData().get(3).getYValue()).isEqualTo(2.0);
//        assertThat(result.get(1).getData().get(4).getXValue()).isEqualTo(3.0);
//        assertThat(result.get(1).getData().get(4).getYValue()).isEqualTo(2.0);
//        assertThat(result.get(1).getData().get(5).getXValue()).isEqualTo(3.0);
//        assertThat(result.get(1).getData().get(5).getYValue()).isEqualTo(3.0);
//
//
//        assertThat(result.get(2).getName()).isEqualTo("group3");
//        assertThat(result.get(2).getData().size()).isEqualTo(2);
//        assertThat(result.get(2).getData().get(0).getXValue()).isEqualTo(5.0);
//        assertThat(result.get(2).getData().get(0).getYValue()).isEqualTo(1.0);
//        assertThat(result.get(2).getData().get(1).getXValue()).isEqualTo(5.0);
//        assertThat(result.get(2).getData().get(1).getYValue()).isEqualTo(5.0);
    }

    private List<DbRow> testData() {
        List<DbRow> dbRows = new ArrayList<>();

        // event, dependency, group_name, group_order, x, y
        dbRows.add(dbRow("event1", "dependency1", "group1", "2", "365.0", "1.0"));
        dbRows.add(dbRow("event1", "dependency1", "group1", "2", "730.0", "2.0"));
        dbRows.add(dbRow("event1", "dependency1", "group1", "2", "1095.0", "3.0"));
        dbRows.add(dbRow("event1", "dependency1", "group2", "1", "1460.0", "4.0"));
        dbRows.add(dbRow("event1", "dependency1", "group3", "3", "1825.0", "5.0"));
        dbRows.add(dbRow("event1", "dependency1", "group2", "1", "2007.0", "6.0"));
        dbRows.add(dbRow("event1", "dependency2", "group1", "1", "7", "7.0")); // should be ignored
        dbRows.add(dbRow("event2", "dependency1", "group1", "1", "8", "8.0")); // should be ignored
        dbRows.add(dbRow("event2", "dependency2", "group1", "1", "9", "9.0")); // should be ignored

        return dbRows;
    }

    private void setUpTestData() {
        String evaluationTable = "evaluation_test";
        db.rebuildTable(String.format("%s%s", evaluationTable, Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER), columns());
        db.writeRows(String.format("%s%s", evaluationTable, Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER), testData());
    }

    private List<DbColumn> columns() {
        List<DbColumn> dbColumns = new ArrayList<>();

        dbColumns.add(new DbColumn("event", "text"));
        dbColumns.add(new DbColumn("dependency", "text"));
        dbColumns.add(new DbColumn("group_name", "text"));
        dbColumns.add(new DbColumn("group_order", "text"));
        dbColumns.add(new DbColumn("x", "text"));
        dbColumns.add(new DbColumn("y", "text"));

        return dbColumns;
    }

    private DbRow dbRow(String event, String dependency, String groupName, String groupOrder, String x, String y) {
        DbRow row = new DbRow();

        row.addCell(new DbCell("event", event));
        row.addCell(new DbCell("dependency", dependency));
        row.addCell(new DbCell("group_name", groupName));
        row.addCell(new DbCell("group_order", groupOrder));
        row.addCell(new DbCell("x", x));
        row.addCell(new DbCell("y", y));

        return row;
    }
}