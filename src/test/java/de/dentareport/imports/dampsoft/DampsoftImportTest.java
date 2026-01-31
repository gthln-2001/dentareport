package de.dentareport.imports.dampsoft;

//import de.dentareport.gui.ProgressUpdate;
import de.dentareport.imports.dampsoft.dampsoft_files.DampsoftFile;
import de.dentareport.utils.db.*;
import de.dentareport.utils.dbf.Dbf;
import de.dentareport.utils.dbf.DbfCell;
import de.dentareport.utils.dbf.DbfRow;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DampsoftImportTest {

    private DampsoftImport dampsoftImport;
    private Db db;
    @Mocked
    DbConnection mockDbConnection;
    @Mocked
    Dbf mockDbf;
//    @Mocked
//    ProgressUpdate mockProgressUpdate;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();

        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
        dampsoftImport = new DampsoftImport(new Foo(), mockDbf);
    }

    @AfterEach
    public void tearDown() {
        db.close();
        dampsoftImport.closeConnections();
    }

    @Test
    public void it_imports_data() throws Exception {
        new Expectations() {{
            mockDbf.chunkOfRows();
            result = Foo.mockDbfRows();
        }};

        dampsoftImport.rebuildTable();
        dampsoftImport.importData();

        List<DbRow> dbResults = Foo.dbResults(db);
        assertThat(dbResults.size()).isEqualTo(2);
        assertThat(dbResults.get(0).cells().size()).isEqualTo(1);
        assertThat(dbResults.get(0).value("col")).isEqualTo("db_value1");
        assertThat(dbResults.get(1).value("col")).isEqualTo("db_value2");

//        new Verifications() {{
//            ProgressUpdate.tick();
//            times = 3;
//        }};
    }
}

class Foo implements DampsoftFile {

    public static List<DbRow> dbResults(Db db) throws Exception {
        ResultSet rs = db.query("SELECT * FROM foo_table");
        List<DbRow> dbResults = new ArrayList<>();
        while (rs.next()) {
            DbRow dbRow = new DbRow();
            dbRow.addCell(new DbCell("col", rs.getString("col")));
            dbResults.add(dbRow);
        }
        return dbResults;
    }

    public static List<DbfRow> mockDbfRows() {
        List<DbfRow> dbfRows = new ArrayList<>();

        DbfRow row1 = new DbfRow();
        row1.addCell(new DbfCell("dbf_column", "dbf_value1"));
        dbfRows.add(row1);

        DbfRow row2 = new DbfRow();
        row2.addCell(new DbfCell("dbf_column", "dbf_value2"));
        dbfRows.add(row2);

        DbfRow row3 = new DbfRow();
        row3.addCell(new DbfCell("dbf_column", "dbf_value3"));
        dbfRows.add(row3);

        return dbfRows;
    }

    @Override
    public void importFile() {
    }

    @Override
    public String filename() {
        return null;
    }

    @Override
    public String tablename() {
        return "foo_table";
    }

    @Override
    public List<String> columnsToImport() {
        return null;
    }

    @Override
    public List<DbRow> convert(DbfRow dbfRow) {
        List<DbRow> ret = new ArrayList<>();
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("col", dbfRow.value("dbf_column").replace("dbf", "db")));
        ret.add(dbRow);
        return ret;
    }

    @Override
    public List<DbColumn> columnsToWrite() {
        List<DbColumn> ret = new ArrayList<>();
        ret.add(new DbColumn("col", "text"));
        return ret;
    }

    @Override
    public boolean isValidRow(DbRow dbRow) {
        return !Objects.equals(dbRow.value("col"), "db_value3");
    }
}