package de.dentareport.gui.tables;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.Translate;
import de.dentareport.gui.elements.TableRow;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.*;
import javafx.collections.ObservableList;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class GroupsSizesTest {

    private Db db;
    private GroupsSizes tableData;
    @Mocked
    DbConnection mockDbConnection;
    @Mocked
    Translate mockTranslate;

    @BeforeEach
    public void setUp() throws Exception {
        tableData = new GroupsSizes();
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
    }

    @AfterEach
    public void tearDown() {
        db.close();
    }

    @Test
    public void it_creates_observable_list_for_group_sizes_of_age_at_start_observation() {
        setExpectations();
        setUpTestData(
                "groups__of__age_at_event_start_observation",
                "age__at__event_start_observation"
        );

        validateResult(tableData.data(ImmutableMap.of("evaluationId", "test",
                                                      "dependency", Keys.GUI_TEXT_AGE_START_OBSERVATION)));
    }

    @Test
    public void it_creates_observable_list_for_group_sizes_of_toothcount_in_jaw() {
        setExpectations();
        setUpTestData(
                "groups__of__toothcount_in_jaw_of_evidence_01_position_first_after_date_start_observation",
                "toothcount_in_jaw__of__evidence_01_position_first_after_date_start_observation"
        );

        validateResult(tableData.data(ImmutableMap.of("evaluationId", "test",
                                                      "dependency", Keys.GUI_TEXT_TOOTHCOUNT_IN_JAW)));
    }

    @Test
    public void it_creates_observable_list_for_group_sizes_of_dmft() {
        setExpectations();
        setUpTestData(
                "groups__of__dmft_of_evidence_01_position_last_before_date_start_observation",
                "dmft__of__evidence_01_position_last_before_date_start_observation"
        );

        validateResult(tableData.data(ImmutableMap.of("evaluationId", "test",
                                                      "dependency", Keys.GUI_TEXT_DMFT)));
    }

    @Test
    public void it_creates_observable_list_for_group_sizes_of_gender() {
        setExpectations();
        setUpTestData("gender");

        validateResult(tableData.data(ImmutableMap.of("evaluationId", "test",
                                                      "dependency", Keys.GUI_TEXT_GENDER)));
    }

    @Test
    public void it_creates_observable_list_for_group_sizes_of_endstaendiger_pfeiler() {
        setExpectations();
        setUpTestData("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation");

        validateResult(tableData.data(ImmutableMap.of("evaluationId", "test",
                                                      "dependency", Keys.GUI_TEXT_ENDSTAENDIGER_PFEILER)));
    }

    @Test
    public void it_creates_observable_list_for_group_sizes_of_number_of_toothcontacts() {
        setExpectations();
        setUpTestData("toothcontacts__of__evidence_01_position_first_after_date_start_observation");

        validateResult(tableData.data(ImmutableMap.of("evaluationId", "test",
                                                      "dependency", Keys.GUI_TEXT_TOOTHCONTACTS)));
    }

    @Test
    public void it_creates_observable_list_for_group_sizes_of_toothtype() {
        setExpectations();
        setUpTestData("toothtype");

        validateResult(tableData.data(ImmutableMap.of("evaluationId", "test",
                                                      "dependency", Keys.GUI_TEXT_TOOTHTYPE)));
    }

    @Test
    public void it_gets_total_item_count() {
        new Expectations() {{
            mockTranslate.translate(Keys.GUI_TEXT_TOTAL);
            result = Keys.GUI_TEXT_TOTAL;
        }};
        setUpTestData("foo");

        ObservableList<TableRow> result = tableData.data(ImmutableMap.of("evaluationId", "test",
                                                                         "dependency", Keys.GUI_TEXT_NO_DEPENDENCY));

        assertThat(result.size()).isEqualTo(1);

        assertThat(result.get(0).getColumn1()).isEqualTo(Keys.GUI_TEXT_TOTAL);
        assertThat(result.get(0).getColumn2()).isEqualTo("7");
    }

    private void setExpectations() {
        new Expectations() {{
            mockTranslate.translate("1-2");
            result = "1-2 trans";
            mockTranslate.translate("3-5");
            result = "3-5 trans";
            mockTranslate.translate("6-8");
            result = "6-8 trans";
        }};
    }

    private void validateResult(ObservableList<TableRow> result) {
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getColumn1()).isEqualTo("1-2 trans");
        assertThat(result.get(0).getColumn2()).isEqualTo("2");

        assertThat(result.get(1).getColumn1()).isEqualTo("3-5 trans");
        assertThat(result.get(1).getColumn2()).isEqualTo("1");

        assertThat(result.get(2).getColumn1()).isEqualTo("6-8 trans");
        assertThat(result.get(2).getColumn2()).isEqualTo("2");
    }

    private void setUpTestData(String group) {
        setUpTestData(group, "irrelevant_for_this_test");
    }

    private void setUpTestData(String group, String orderBy) {
        List<List<String>> data = ImmutableList.of(
                ImmutableList.of("3-5", "3"),
                ImmutableList.of("1-2", "1"),
                ImmutableList.of("1-2", "2"),
                ImmutableList.of("6-8", "8"),
                ImmutableList.of("6-8", "6"),
                ImmutableList.of("no_data", "1"), // not counted when grouped
                ImmutableList.of("", "1") // not counted when grouped
        );
        setUpTable(group, orderBy);
        List<DbRow> dbRows = new ArrayList<>();
        DbRow row;
        for (List<String> rowData : data) {
            row = new DbRow();
            row.addCell(new DbCell(group, rowData.get(0)));
            row.addCell(new DbCell(orderBy, rowData.get(1)));
            dbRows.add(row);
        }

        db.writeRows("evaluation_test", dbRows);
    }

    private void setUpTable(String col1, String col2) {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn(col1, "text"));
        dbColumns.add(new DbColumn(col2, "text"));
        db.rebuildTable("evaluation_test", dbColumns);
    }
}