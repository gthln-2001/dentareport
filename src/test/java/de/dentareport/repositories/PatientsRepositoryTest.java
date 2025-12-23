package de.dentareport.repositories;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.models.Patient;
import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PatientsRepositoryTest {

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
    public void it_gets_patient() throws Exception {
        setupTable();
        setupTestData();

        Patient patient = PatientsRepository.patient("42");

        assertThat(patient.gender()).isEqualTo("f");
    }

    // TODO: Fix tests
//    @Test
//    public void it_throws_exception_if_patient_does_not_exist() throws Exception {
//        setupTable();
//
//        assertThatExceptionOfType(DentareportSqlException.class).isThrownBy(() ->
//                PatientsRepository.patient("42")
//        );
//    }

    private void setupTestData() {
        List<DbRow> dbRows = new ArrayList<>();
        DbRow row1 = emptyPatient();
        row1.setValue("patient_index", "12");
        row1.setValue("gender", "m");
        dbRows.add(row1);
        DbRow row2 = emptyPatient();
        row2.setValue("patient_index", "42");
        row2.setValue("gender", "f");
        dbRows.add(row2);
        db.writeRows("patients", dbRows);
    }

    private DbRow emptyPatient() {
        DbRow row = new DbRow();
        row.addCell(new DbCell("patient_index", ""));
        row.addCell(new DbCell("first_name", ""));
        row.addCell(new DbCell("last_name", ""));
        row.addCell(new DbCell("date_of_birth", ""));
        row.addCell(new DbCell("gender", ""));
        row.addCell(new DbCell("first_visit", ""));
        row.addCell(new DbCell("last_visit", ""));
        return row;
    }

    private void setupTable() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("first_name", "text"));
        dbColumns.add(new DbColumn("last_name", "text"));
        dbColumns.add(new DbColumn("date_of_birth", "text"));
        dbColumns.add(new DbColumn("gender", "text"));
        dbColumns.add(new DbColumn("first_visit", "text"));
        dbColumns.add(new DbColumn("last_visit", "text"));
        db.rebuildTable("patients", dbColumns);
    }
}