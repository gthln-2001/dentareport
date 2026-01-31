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

// TODO: TEST?
public class GeneralPatientInformationAveragesTest {

    private Db db;
    private GeneralPatientInformationAverages generalPatientInformationAverages;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
        this.generalPatientInformationAverages = new GeneralPatientInformationAverages();
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

        ObservableList<TableRow> result = generalPatientInformationAverages.data();

        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getColumn1()).isEqualTo("translated A");
        assertThat(result.get(0).getColumn2()).isEqualTo("unit a trans");
        assertThat(result.get(0).getColumn3()).isEqualTo("5");
        assertThat(result.get(0).getColumn4()).isEqualTo("6");
        assertThat(result.get(0).getColumn5()).isEqualTo("7");
        assertThat(result.get(0).getColumn6()).isEqualTo("8");

        assertThat(result.get(1).getColumn1()).isEqualTo("translated B");
        assertThat(result.get(1).getColumn2()).isEqualTo("unit b trans");
        assertThat(result.get(1).getColumn3()).isEqualTo("1");
        assertThat(result.get(1).getColumn4()).isEqualTo("2");
        assertThat(result.get(1).getColumn5()).isEqualTo("3");
        assertThat(result.get(1).getColumn6()).isEqualTo("4");
    }

    private List<List<String>> testData() {
        return ImmutableList.of(
                ImmutableList.of("item_b", "name_b", "unit_b", "1", "2", "3", "4"),
                ImmutableList.of("item_a", "name_a", "unit_a", "5", "6", "7", "8")
        );
    }

    private void fillTestDb() {
        List<DbRow> rows = new ArrayList<>();
        for (List<String> data : testData()) {
            DbRow row = new DbRow();
            row.addCell(new DbCell("item", data.get(0)));
            row.addCell(new DbCell("name", data.get(1)));
            row.addCell(new DbCell("unit", data.get(2)));
            row.addCell(new DbCell("average", data.get(3)));
            row.addCell(new DbCell("median", data.get(4)));
            row.addCell(new DbCell("minimum", data.get(5)));
            row.addCell(new DbCell("maximum", data.get(6)));
            rows.add(row);
        }
        db.writeRows(Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES, rows);
    }

    private void setUpTestDb() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("item", "text"));
        dbColumns.add(new DbColumn("name", "text"));
        dbColumns.add(new DbColumn("unit", "text"));
        dbColumns.add(new DbColumn("average", "text"));
        dbColumns.add(new DbColumn("median", "text"));
        dbColumns.add(new DbColumn("minimum", "text"));
        dbColumns.add(new DbColumn("maximum", "text"));
        db.rebuildTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES, dbColumns);
    }
}