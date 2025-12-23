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

public class DmftLast01GroupedByAgeLast01Test {

    private Db db;
    private DmftLast01GroupedByAgeLast01 dmft;
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
    public void it_evaluates_average_dmft_in_last_01_grouped_per_patient_age() throws Exception {
        prepareTestDb();
        this.dmft = new DmftLast01GroupedByAgeLast01();
        dmft.evaluate();

        Map<String, String> results = results();

        assertThat(results.size()).isEqualTo(3);

        assertThat(results.get("0-19")).isEqualTo("11.5");
        assertThat(results.get("20-29")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("30-39")).isEqualTo("14.0");
    }

    @Test
    public void it_fills_up_missing_groups() throws Exception {
        prepareTestDbWithOneEntry();
        this.dmft = new DmftLast01GroupedByAgeLast01();
        dmft.evaluate();

        Map<String, String> results = results();

        assertThat(results.size()).isEqualTo(14);

        assertThat(results.get("0-19")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("20-29")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("30-39")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("40-49")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("50-59")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("60-69")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("70-79")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("80-89")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("90-99")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("100-109")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("110-119")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("120-129")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("130-139")).isEqualTo(Keys.NO_DATA);
        assertThat(results.get("140-149")).isEqualTo("42.0");
    }

    private ImmutableList<List<String>> testDataPatients() {
        return ImmutableList.<List<String>>builder()
                .add(ImmutableList.of("111", "0-19"))
                .add(ImmutableList.of("222", "30-39"))
                .add(ImmutableList.of("333", "0-19"))
                .add(ImmutableList.of("444", "30-39"))
                .add(ImmutableList.of("555", Keys.NO_DATA))
                .build();
    }

    private ImmutableList<List<String>> testData01() {
        return ImmutableList.<List<String>>builder()
                .add(ImmutableList.of("111", "1999-10-01", "1000")) // should be ignored
                .add(ImmutableList.of("111", "2010-10-01", "10"))
                .add(ImmutableList.of("111", "2000-10-01", "1000")) // should be ignored
                .add(ImmutableList.of("222", "2000-10-01", "11"))
                .add(ImmutableList.of("333", "2000-10-01", "13"))
                .add(ImmutableList.of("444", "2000-10-01", "17"))
                .add(ImmutableList.of("555", "2000-10-01", "18")) // should be ignored, patient without date of birht
                .add(ImmutableList.of("666", "2000-10-01", "19")) // should be ignored, evidence without patient
                .build();
    }

    private void prepareTestDbWithOneEntry() {
        createTables();
        fillSourceTablesWithOneEntry();
    }

    private void fillSourceTablesWithOneEntry() {
        DbRow rowPatient = new DbRow();
        rowPatient.addCell(new DbCell("patient_index", "111"));
        rowPatient.addCell(new DbCell("group_age_last_01", "140-149"));

        db().writeRow("patients", rowPatient);

        DbRow row01 = new DbRow();
        row01.addCell(new DbCell("patient_index", "111"));
        row01.addCell(new DbCell("date", "2000-10-01"));
        row01.addCell(new DbCell("dmft", "42"));

        db().writeRow("evidences_01", row01);
    }

    private void prepareTestDb() {
        createTables();
        fillSourceTables();
    }

    private void fillSourceTables() {
        fillSourceTablePatients();
        fillSourceTable01();
    }

    private void fillSourceTablePatients() {
        List<DbRow> rows = testDataPatients().stream().map(e -> {
            DbRow row = new DbRow();
            row.addCell(new DbCell("patient_index", e.get(0)));
            row.addCell(new DbCell("group_age_last_01", e.get(1)));
            return row;
        }).collect(Collectors.toList());

        db().writeRows("patients", rows);
    }

    private void fillSourceTable01() {
        List<DbRow> rows = testData01().stream().map(e -> {
            DbRow row = new DbRow();
            row.addCell(new DbCell("patient_index", e.get(0)));
            row.addCell(new DbCell("date", e.get(1)));
            row.addCell(new DbCell("dmft", e.get(2)));
            return row;
        }).collect(Collectors.toList());

        db().writeRows("evidences_01", rows);
    }

    private void createTables() {
        createSourceTables();
        createTargetTable();
    }

    private void createSourceTables() {
        createSourceTablePatients();
        createSourceTable01();
    }

    private void createSourceTablePatients() {
        List<String> columns = ImmutableList.of(
                "patient_index",
                "group_age_last_01"
        );
        List<DbColumn> dbColumns = columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
        db().rebuildTable("patients", dbColumns);
    }

    private void createSourceTable01() {
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
                "SELECT * FROM %s WHERE item='average_dmft_grouped_by_age_last_01' " +
                        "AND name = '%s' " +
                        "AND unit = '%s'",
                Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP,
                Keys.TEXT_AVERAGE_DMFT_GROUPED_BY_AGE_LAST_01,
                Keys.UNIT_DMFT
        ));
        while (rs.next()) {
            ret.put(rs.getString("group_name"), rs.getString("value"));
        }
        return ret;
    }
}