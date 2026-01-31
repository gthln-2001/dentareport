package de.dentareport.repositories;

import com.google.common.collect.ImmutableSet;
import de.dentareport.models.TreatmentInterface;
import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class TreatmentsRepositoryTest {

    @Mocked
    DbConnection mockDbConnection;
    private Db db;

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
    public void it_gets_treatments() {
        setupTable();
        setupTestData();

        Set<String> billingcodes = ImmutableSet.of("01", "02", "03");
        List<TreatmentInterface> treatments = TreatmentsRepository.treatments("42", billingcodes);

        assertThat(treatments.size()).isEqualTo(3);

        assertThat(treatments.get(0).billingcode()).isEqualTo("03");
        assertThat(treatments.get(1).billingcode()).isEqualTo("02");
        assertThat(treatments.get(2).billingcode()).isEqualTo("01");
    }

    @Test
    public void it_does_not_squawk_if_there_are_no_treatments() {
        setupTable();

        Set<String> billingcodes = ImmutableSet.of("01", "02", "03");
        List<TreatmentInterface> treatments = TreatmentsRepository.treatments("42", billingcodes);
        assertThat(treatments.size()).isEqualTo(0);
    }

    private void setupTable() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("billingcode", "text"));
        dbColumns.add(new DbColumn("surfaces", "text"));
        dbColumns.add(new DbColumn("date", "text"));
        dbColumns.add(new DbColumn("sequence", "text"));
        dbColumns.add(new DbColumn("tooth", "text"));
        dbColumns.add(new DbColumn("handler", "text"));
        dbColumns.add(new DbColumn("insurance", "text"));
        dbColumns.add(new DbColumn("comment", "text"));
        dbColumns.add(new DbColumn("source", "text"));
        db.rebuildTable("treatments", dbColumns);
    }

    private void setupTestData() {
        List<DbRow> dbRows = new ArrayList<>();
        DbRow row1 = emptyTreatment();
        row1.setValue("patient_index", "42");
        row1.setValue("billingcode", "02");
        row1.setValue("date", "1999-10-01");
        row1.setValue("sequence", "2");
        dbRows.add(row1);
        DbRow row2 = emptyTreatment();
        row2.setValue("patient_index", "42");
        row2.setValue("billingcode", "01");
        row2.setValue("date", "2000-10-01");
        dbRows.add(row2);
        DbRow row3 = emptyTreatment();
        row3.setValue("patient_index", "42");
        row3.setValue("billingcode", "03");
        row3.setValue("date", "1999-10-01");
        row3.setValue("sequence", "1");
        dbRows.add(row3);
        DbRow row4 = emptyTreatment();
        row4.setValue("patient_index", "42");
        row4.setValue("billingcode", "04");
        dbRows.add(row4);
        DbRow row5 = emptyTreatment();
        row5.setValue("patient_index", "42");
        row5.setValue("billingcode", "");
        dbRows.add(row5);
        DbRow row6 = emptyTreatment();
        row6.setValue("patient_index", "23");
        row6.setValue("billingcode", "01");
        dbRows.add(row6);
        db.writeRows("treatments", dbRows);
    }

    private DbRow emptyTreatment() {
        DbRow row = new DbRow();
        row.addCell(new DbCell("patient_index", ""));
        row.addCell(new DbCell("billingcode", ""));
        row.addCell(new DbCell("surfaces", ""));
        row.addCell(new DbCell("date", ""));
        row.addCell(new DbCell("sequence", ""));
        row.addCell(new DbCell("tooth", ""));
        row.addCell(new DbCell("handler", ""));
        row.addCell(new DbCell("insurance", ""));
        row.addCell(new DbCell("comment", ""));
        row.addCell(new DbCell("source", ""));
        return row;
    }
}