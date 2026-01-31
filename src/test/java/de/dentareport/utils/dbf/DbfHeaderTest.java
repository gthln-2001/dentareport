package de.dentareport.utils.dbf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DbfHeaderTest {

    private DbfHeader dbfHeader;

    @BeforeEach
    public void setUp() {
        DbfFile testDbfFile = new DbfFile(pathToTestFile(), new ArrayList<>());
        dbfHeader = new DbfHeader(testDbfFile);
    }

    @Test
    public void it_gets_columns() {
        List<DbfColumn> result = dbfHeader.columns();

        assertThat(result.size()).isEqualTo(31);
    }

    @Test
    public void it_gets_dbf_version() {
        assertThat(dbfHeader.version()).isEqualTo(3);
    }

    @Test
    public void it_gets_header_length() {
        assertThat(dbfHeader.headerLength()).isEqualTo(1025);
    }

    @Test
    public void it_gets_last_modified_date() {
        LocalDate result = dbfHeader.lastModified();

        assertThat(result.getYear()).isEqualTo(2005);
        assertThat(result.getMonthValue()).isEqualTo(7);
        assertThat(result.getDayOfMonth()).isEqualTo(13);
    }

    @Test
    public void it_gets_row_count() {
        assertThat(dbfHeader.rowCount()).isEqualTo(14);
    }

    @Test
    public void it_gets_row_length() {
        assertThat(dbfHeader.rowLength()).isEqualTo(590);
    }

    private String pathToTestFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        @SuppressWarnings("ConstantConditions") File file = new File(classLoader.getResource("testfile.dbf").getFile());
        return file.getAbsolutePath();
    }
}