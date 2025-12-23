package de.dentareport.gui.tables;

import com.google.common.collect.ImmutableList;
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

public class GeneralPatientInformationGroupedByAgeTest {

    private Db db;
    private GeneralPatientInformationGroupedByAge generalPatientInformationGroupedByAge;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
        this.generalPatientInformationGroupedByAge = new GeneralPatientInformationGroupedByAge();
    }

    @AfterEach
    public void tearDown() {
        db.close();
    }

    @Test
    public void it_gets_table_data(@Mocked Translate mockTranslate) {
        new Expectations() {{
            mockTranslate.translate("name_a");
            result = "translated A";
            mockTranslate.translate("name_b");
            result = "translated B";
            mockTranslate.translate("unit_a");
            result = "unit a trans";
            mockTranslate.translate("unit_b");
            result = "unit b trans";
        }};
        setUpTestDb();
        fillTestDb();

        ObservableList<TableRow> result = generalPatientInformationGroupedByAge.data();

        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getColumn1()).isEqualTo("translated A");
        assertThat(result.get(0).getColumn2()).isEqualTo("unit a trans");
        assertThat(result.get(0).getColumn3()).isEqualTo("value_a_1");
        assertThat(result.get(0).getColumn4()).isEqualTo("value_a_2");
        assertThat(result.get(0).getColumn5()).isEqualTo("value_a_3");

        assertThat(result.get(1).getColumn1()).isEqualTo("translated B");
        assertThat(result.get(1).getColumn2()).isEqualTo("unit b trans");
        assertThat(result.get(1).getColumn3()).isEqualTo("value_b_1");
        assertThat(result.get(1).getColumn4()).isEqualTo("value_b_2");
        assertThat(result.get(1).getColumn5()).isEqualTo("value_b_3");
    }

    @Test
    public void it_gets_ordered_list_of_age_groups() {
        setUpTestDbForGroups();

        List<String> result = generalPatientInformationGroupedByAge.groups();

        assertThat(result.size()).isEqualTo(5);

        assertThat(result.get(0)).isEqualTo("0-19");
        assertThat(result.get(1)).isEqualTo("20-29");
        assertThat(result.get(2)).isEqualTo("40-49");
        assertThat(result.get(3)).isEqualTo("100-109");
        assertThat(result.get(4)).isEqualTo("110-119");
    }

    private void fillTestDb() {
        List<DbRow> rows = new ArrayList<>();
        for (List<String> data : testData()) {
            DbRow row = new DbRow();
            row.addCell(new DbCell("item", data.get(0)));
            row.addCell(new DbCell("name", data.get(1)));
            row.addCell(new DbCell("unit", data.get(2)));
            row.addCell(new DbCell("group_name", data.get(3)));
            row.addCell(new DbCell("value", data.get(4)));
            rows.add(row);
        }
        db.writeRows(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP, rows);
    }

    private List<List<String>> testData() {
        return ImmutableList.of(
                ImmutableList.of("item_b", "name_b", "unit_b", "30-39", "value_b_1"),
                ImmutableList.of("item_a", "name_a", "unit_a", "20-29", "value_a_2"),
                ImmutableList.of("item_a", "name_a", "unit_a", "0-19", "value_a_1"),
                ImmutableList.of("item_a", "name_a", "unit_a", "100-109", "value_a_3"),
                ImmutableList.of("item_b", "name_b", "unit_b", "40-49", "value_b_2"),
                ImmutableList.of("item_b", "name_b", "unit_b", "140-149", "value_b_3")
        );
    }

    private void setUpTestDb() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("item", "text"));
        dbColumns.add(new DbColumn("name", "text"));
        dbColumns.add(new DbColumn("unit", "text"));
        dbColumns.add(new DbColumn("value", "text"));
        dbColumns.add(new DbColumn("group_name", "text"));
        db.rebuildTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP, dbColumns);
    }

    private void setUpTestDbForGroups() {
        List<String> data = ImmutableList.of(
                "20-29",
                "0-19",
                "20-29",
                "40-49",
                "110-119",
                "100-109"
        );
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("group_name", "text"));
        db.rebuildTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP, dbColumns);

        List<DbRow> rows = new ArrayList<>();
        for (String group : data) {
            DbRow row = new DbRow();
            row.addCell(new DbCell("group_name", group));
            rows.add(row);
        }
        db.writeRows(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP, rows);
    }
}