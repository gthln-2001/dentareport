package de.dentareport.gui.tables;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.elements.TableRow;
import de.dentareport.Translate;
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
public class CountAndDistributionTest {

    private Db db;
    @Mocked
    Translate mockTranslate;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
            mockTranslate.translate(Keys.TEXT_COUNT_PATIENTS, "42");
            result = "t_count_patients";
            mockTranslate.translate(Keys.TEXT_GENDER, "42");
            result = "t_gender";
            mockTranslate.translate("gender1");
            result = "t_gender1";
            mockTranslate.translate("gender2");
            result = "t_gender2";
            mockTranslate.translate(Keys.TEXT_INSURANCE, "42");
            result = "t_insurance";
            mockTranslate.translate("insurance1");
            result = "t_insurance1";
            mockTranslate.translate("insurance2");
            result = "t_insurance2";
            mockTranslate.translate(Keys.TEXT_COUNT_CASES, "42");
            result = "t_count_cases";
            mockTranslate.translate(Keys.TEXT_COUNT_TOOTH_LOSS, "42");
            result = "t_count_tooth_loss";
            mockTranslate.translate(Keys.TEXT_COUNT_REZEMENTIERUNG, "42");
            result = "t_count_rezementierung";
            mockTranslate.translate(Keys.TEXT_COUNT_ENDODONTIE, "42");
            result = "t_count_endodontie";
            mockTranslate.translate(Keys.TEXT_COUNT_WURZELSTIFT, "42");
            result = "t_count_wurzelstift";
            mockTranslate.translate(Keys.TEXT_TOOTHTYPES, "42");
            result = "t_toothtypes";
            mockTranslate.translate("toothtype1");
            result = "t_toothtype1";
            mockTranslate.translate("toothtype2");
            result = "t_toothtype2";
            mockTranslate.translate(Keys.TEXT_DISTRIBUTION_ENDSTAENDIG, "42");
            result = "t_endstaendig";
            mockTranslate.translate("endstaendig1");
            result = "t_endstaendig1";
            mockTranslate.translate("endstaendig2");
            result = "t_endstaendig2";
            mockTranslate.translate(Keys.TEXT_DISTRIBUTION_TOOTHCONTACTS, "42");
            result = "t_toothcontacts";
            mockTranslate.translate("toothcontact1");
            result = "t_toothcontact1";
            mockTranslate.translate("toothcontact2");
            result = "t_toothcontact2";
        }};
    }

    @AfterEach
    public void tearDown() {
        db.close();
    }

    @Test
    public void it_creates_observable_list_for_count_and_distribution() {
        setupTables();
        setupTestData();

        ObservableList<TableRow> data = new CountAndDistribution(ImmutableMap.of("evaluationId", "42")).data();

        assertThat(data.size()).isEqualTo(11);

        assertThat(data.get(0).getColumn1()).isEqualTo("t_count_patients");
        assertThat(data.get(0).getColumn2()).isEqualTo("value patient count");

        assertThat(data.get(1).getColumn1()).isEqualTo("t_gender");
        assertThat(data.get(1).getColumn2()).isEqualTo("t_gender1: 1, t_gender2: 2");

        assertThat(data.get(2).getColumn1()).isEqualTo("t_insurance");
        assertThat(data.get(2).getColumn2()).isEqualTo("t_insurance1: 3, t_insurance2: 4");

        assertThat(data.get(3).getColumn1()).isEqualTo("t_count_cases");
        assertThat(data.get(3).getColumn2()).isEqualTo("value case count");

        assertThat(data.get(4).getColumn1()).isEqualTo("t_count_tooth_loss");
        assertThat(data.get(4).getColumn2()).isEqualTo("value tooth loss count");

        assertThat(data.get(5).getColumn1()).isEqualTo("t_count_rezementierung");
        assertThat(data.get(5).getColumn2()).isEqualTo("value rezementierung count");

        assertThat(data.get(6).getColumn1()).isEqualTo("t_count_endodontie");
        assertThat(data.get(6).getColumn2()).isEqualTo("value endodontie count");

        assertThat(data.get(7).getColumn1()).isEqualTo("t_count_wurzelstift");
        assertThat(data.get(7).getColumn2()).isEqualTo("value wurzelstift count");

        assertThat(data.get(8).getColumn1()).isEqualTo("t_toothtypes");
        assertThat(data.get(8).getColumn2()).isEqualTo("t_toothtype1: 5, t_toothtype2: 6");

        assertThat(data.get(9).getColumn1()).isEqualTo("t_endstaendig");
        assertThat(data.get(9).getColumn2()).isEqualTo("t_endstaendig1: 7, t_endstaendig2: 8");

        assertThat(data.get(10).getColumn1()).isEqualTo("t_toothcontacts");
        assertThat(data.get(10).getColumn2()).isEqualTo("t_toothcontact1: 9, t_toothcontact2: 10");
    }

    private void setupTables() {
        setUpTableCount();
        setUpTableDistribution();
    }

    private void setUpTableCount() {
        List<DbColumn> dbColumnsCount = new ArrayList<>();
        dbColumnsCount.add(new DbColumn("item", "text"));
        dbColumnsCount.add(new DbColumn("name", "text"));
        dbColumnsCount.add(new DbColumn("value", "text"));
        db.rebuildTable("evaluation_42_counts", dbColumnsCount);
    }

    private void setUpTableDistribution() {
        List<DbColumn> dbColumnsDistribution = new ArrayList<>();
        dbColumnsDistribution.add(new DbColumn("item", "text"));
        dbColumnsDistribution.add(new DbColumn("name", "text"));
        dbColumnsDistribution.add(new DbColumn("value", "text"));
        dbColumnsDistribution.add(new DbColumn("value_count", "text"));
        db.rebuildTable("evaluation_42_distributions", dbColumnsDistribution);
    }

    private void setupTestData() {
        setUpTestDataCount();
        setUpTestDataDistribution();
    }

    private void setUpTestDataCount() {
        List<List<String>> testData = ImmutableList.of(
                ImmutableList.of("patient_count", Keys.TEXT_COUNT_PATIENTS, "value patient count"),
                ImmutableList.of("case_count", Keys.TEXT_COUNT_CASES, "value case count"),
                ImmutableList.of("tooth_loss_count", Keys.TEXT_COUNT_TOOTH_LOSS, "value tooth loss count"),
                ImmutableList.of("rezementierung_count", Keys.TEXT_COUNT_REZEMENTIERUNG, "value rezementierung count"),
                ImmutableList.of("endodontie_count", Keys.TEXT_COUNT_ENDODONTIE, "value endodontie count"),
                ImmutableList.of("wurzelstift_count", Keys.TEXT_COUNT_WURZELSTIFT, "value wurzelstift count")
        );

        List<DbRow> dbRows = new ArrayList<>();
        DbRow row;
        for (List<String> data : testData) {
            row = new DbRow();
            row.addCell(new DbCell("item", data.get(0)));
            row.addCell(new DbCell("name", data.get(1)));
            row.addCell(new DbCell("value", data.get(2)));
            dbRows.add(row);
        }

        db.writeRows("evaluation_42_counts", dbRows);
    }

    private void setUpTestDataDistribution() {
        List<List<String>> testData = ImmutableList.of(
                ImmutableList.of("gender", Keys.TEXT_GENDER, "gender1", "1"),
                ImmutableList.of("gender", Keys.TEXT_GENDER, "gender2", "2"),
                ImmutableList.of("insurance", Keys.TEXT_INSURANCE, "insurance1", "3"),
                ImmutableList.of("insurance", Keys.TEXT_INSURANCE, "insurance2", "4"),
                ImmutableList.of("toothtype", Keys.TEXT_TOOTHTYPES, "toothtype1", "5"),
                ImmutableList.of("toothtype", Keys.TEXT_TOOTHTYPES, "toothtype2", "6"),
                ImmutableList.of("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation", Keys.TEXT_DISTRIBUTION_ENDSTAENDIG, "endstaendig1", "7"),
                ImmutableList.of("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation", Keys.TEXT_DISTRIBUTION_ENDSTAENDIG, "endstaendig2", "8"),
                ImmutableList.of("toothcontacts__of__evidence_01_position_first_after_date_start_observation", Keys.TEXT_DISTRIBUTION_TOOTHCONTACTS, "toothcontact1", "9"),
                ImmutableList.of("toothcontacts__of__evidence_01_position_first_after_date_start_observation", Keys.TEXT_DISTRIBUTION_TOOTHCONTACTS, "toothcontact2", "10")
        );

        List<DbRow> dbRows = new ArrayList<>();
        DbRow row;
        for (List<String> data : testData) {
            row = new DbRow();
            row.addCell(new DbCell("item", data.get(0)));
            row.addCell(new DbCell("name", data.get(1)));
            row.addCell(new DbCell("value", data.get(2)));
            row.addCell(new DbCell("value_count", data.get(3)));
            dbRows.add(row);
        }

        db.writeRows("evaluation_42_distributions", dbRows);
    }
}