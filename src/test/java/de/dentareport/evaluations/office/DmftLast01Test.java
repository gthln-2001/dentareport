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
public class DmftLast01Test {

    private Db db;
    private DmftLast01 dmftLast01;
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
        this.dmftLast01 = new DmftLast01();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_evaluates_dmft_from_last_01() throws Exception {
        dmftLast01.evaluate();

        Map<String, String> results = results();

        assertThat(results.get("item")).isEqualTo("dmft_last_01");
        assertThat(results.get("name")).isEqualTo(Keys.TEXT_DMFT_LAST_01);
        assertThat(results.get("unit")).isEqualTo(Keys.UNIT_DMFT);
        assertThat(results.get("minimum")).isEqualTo("7");
        assertThat(results.get("maximum")).isEqualTo("77");
        assertThat(results.get("average")).isEqualTo("42.13");
        assertThat(results.get("median")).isEqualTo("44.5");
    }

    private ImmutableList<List<String>> testData() {
        return ImmutableList.<List<String>>builder()
                .add(ImmutableList.of("111", "2000-10-01", "11"))
                .add(ImmutableList.of("222", "2001-10-01", "27"))
                .add(ImmutableList.of("333", "2002-10-01", "42"))
                .add(ImmutableList.of("444", "2003-10-01", "63"))
                .add(ImmutableList.of("555", "2004-10-01", "63"))
                .add(ImmutableList.of("666", "2004-10-01", "7"))
                .add(ImmutableList.of("777", "2005-10-01", "77"))
                .add(ImmutableList.of("888", "2006-10-01", "47"))
                .add(ImmutableList.of("111", "1980-10-01", "-1000"))
                .add(ImmutableList.of("333", "1980-10-01", "2000"))
                .add(ImmutableList.of("333", "1990-10-01", "5000"))
                .build();
    }

    private Map<String, String> results() throws Exception {
        ResultSet rs = db().query(String.format("SELECT * FROM %s WHERE item='dmft_last_01'",
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
            row.addCell(new DbCell("dmft", e.get(2)));
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
                "dmft"
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