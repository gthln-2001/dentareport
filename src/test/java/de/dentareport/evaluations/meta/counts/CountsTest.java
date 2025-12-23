package de.dentareport.evaluations.meta.counts;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

public class CountsTest {

    private Db db;
    private Counts counts;
    private String testTableName = "test";
    @Mocked
    DbConnection mockDbConnection;
    @Mocked
    Evaluation mockEvaluation;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
            mockEvaluation.dbTable();
            result = testTableName;
        }};
        counts = new Counts(mockEvaluation);
        setUpTestData();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_evaluates_patient_count() throws Exception {
        counts.evaluate();

        ResultSet result = getResultRow("patient_count");

        assertThat(result.getString("name")).isEqualTo(Keys.TEXT_COUNT_PATIENTS);
        assertThat(result.getString("value")).isEqualTo("3");
    }

    @Test
    public void it_evaluates_case_count() throws Exception {
        counts.evaluate();

        ResultSet result = getResultRow("case_count");

        assertThat(result.getString("name")).isEqualTo(Keys.TEXT_COUNT_CASES);
        assertThat(result.getString("value")).isEqualTo("5");
    }

    @Test
    public void it_evaluates_case_count_with_tooth_loss() throws Exception {
        counts.evaluate();

        ResultSet result = getResultRow("tooth_loss_count");

        assertThat(result.getString("name")).isEqualTo(Keys.TEXT_COUNT_TOOTH_LOSS);
        assertThat(result.getString("value")).isEqualTo("3");
    }

    @Test
    public void it_evaluates_case_count_with_rezementierung() throws Exception {
        counts.evaluate();

        ResultSet result = getResultRow("rezementierung_count");

        assertThat(result.getString("name")).isEqualTo(Keys.TEXT_COUNT_REZEMENTIERUNG);
        assertThat(result.getString("value")).isEqualTo("2");
    }

    @Test
    public void it_evaluates_case_count_with_endodontie() throws Exception {
        counts.evaluate();

        ResultSet result = getResultRow("endodontie_count");

        assertThat(result.getString("name")).isEqualTo(Keys.TEXT_COUNT_ENDODONTIE);
        assertThat(result.getString("value")).isEqualTo("4");
    }

    @Test
    public void it_evaluates_case_count_with_wurzelstift() throws Exception {
        counts.evaluate();

        ResultSet result = getResultRow("wurzelstift_count");

        assertThat(result.getString("name")).isEqualTo(Keys.TEXT_COUNT_WURZELSTIFT);
        assertThat(result.getString("value")).isEqualTo("1");
    }

    private ResultSet getResultRow(String item) throws Exception {
        ResultSet rs = db.query(String.format("SELECT * FROM %s_counts WHERE item='%s'", testTableName, item));
        rs.next();
        return rs;
    }

    private void setUpTestData() {
        db.rebuildTable(testTableName, columns());
        db.writeRows(testTableName, testData());
    }

    private List<DbRow> testData() {
        List<DbRow> dbRows = new ArrayList<>();

        // patient_index, tooth_loss, rezementierung, endodontie, wurzelstift
        dbRows.add(dbRow("1", "0", "1", "0", "0"));
        dbRows.add(dbRow("1", "1", "1", "1", "0"));
        dbRows.add(dbRow("2", "1", "0", "1", "1"));
        dbRows.add(dbRow("3", "0", "0", "1", "0"));
        dbRows.add(dbRow("2", "1", "0", "1", "0"));

        return dbRows;
    }

    private DbRow dbRow(String patient_index, String tooth_loss, String rezementierung, String endodontie, String wurzelstift) {
        DbRow row = new DbRow();
        row.addCell(new DbCell("patient_index", patient_index));
        row.addCell(new DbCell("censored__of__treatment_position_first_with_" + Keys.EXTRACTION + "_after_date_start_observation", tooth_loss));
        row.addCell(new DbCell("censored__of__treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation", rezementierung));
        row.addCell(new DbCell("censored__of__treatment_position_first_with_" + Keys.VITE_TREP_WK + "_after_date_start_observation", endodontie));
        row.addCell(new DbCell("censored__of__treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation", wurzelstift));

        return row;
    }

    private List<DbColumn> columns() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("censored__of__treatment_position_first_with_" + Keys.EXTRACTION + "_after_date_start_observation", "text"));
        dbColumns.add(new DbColumn("censored__of__treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation", "text"));
        dbColumns.add(new DbColumn("censored__of__treatment_position_first_with_" + Keys.VITE_TREP_WK + "_after_date_start_observation", "text"));
        dbColumns.add(new DbColumn("censored__of__treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation", "text"));
        return dbColumns;
    }
}