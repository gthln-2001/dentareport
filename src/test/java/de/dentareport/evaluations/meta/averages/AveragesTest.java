package de.dentareport.evaluations.meta.averages;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.*;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class AveragesTest {

    private Db db;
    private Map<String, Map<String, String>> metaData;
    private String testTableName = "test";
    @Mocked
    DbConnection mockDbConnection;
    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    Items mockItems;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
            mockEvaluation.dbTable();
            result = testTableName;
            mockItems.perPatient();
            result = testItemsPerPatient();
            mockItems.perCase();
            result = testItemsPerCase();
        }};
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_writes_name_and_unit_per_item_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_patient_1").get("name")).isEqualTo("per_patient_1_name");
        assertThat(metaData.get("per_patient_2").get("name")).isEqualTo("per_patient_2_name");
        assertThat(metaData.get("per_case_1").get("name")).isEqualTo("per_case_1_name");
        assertThat(metaData.get("per_case_2").get("name")).isEqualTo("per_case_2_name");

        assertThat(metaData.get("per_patient_1").get("unit")).isEqualTo("per_patient_1_unit");
        assertThat(metaData.get("per_patient_2").get("unit")).isEqualTo("per_patient_2_unit");
        assertThat(metaData.get("per_case_1").get("unit")).isEqualTo("per_case_1_unit");
        assertThat(metaData.get("per_case_2").get("unit")).isEqualTo("per_case_2_unit");
    }

    @Test
    public void it_writes_name_and_unit_per_item_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_patient_1").get("name")).isEqualTo("per_patient_1_name");
        assertThat(metaData.get("per_patient_2").get("name")).isEqualTo("per_patient_2_name");
        assertThat(metaData.get("per_case_1").get("name")).isEqualTo("per_case_1_name");
        assertThat(metaData.get("per_case_2").get("name")).isEqualTo("per_case_2_name");

        assertThat(metaData.get("per_patient_1").get("unit")).isEqualTo("per_patient_1_unit");
        assertThat(metaData.get("per_patient_2").get("unit")).isEqualTo("per_patient_2_unit");
        assertThat(metaData.get("per_case_1").get("unit")).isEqualTo("per_case_1_unit");
        assertThat(metaData.get("per_case_2").get("unit")).isEqualTo("per_case_2_unit");
    }

    @Test
    public void it_evaluates_average_per_patient_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_patient_1").get("average")).isEqualTo("2.5");
        assertThat(metaData.get("per_patient_2").get("average")).isEqualTo("6.3");
    }

    @Test
    public void it_evaluates_average_per_patient_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_patient_1").get("average")).isEqualTo("2.0");
        assertThat(metaData.get("per_patient_2").get("average")).isEqualTo("5.8");
    }

    @Test
    public void it_evaluates_average_per_case_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_case_1").get("average")).isEqualTo("14.63");
        assertThat(metaData.get("per_case_2").get("average")).isEqualTo("33.08");
    }

    @Test
    public void it_evaluates_average_per_case_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_case_1").get("average")).isEqualTo("13.33");
        assertThat(metaData.get("per_case_2").get("average")).isEqualTo("30.77");
    }

    @Test
    public void it_evaluates_minimum_per_patient_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_patient_1").get("minimum")).isEqualTo("1");
        assertThat(metaData.get("per_patient_2").get("minimum")).isEqualTo("4.7");
    }

    @Test
    public void it_evaluates_minimum_per_patient_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_patient_1").get("minimum")).isEqualTo("1");
        assertThat(metaData.get("per_patient_2").get("minimum")).isEqualTo("4.7");
    }

    @Test
    public void it_evaluates_minimum_per_case_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_case_1").get("minimum")).isEqualTo("1");
        assertThat(metaData.get("per_case_2").get("minimum")).isEqualTo("10.1");
    }

    @Test
    public void it_evaluates_minimum_per_case_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_case_1").get("minimum")).isEqualTo("2");
        assertThat(metaData.get("per_case_2").get("minimum")).isEqualTo("11.2");
    }

    @Test
    public void it_evaluates_maximum_per_patient_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_patient_1").get("maximum")).isEqualTo("4");
        assertThat(metaData.get("per_patient_2").get("maximum")).isEqualTo("7.8");
    }

    @Test
    public void it_evaluates_maximum_per_patient_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_patient_1").get("maximum")).isEqualTo("3");
        assertThat(metaData.get("per_patient_2").get("maximum")).isEqualTo("6.9");
    }

    @Test
    public void it_evaluates_maximum_per_case_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_case_1").get("maximum")).isEqualTo("89");
        assertThat(metaData.get("per_case_2").get("maximum")).isEqualTo("170.8");
    }

    @Test
    public void it_evaluates_maximum_per_case_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_case_1").get("maximum")).isEqualTo("89");
        assertThat(metaData.get("per_case_2").get("maximum")).isEqualTo("170.8");
    }

    @Test
    public void it_evaluates_median_per_patient_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_patient_1").get("median")).isEqualTo("2.5");
        assertThat(metaData.get("per_patient_2").get("median")).isEqualTo("6.35");
    }

    @Test
    public void it_evaluates_median_per_patient_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_patient_1").get("median")).isEqualTo("2.0");
        assertThat(metaData.get("per_patient_2").get("median")).isEqualTo("5.8");
    }

    @Test
    public void it_evaluates_median_per_case_for_data_with_even_number_of_rows() throws Exception {
        metaData = evaluateMetaData("even");

        assertThat(metaData.get("per_case_1").get("median")).isEqualTo("4.5");
        assertThat(metaData.get("per_case_2").get("median")).isEqualTo("13.95");
    }

    @Test
    public void it_evaluates_median_per_case_for_data_with_uneven_number_of_rows() throws Exception {
        metaData = evaluateMetaData("uneven");

        assertThat(metaData.get("per_case_1").get("median")).isEqualTo("4.0");
        assertThat(metaData.get("per_case_2").get("median")).isEqualTo("13.4");
    }

    private List<DbRow> testDataEven() {
        List<DbRow> dbRows = new ArrayList<>();

        // patient_index, per_patient_1, per_patient_2, per_case_1, per_case_2
        dbRows.add(row("01", "1", "4.7", "1", "10.1"));
        dbRows.add(row("01", "1", "4.7", "2", "11.2"));
        dbRows.add(row("01", "1", "4.7", "3", "12.3"));
        dbRows.add(row("01", "1", "4.7", "4", "13.4"));
        dbRows.add(row("02", "2", "5.8", "5", "14.5"));
        dbRows.add(row("02", "2", "5.8", "6", "15.6"));
        dbRows.add(row("03", "3", "6.9", "7", "16.7"));
        dbRows.add(row("04", "4", "7.8", "89", "170.8"));

        return dbRows;
    }

    private List<DbRow> testDataUneven() {
        List<DbRow> dbRows = new ArrayList<>();

        // patient_index, per_patient_1, per_patient_2, per_case_1, per_case_2
        dbRows.add(row("01", "1", "4.7", "2", "11.2"));
        dbRows.add(row("01", "1", "4.7", "2", "11.2"));
        dbRows.add(row("01", "1", "4.7", "2", "11.2"));
        dbRows.add(row("01", "1", "4.7", "3", "12.3"));
        dbRows.add(row("01", "1", "4.7", "4", "13.4"));
        dbRows.add(row("02", "2", "5.8", "5", "14.5"));
        dbRows.add(row("02", "2", "5.8", "6", "15.6"));
        dbRows.add(row("03", "3", "6.9", "7", "16.7"));
        dbRows.add(row("03", "3", "6.9", "89", "170.8"));

        return dbRows;
    }

    private Map<String, Map<String, String>> evaluateMetaData(String testData) throws Exception {
        if (Objects.equals(testData, "even")) {
            prepareTestTableEven();
        } else {
            prepareTestTableUneven();
        }

        Averages averages = new Averages(mockEvaluation);
        averages.evaluate();
        return evaluatedMetaData();
    }

    private Map<String, Map<String, String>> evaluatedMetaData() throws Exception {
        Map<String, Map<String, String>> ret = new HashMap<>();
        ResultSet rs = db.query(String.format("SELECT * FROM %s_averages", testTableName));
        while (rs.next()) {
            Map<String, String> data = new HashMap<>();
            data.put("name", rs.getString("name"));
            data.put("unit", rs.getString("unit"));
            data.put("average", rs.getString("average"));
            data.put("minimum", rs.getString("minimum"));
            data.put("maximum", rs.getString("maximum"));
            data.put("median", rs.getString("median"));
            ret.put(rs.getString("item"), data);
        }
        return ret;
    }

    private DbRow row(String patient_index, String per_patient_1, String paer_patient_2, String per_case_1, String per_case_2) {
        DbRow row = new DbRow();
        row.addCell(new DbCell("patient_index", patient_index));
        row.addCell(new DbCell("per_patient_1", per_patient_1));
        row.addCell(new DbCell("per_patient_2", paer_patient_2));
        row.addCell(new DbCell("per_case_1", per_case_1));
        row.addCell(new DbCell("per_case_2", per_case_2));
        return row;
    }

    private List<Map<String, String>> testItemsPerPatient() {
        return ImmutableList.of(
                ImmutableMap.of(
                        "column", "per_patient_1",
                        "unit", "per_patient_1_unit",
                        "name", "per_patient_1_name"
                ),
                ImmutableMap.of(
                        "column", "per_patient_2",
                        "unit", "per_patient_2_unit",
                        "name", "per_patient_2_name"
                )
        );
    }

    private List<Map<String, String>> testItemsPerCase() {
        return ImmutableList.of(
                ImmutableMap.of(
                        "column", "per_case_1",
                        "unit", "per_case_1_unit",
                        "name", "per_case_1_name"
                ),
                ImmutableMap.of(
                        "column", "per_case_2",
                        "unit", "per_case_2_unit",
                        "name", "per_case_2_name"
                )
        );
    }

    private void prepareTestTableEven() {
        db.rebuildTable(testTableName, columns());
        db.writeRows(testTableName, testDataEven());
    }

    private void prepareTestTableUneven() {
        db.rebuildTable(testTableName, columns());
        db.writeRows(testTableName, testDataUneven());
    }

    private List<DbColumn> columns() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("per_patient_1", "text"));
        dbColumns.add(new DbColumn("per_patient_2", "text"));
        dbColumns.add(new DbColumn("per_case_1", "text"));
        dbColumns.add(new DbColumn("per_case_2", "text"));
        return dbColumns;
    }
}