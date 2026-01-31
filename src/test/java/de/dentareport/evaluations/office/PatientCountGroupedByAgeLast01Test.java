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
public class PatientCountGroupedByAgeLast01Test {

    private Db db;
    private PatientCountGroupedByAgeLast01 patientCount;
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
    public void it_evaluates_patient_count_grouped_per_age_at_last_01() throws Exception {
        prepareTestDb();
        this.patientCount = new PatientCountGroupedByAgeLast01();
        patientCount.evaluate();

        Map<String, String> results = results();

        assertThat(results.size()).isEqualTo(8);

        assertThat(results.get("0-19")).isEqualTo("2");
        assertThat(results.get("20-29")).isEqualTo("3");
        assertThat(results.get("30-39")).isEqualTo("0");
        assertThat(results.get("40-49")).isEqualTo("1");
        assertThat(results.get("50-59")).isEqualTo("0");
        assertThat(results.get("60-69")).isEqualTo("0");
        assertThat(results.get("70-79")).isEqualTo("0");
        assertThat(results.get("80-89")).isEqualTo("1");
    }

    @Test
    public void it_fills_up_missing_groups() throws Exception {
        prepareTestDbWithOneEntry();
        this.patientCount = new PatientCountGroupedByAgeLast01();
        patientCount.evaluate();

        Map<String, String> results = results();

        assertThat(results.size()).isEqualTo(14);

        assertThat(results.get("0-19")).isEqualTo("0");
        assertThat(results.get("20-29")).isEqualTo("0");
        assertThat(results.get("30-39")).isEqualTo("0");
        assertThat(results.get("40-49")).isEqualTo("0");
        assertThat(results.get("50-59")).isEqualTo("0");
        assertThat(results.get("60-69")).isEqualTo("0");
        assertThat(results.get("70-79")).isEqualTo("0");
        assertThat(results.get("80-89")).isEqualTo("0");
        assertThat(results.get("90-99")).isEqualTo("0");
        assertThat(results.get("100-109")).isEqualTo("0");
        assertThat(results.get("110-119")).isEqualTo("0");
        assertThat(results.get("120-129")).isEqualTo("0");
        assertThat(results.get("130-139")).isEqualTo("0");
        assertThat(results.get("140-149")).isEqualTo("1");
    }

    private ImmutableList<List<String>> testData() {
        return ImmutableList.<List<String>>builder()
                .add(ImmutableList.of("111", "0-19"))
                .add(ImmutableList.of("222", "20-29"))
                .add(ImmutableList.of("333", "0-19"))
                .add(ImmutableList.of("444", "20-29"))
                .add(ImmutableList.of("555", "80-89"))
                .add(ImmutableList.of("666", "40-49"))
                .add(ImmutableList.of("777", "20-29"))
                .add(ImmutableList.of("888", Keys.NO_DATA))
                .build();
    }

    private ImmutableList<List<String>> testDataWithOneEntry() {
        return ImmutableList.<List<String>>builder()
                .add(ImmutableList.of("111", "140-149"))
                .build();
    }

    private void prepareTestDb() {
        createTables();
        fillSourceTable();
    }

    private void prepareTestDbWithOneEntry() {
        createTables();
        fillSourceTableWithOneEntry();
    }

    private void fillSourceTable() {
        List<DbRow> rows = testData().stream().map(e -> {
            DbRow row = new DbRow();
            row.addCell(new DbCell("patient_index", e.get(0)));
            row.addCell(new DbCell("group_age_last_01", e.get(1)));
            return row;
        }).collect(Collectors.toList());

        db().writeRows("patients", rows);
    }

    private void fillSourceTableWithOneEntry() {
        List<DbRow> rows = testDataWithOneEntry().stream().map(e -> {
            DbRow row = new DbRow();
            row.addCell(new DbCell("patient_index", e.get(0)));
            row.addCell(new DbCell("group_age_last_01", e.get(1)));
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
                "group_age_last_01"
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
                "group_name",
                "value"
        );
        List<DbColumn> dbColumns = columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
        db().rebuildTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP, dbColumns);
    }

    private Map<String, String> results() throws Exception {
        Map<String, String> ret = new HashMap<>();

        ResultSet rs = db().query(String.format(
                "SELECT * FROM %s WHERE item='patient_count_grouped_by_age_last_01' " +
                        "AND name = '%s' " +
                        "AND unit = '%s'",
                Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP,
                Keys.TEXT_PATIENT_COUNT_GROUPED_BY_AGE_LAST_01,
                Keys.UNIT_NUMBER
        ));
        while (rs.next()) {
            ret.put(rs.getString("group_name"), rs.getString("value"));
        }
        return ret;
    }
}