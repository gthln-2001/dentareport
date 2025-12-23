package de.dentareport.repositories;

import de.dentareport.utils.db.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidCasesRepositoryTest {

    @Mocked
    DbConnection mockDbConnection;
    private Db db;
    private ValidCasesRepository validCasesRepository;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
        this.validCasesRepository = new ValidCasesRepository();
    }

    @AfterEach
    public void tearDown() {
        db.close();
    }

    @Test
    public void it_identifies_valid_cases() {
        setupTestData();

        validCasesRepository.identifyValidCases();

        Map<String, List<String>> cases = validCasesRepository.validCases("9");

        assertThat(cases.size()).isEqualTo(2);

        assertThat(cases.keySet().toArray()[0]).isEqualTo("1");
        assertThat(cases.keySet().toArray()[1]).isEqualTo("3");

        assertThat(cases.get("1").size()).isEqualTo(1);
        assertThat(cases.get("1").get(0)).isEqualTo("44");

        assertThat(cases.get("3").size()).isEqualTo(2);
        assertThat(cases.get("3").get(0)).isEqualTo("14");
        assertThat(cases.get("3").get(1)).isEqualTo("24");
    }

    @Test
    public void it_does_not_squawk_if_there_are_no_valid_cases() {
        setupTestDataWithoutValidCases();

        validCasesRepository.identifyValidCases();

        Map<String, List<String>> cases = validCasesRepository.validCases("9");

        assertThat(cases.size()).isEqualTo(0);
        assertThat(db.hasTable("valid_cases")).isTrue();
    }

    private void setupTestData() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("tooth", "text"));
        dbColumns.add(new DbColumn("billingcode", "text"));
        dbColumns.add(new DbColumn("source", "text"));
        db.rebuildTable("treatments", dbColumns);

        List<DbRow> dbRows = new ArrayList<>();
        DbRow row1 = new DbRow();
        row1.addCell(new DbCell("patient_index", "3"));
        row1.addCell(new DbCell("tooth", "24"));
        row1.addCell(new DbCell("billingcode", "504"));
        dbRows.add(row1);
        DbRow row2 = new DbRow();
        row2.addCell(new DbCell("patient_index", "2"));
        row2.addCell(new DbCell("tooth", "23"));
        row2.addCell(new DbCell("billingcode", "01"));
        dbRows.add(row2);
        DbRow row3 = new DbRow();
        row3.addCell(new DbCell("patient_index", "1"));
        row3.addCell(new DbCell("tooth", "44"));
        row3.addCell(new DbCell("billingcode", "504"));
        dbRows.add(row3);
        DbRow row4 = new DbRow();
        row4.addCell(new DbCell("patient_index", "3"));
        row4.addCell(new DbCell("tooth", "14"));
        row4.addCell(new DbCell("billingcode", "504"));
        dbRows.add(row4);

        db.writeRows("treatments", dbRows);
    }

    private void setupTestDataWithoutValidCases() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("tooth", "text"));
        dbColumns.add(new DbColumn("billingcode", "text"));
        dbColumns.add(new DbColumn("source", "text"));
        db.rebuildTable("treatments", dbColumns);
    }
}