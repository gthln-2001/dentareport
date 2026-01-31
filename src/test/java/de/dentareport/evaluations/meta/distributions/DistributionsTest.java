package de.dentareport.evaluations.meta.distributions;

import com.google.common.collect.ImmutableMap;
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
import java.util.Map;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DistributionsTest {

    private Db db;
    private Distributions distributions;
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
        distributions = new Distributions(mockEvaluation);
        setUpTestData();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_evaluates_distribution_for_gender() throws Exception {
        distributions.evaluate();

        List<Map<String, String>> result = getResultRow("gender");

        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).get("item")).isEqualTo("gender");
        assertThat(result.get(0).get("name")).isEqualTo(Keys.TEXT_GENDER);
        assertThat(result.get(0).get("value")).isEqualTo("f");
        assertThat(result.get(0).get("value_count")).isEqualTo("3");

        assertThat(result.get(1).get("item")).isEqualTo("gender");
        assertThat(result.get(1).get("name")).isEqualTo(Keys.TEXT_GENDER);
        assertThat(result.get(1).get("value")).isEqualTo("m");
        assertThat(result.get(1).get("value_count")).isEqualTo("2");

        assertThat(result.get(2).get("item")).isEqualTo("gender");
        assertThat(result.get(2).get("name")).isEqualTo(Keys.TEXT_GENDER);
        assertThat(result.get(2).get("value")).isEqualTo("?");
        assertThat(result.get(2).get("value_count")).isEqualTo("2");
    }

    @Test
    public void it_evaluates_distribution_for_insurance() throws Exception {
        distributions.evaluate();

        List<Map<String, String>> result = getResultRow("insurance");

        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).get("item")).isEqualTo("insurance");
        assertThat(result.get(0).get("name")).isEqualTo(Keys.TEXT_INSURANCE);
        assertThat(result.get(0).get("value")).isEqualTo("g");
        assertThat(result.get(0).get("value_count")).isEqualTo("3");

        assertThat(result.get(1).get("item")).isEqualTo("insurance");
        assertThat(result.get(1).get("name")).isEqualTo(Keys.TEXT_INSURANCE);
        assertThat(result.get(1).get("value")).isEqualTo("p");
        assertThat(result.get(1).get("value_count")).isEqualTo("2");

        assertThat(result.get(2).get("item")).isEqualTo("insurance");
        assertThat(result.get(2).get("name")).isEqualTo(Keys.TEXT_INSURANCE);
        assertThat(result.get(2).get("value")).isEqualTo("?");
        assertThat(result.get(2).get("value_count")).isEqualTo("2");
    }

    @Test
    public void it_evaluates_distribution_for_toothtype() throws Exception {
        distributions.evaluate();

        List<Map<String, String>> result = getResultRow("toothtype");

        assertThat(result.size()).isEqualTo(4);

        assertThat(result.get(0).get("item")).isEqualTo("toothtype");
        assertThat(result.get(0).get("name")).isEqualTo(Keys.TEXT_TOOTHTYPES);
        assertThat(result.get(0).get("value")).isEqualTo("a");
        assertThat(result.get(0).get("value_count")).isEqualTo("2");

        assertThat(result.get(1).get("item")).isEqualTo("toothtype");
        assertThat(result.get(1).get("name")).isEqualTo(Keys.TEXT_TOOTHTYPES);
        assertThat(result.get(1).get("value")).isEqualTo("b");
        assertThat(result.get(1).get("value_count")).isEqualTo("4");

        assertThat(result.get(2).get("item")).isEqualTo("toothtype");
        assertThat(result.get(2).get("name")).isEqualTo(Keys.TEXT_TOOTHTYPES);
        assertThat(result.get(2).get("value")).isEqualTo("c");
        assertThat(result.get(2).get("value_count")).isEqualTo("2");

        assertThat(result.get(3).get("item")).isEqualTo("toothtype");
        assertThat(result.get(3).get("name")).isEqualTo(Keys.TEXT_TOOTHTYPES);
        assertThat(result.get(3).get("value")).isEqualTo("?");
        assertThat(result.get(3).get("value_count")).isEqualTo("3");
    }

    @Test
    public void it_evaluates_distribution_for_endstaendigkeit() throws Exception {
        distributions.evaluate();

        List<Map<String, String>> result =
                getResultRow("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation");

        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).get("item"))
                .isEqualTo("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation");
        assertThat(result.get(0).get("name")).isEqualTo(Keys.TEXT_DISTRIBUTION_ENDSTAENDIG);
        assertThat(result.get(0).get("value")).isEqualTo("x");
        assertThat(result.get(0).get("value_count")).isEqualTo("3");

        assertThat(result.get(1).get("item"))
                .isEqualTo("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation");
        assertThat(result.get(1).get("name")).isEqualTo(Keys.TEXT_DISTRIBUTION_ENDSTAENDIG);
        assertThat(result.get(1).get("value")).isEqualTo("y");
        assertThat(result.get(1).get("value_count")).isEqualTo("5");

        assertThat(result.get(2).get("item"))
                .isEqualTo("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation");
        assertThat(result.get(2).get("name")).isEqualTo(Keys.TEXT_DISTRIBUTION_ENDSTAENDIG);
        assertThat(result.get(2).get("value")).isEqualTo("?");
        assertThat(result.get(2).get("value_count")).isEqualTo("3");
    }

    @Test
    public void it_evaluates_distribution_for_toothcontacts() throws Exception {
        distributions.evaluate();

        List<Map<String, String>> result =
                getResultRow("toothcontacts__of__evidence_01_position_first_after_date_start_observation");

        assertThat(result.size()).isEqualTo(4);

        assertThat(result.get(0).get("item"))
                .isEqualTo("toothcontacts__of__evidence_01_position_first_after_date_start_observation");
        assertThat(result.get(0).get("name")).isEqualTo(Keys.TEXT_DISTRIBUTION_TOOTHCONTACTS);
        assertThat(result.get(0).get("value")).isEqualTo("0");
        assertThat(result.get(0).get("value_count")).isEqualTo("2");

        assertThat(result.get(1).get("item"))
                .isEqualTo("toothcontacts__of__evidence_01_position_first_after_date_start_observation");
        assertThat(result.get(1).get("name")).isEqualTo(Keys.TEXT_DISTRIBUTION_TOOTHCONTACTS);
        assertThat(result.get(1).get("value")).isEqualTo("1");
        assertThat(result.get(1).get("value_count")).isEqualTo("3");

        assertThat(result.get(2).get("item"))
                .isEqualTo("toothcontacts__of__evidence_01_position_first_after_date_start_observation");
        assertThat(result.get(2).get("name")).isEqualTo(Keys.TEXT_DISTRIBUTION_TOOTHCONTACTS);
        assertThat(result.get(2).get("value")).isEqualTo("2");
        assertThat(result.get(2).get("value_count")).isEqualTo("3");

        assertThat(result.get(3).get("item"))
                .isEqualTo("toothcontacts__of__evidence_01_position_first_after_date_start_observation");
        assertThat(result.get(3).get("name")).isEqualTo(Keys.TEXT_DISTRIBUTION_TOOTHCONTACTS);
        assertThat(result.get(3).get("value")).isEqualTo("?");
        assertThat(result.get(3).get("value_count")).isEqualTo("3");
    }

    private List<Map<String, String>> getResultRow(String item) throws Exception {
        List<Map<String, String>> ret = new ArrayList<>();
        ResultSet rs = db.query(String.format(
                "SELECT * FROM %s_distributions WHERE item='%s'",
                testTableName,
                item
        ));
        while (rs.next()) {
            ret.add(ImmutableMap.of(
                    "item", rs.getString("item"),
                    "name", rs.getString("name"),
                    "value", rs.getString("value"),
                    "value_count", rs.getString("value_count")
            ));
        }
        return ret;
    }

    private void setUpTestData() {
        db.rebuildTable(testTableName, columns());
        db.writeRows(testTableName, testData());
    }

    private List<DbRow> testData() {
        List<DbRow> dbRows = new ArrayList<>();

        // patient_index, gender, insurance, toothtype, endstaendigkeit, toothcontacts
        dbRows.add(dbRow("1", "m", "p", "a", "y", "2"));
        dbRows.add(dbRow("1", "m", "p", "b", "y", "1"));
        dbRows.add(dbRow("2", "f", "p", "c", "x", "0"));
        dbRows.add(dbRow("3", "f", "g", "a", "y", "2"));
        dbRows.add(dbRow("3", "f", "g", "b", "x", "1"));
        dbRows.add(dbRow("4", "f", "g", "b", "y", "0"));
        dbRows.add(dbRow("5", "m", "g", "c", "x", "2"));
        dbRows.add(dbRow("5", "m", "g", "b", "y", "1"));
        dbRows.add(dbRow("6", "no_data", "no_data", "no_data", "no_data", "no_data"));
        dbRows.add(dbRow("7", "", "BEFUND", "no_data", "", ""));
        dbRows.add(dbRow("7", "", "BEFUND", "", "", ""));

        return dbRows;
    }

    private DbRow dbRow(String patient_index,
                        String gender,
                        String insurance,
                        String toothtype,
                        String endstaendigkeit,
                        String toothcontacts) {
        DbRow row = new DbRow();

        row.addCell(new DbCell("patient_index", patient_index));
        row.addCell(new DbCell("gender", gender));
        row.addCell(new DbCell("insurance", insurance));
        row.addCell(new DbCell("toothtype", toothtype));
        row.addCell(new DbCell("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation", endstaendigkeit));
        row.addCell(new DbCell("toothcontacts__of__evidence_01_position_first_after_date_start_observation", toothcontacts));

        return row;
    }

    private List<DbColumn> columns() {
        List<DbColumn> dbColumns = new ArrayList<>();

        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("gender", "text"));
        dbColumns.add(new DbColumn("insurance", "text"));
        dbColumns.add(new DbColumn("toothtype", "text"));
        dbColumns.add(new DbColumn("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation", "text"));
        dbColumns.add(new DbColumn("toothcontacts__of__evidence_01_position_first_after_date_start_observation", "text"));

        return dbColumns;
    }
}