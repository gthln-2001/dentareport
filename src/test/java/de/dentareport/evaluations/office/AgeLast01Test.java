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

public class AgeLast01Test {

    private Db db;
    private AgeLast01 ageLast01;
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
        this.ageLast01 = new AgeLast01();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_evaluates_age_from_last_01() throws Exception {
        ageLast01.evaluate();

        Map<String, String> results = results();

        assertThat(results.get("item")).isEqualTo("age_last_01");
        assertThat(results.get("name")).isEqualTo(Keys.TEXT_AGE_LAST_01);
        assertThat(results.get("unit")).isEqualTo(Keys.UNIT_YEARS);
        assertThat(results.get("minimum")).isEqualTo("6");
        assertThat(results.get("maximum")).isEqualTo("50");
        assertThat(results.get("average")).isEqualTo("29.43");
        assertThat(results.get("median")).isEqualTo("30.0");
    }

    private ImmutableList<List<String>> testData() {
        return ImmutableList.<List<String>>builder()
                .add(ImmutableList.of("111", "6"))
                .add(ImmutableList.of("222", "20"))
                .add(ImmutableList.of("333", "20"))
                .add(ImmutableList.of("444", "30"))
                .add(ImmutableList.of("555", "40"))
                .add(ImmutableList.of("666", "40"))
                .add(ImmutableList.of("777", "50"))
                .add(ImmutableList.of("888", Keys.NO_DATA))
                .build();
    }

    private void prepareTestDb() {
        createTables();
        fillSourceTable();
    }

    private void fillSourceTable() {
        List<DbRow> rows = testData().stream().map(e -> {
            DbRow row = new DbRow();
            row.addCell(new DbCell("patient_index", e.get(0)));
            row.addCell(new DbCell("age_last_01", e.get(1)));
            return row;
        }).collect(Collectors.toList());

        db().writeRows("patients", rows);
    }

    private void createTables() {
        createSourceTable();
        createTargetTable();
    }

    private void createSourceTable() {
        List<String> columns = ImmutableList.of(
                "patient_index",
                "age_last_01"
        );
        List<DbColumn> dbColumns = columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
        db().rebuildTable("patients", dbColumns);
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

    private Map<String, String> results() throws Exception {
        ResultSet rs = db().query(String.format("SELECT * FROM %s WHERE item='age_last_01'",
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
}