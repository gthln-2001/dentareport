package de.dentareport.evaluations.office;

import com.google.common.collect.ImmutableList;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.dentareport.TestHelper.dbInMemory;
import static de.dentareport.utils.db.DbConnection.db;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ToothcountLast01Test {

    private Db db;
    private ToothcountLast01 toothcountLast01;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
        prepareTestDb();
        this.toothcountLast01 = new ToothcountLast01();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_evaluates_toothcount_from_last_01() throws Exception {
        toothcountLast01.evaluate();

        Map<String, String> results = results();

        assertThat(results.get("item")).isEqualTo("toothcount_last_01");
        assertThat(results.get("name")).isEqualTo(Keys.TEXT_TOOTHCOUNT_LAST_01);
        assertThat(results.get("unit")).isEqualTo(Keys.UNIT_NUMBER);
        assertThat(results.get("minimum")).isEqualTo("10");
        assertThat(results.get("maximum")).isEqualTo("40");
        assertThat(results.get("average")).isEqualTo("20.5");
        assertThat(results.get("median")).isEqualTo("16.0");
    }

    private ImmutableList<List<String>> testData() {
        return ImmutableList.<List<String>>builder()
                .add(ImmutableList.of("111", "2000-10-01", "1", "2", "3", "4")) // 10
                .add(ImmutableList.of("222", "2001-10-01", "2", "3", "4", "5")) // 14
                .add(ImmutableList.of("333", "2002-10-01", "3", "4", "5", "6")) // 18
                .add(ImmutableList.of("444", "2003-10-01", "10", "10", "10", "10")) // 40
                .add(ImmutableList.of("111", "1980-10-01", "-10", "-10", "-10", "-10")) // should be ignored
                .add(ImmutableList.of("333", "1980-10-01", "20", "20", "20", "20")) // should be ignored
                .add(ImmutableList.of("333", "1990-10-01", "50", "50", "50", "50"))  // should be ignored
                .build();
    }

    private Map<String, String> results() throws Exception {
        ResultSet rs = db().query(String.format("SELECT * FROM %s WHERE item='toothcount_last_01'",
                Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES));
        rs.next();

        Map<String, String> ret = new HashMap<>();
        ret.put("item", rs.getString("item"));
        ret.put("name", rs.getString("name"));
        ret.put("unit", rs.getString("unit"));
        ret.put("average", rs.getString("average"));
        ret.put("minimum", rs.getString("minimum"));
        ret.put("maximum", rs.getString("maximum"));
        ret.put("median", rs.getString("median"));

        return ret;
    }

    private void prepareTestDb() {
        createTables();
        fillSourceTable();
    }

    private void fillSourceTable() {
        List<DbRow> rows = testData().stream().map(e -> {
            DbRow row = new DbRow();
            row.addCell(new DbCell("patient_index", e.get(0)));
            row.addCell(new DbCell("date", e.get(1)));
            row.addCell(new DbCell("tooth_count_q1", e.get(2)));
            row.addCell(new DbCell("tooth_count_q2", e.get(3)));
            row.addCell(new DbCell("tooth_count_q3", e.get(4)));
            row.addCell(new DbCell("tooth_count_q4", e.get(5)));
            return row;
        }).collect(Collectors.toList());

        db().writeRows("evidences_01", rows);
    }

    private void createTables() {
        createSourceTable();
        createTargetTable();
    }

    private void createSourceTable() {
        List<String> columns = ImmutableList.of(
                "patient_index",
                "date",
                "tooth_count_q1",
                "tooth_count_q2",
                "tooth_count_q3",
                "tooth_count_q4"
        );
        List<DbColumn> dbColumns = columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
        db().rebuildTable("evidences_01", dbColumns);

    }

    private void createTargetTable() {
        List<String> columns = ImmutableList.of(
                "item",
                "name",
                "unit",
                "average",
                "minimum",
                "maximum",
                "median"
        );
        List<DbColumn> dbColumns = columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
        db().rebuildTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES, dbColumns);
    }
}