package de.dentareport.repositories;

import de.dentareport.models.Evidence01Interface;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

public class Evidences01RepositoryTest {

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
    public void it_gets_evidences01() {
        setupTable();
        setupTestData();

        List<Evidence01Interface> evidences01 = Evidences01Repository.evidences01("42");

        assertThat(evidences01.size()).isEqualTo(3);

        assertThat(evidences01.get(0).date()).isEqualTo("1995-10-01");
        assertThat(evidences01.get(1).date()).isEqualTo("1999-10-01");
        assertThat(evidences01.get(2).date()).isEqualTo("2000-10-01");
    }

    @Test
    public void it_does_not_squawk_if_there_are_no_evidences01() {
        setupTable();

        List<Evidence01Interface> evidences01 = Evidences01Repository.evidences01("42");
        assertThat(evidences01.size()).isEqualTo(0);
    }

    private void setupTable() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("date", "text"));
        dbColumns.add(new DbColumn("billingcode", "text"));
        dbColumns.add(new DbColumn("dft", "text"));
        dbColumns.add(new DbColumn("dmft", "text"));
        dbColumns.add(new DbColumn("dt", "text"));
        dbColumns.add(new DbColumn("mt", "text"));
        dbColumns.add(new DbColumn("ft", "text"));
        dbColumns.add(new DbColumn("tooth_count_q1", "text"));
        dbColumns.add(new DbColumn("tooth_count_q2", "text"));
        dbColumns.add(new DbColumn("tooth_count_q3", "text"));
        dbColumns.add(new DbColumn("tooth_count_q4", "text"));
        dbColumns.addAll(Toothnumbers.ALL.stream()
                                         .map(toothnumber -> new DbColumn("status_" + toothnumber, "text"))
                                         .collect(Collectors.toList()));
        for (String toothnumber: Toothnumbers.ALL) {
            dbColumns.addAll(Keys.SURFACES.stream()
                                             .map(surface -> new DbColumn(String.format("caries_%s_%s",
                                                                                            toothnumber,
                                                                                            surface), "text"))
                                             .collect(Collectors.toList()));
            dbColumns.addAll(Keys.SURFACES.stream()
                                          .map(surface -> new DbColumn(String.format("filling_%s_%s",
                                                                                     toothnumber,
                                                                                     surface), "text"))
                                          .collect(Collectors.toList()));
        }
        db.rebuildTable("evidences_01", dbColumns);
    }

    private void setupTestData() {
        List<DbRow> dbRows = new ArrayList<>();
        DbRow row1 = emptyEvidence01();
        row1.setValue("patient_index", "42");
        row1.setValue("date", "1999-10-01");
        dbRows.add(row1);
        DbRow row2 = emptyEvidence01();
        row2.setValue("patient_index", "42");
        row2.setValue("date", "2000-10-01");
        dbRows.add(row2);
        DbRow row3 = emptyEvidence01();
        row3.setValue("patient_index", "42");
        row3.setValue("date", "1995-10-01");
        dbRows.add(row3);
        DbRow row4 = emptyEvidence01();
        row4.setValue("patient_index", "23");
        row4.setValue("date", "1988-10-01");
        dbRows.add(row4);
        db.writeRows("evidences_01", dbRows);
    }

    private DbRow emptyEvidence01() {
        DbRow row = new DbRow();
        row.addCell(new DbCell("patient_index", ""));
        row.addCell(new DbCell("date", ""));
        row.addCell(new DbCell("billingcode", ""));
        row.addCell(new DbCell("dft", ""));
        row.addCell(new DbCell("dmft", ""));
        row.addCell(new DbCell("dt", ""));
        row.addCell(new DbCell("mt", ""));
        row.addCell(new DbCell("ft", ""));
        row.addCell(new DbCell("tooth_count_q1", ""));
        row.addCell(new DbCell("tooth_count_q2", ""));
        row.addCell(new DbCell("tooth_count_q3", ""));
        row.addCell(new DbCell("tooth_count_q4", ""));
        for (String toothnumber : Toothnumbers.ALL) {
            row.addCell(new DbCell("status_" + toothnumber, ""));
        }
        return row;
    }
}