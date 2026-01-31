package de.dentareport.gui.tables;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.elements.TableRow;
import de.dentareport.Translate;
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
public class AveragesTest {

    private Db db;
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
    public void tearDown() {
        db.close();
    }

    @Test
    public void it_creates_observable_list_for_averages(@Mocked Translate mockTranslate) {
        setupTable();
        setupTestData();

        new Expectations() {{
            mockTranslate.translate("name1", "42");
            result = "trans-name1";
            mockTranslate.translate("name2", "42");
            result = "trans-name2";
            mockTranslate.translate("unit1");
            result = "trans-unit1";
            mockTranslate.translate("unit2");
            result = "trans-unit2";
        }};

        ObservableList<TableRow> data = new Averages(ImmutableMap.of("evaluationId", "42")).data();

        assertThat(data.size()).isEqualTo(2);

        assertThat(data.get(0).getColumn1()).isEqualTo("trans-name1");
        assertThat(data.get(0).getColumn2()).isEqualTo("trans-unit1");
        assertThat(data.get(0).getColumn3()).isEqualTo("average1");
        assertThat(data.get(0).getColumn4()).isEqualTo("median1");
        assertThat(data.get(0).getColumn5()).isEqualTo("minimum1");
        assertThat(data.get(0).getColumn6()).isEqualTo("maximum1");

        assertThat(data.get(1).getColumn1()).isEqualTo("trans-name2");
        assertThat(data.get(1).getColumn2()).isEqualTo("trans-unit2");
        assertThat(data.get(1).getColumn3()).isEqualTo("average2");
        assertThat(data.get(1).getColumn4()).isEqualTo("median2");
        assertThat(data.get(1).getColumn5()).isEqualTo("minimum2");
        assertThat(data.get(1).getColumn6()).isEqualTo("maximum2");
    }

    private void setupTable() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("name", "text"));
        dbColumns.add(new DbColumn("unit", "text"));
        dbColumns.add(new DbColumn("average", "text"));
        dbColumns.add(new DbColumn("median", "text"));
        dbColumns.add(new DbColumn("minimum", "text"));
        dbColumns.add(new DbColumn("maximum", "text"));
        db.rebuildTable("evaluation_42_averages", dbColumns);
    }

    private void setupTestData() {
        List<DbRow> dbRows = new ArrayList<>();

        DbRow row1 = new DbRow();
        row1.addCell(new DbCell("name", "name1"));
        row1.addCell(new DbCell("unit", "unit1"));
        row1.addCell(new DbCell("average", "average1"));
        row1.addCell(new DbCell("median", "median1"));
        row1.addCell(new DbCell("minimum", "minimum1"));
        row1.addCell(new DbCell("maximum", "maximum1"));
        dbRows.add(row1);

        DbRow row2 = new DbRow();
        row2.addCell(new DbCell("name", "name2"));
        row2.addCell(new DbCell("unit", "unit2"));
        row2.addCell(new DbCell("average", "average2"));
        row2.addCell(new DbCell("median", "median2"));
        row2.addCell(new DbCell("minimum", "minimum2"));
        row2.addCell(new DbCell("maximum", "maximum2"));
        dbRows.add(row2);

        db.writeRows("evaluation_42_averages", dbRows);
    }
}