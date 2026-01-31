package de.dentareport.utils.dbf;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DbfTest {

    private Dbf dbf;
    @Mocked
    private DbfFile mockDbfFile;

    @BeforeEach
    public void setUp() {
        this.dbf = new Dbf();
        this.dbf.open(mockDbfFile);
    }

    @AfterEach
    public void tearDown() {
        dbf.close();
    }

    @Test
    public void it_closes_file_connection() {
        dbf.close();

        new Verifications() {{
            mockDbfFile.close();
            times = 1;
        }};
    }

    @Test
    public void it_gets_date_of_last_modification() {
        LocalDate modified = LocalDate.of(2015, 10, 10);
        new Expectations() {{
            mockDbfFile.lastModified();
            result = modified;
        }};

        assertThat(dbf.lastModified()).isEqualTo(modified);
    }

    @Test
    public void it_gets_chunk_size_to_read_from_file() {
        new Expectations() {{
            mockDbfFile.rowLength();
            result = 123;
        }};
        assertThat(dbf.chunkSizeToRead()).isEqualTo(68200);

        new Expectations() {{
            mockDbfFile.rowLength();
            result = 9000000;
        }};
        assertThat(dbf.chunkSizeToRead()).isEqualTo(1);
    }

    @Test
    public void it_gets_column_information() {
        List<DbfColumn> ret = new ArrayList<>();
        new Expectations() {{
            mockDbfFile.columns();
            result = ret;
        }};

        assertThat(dbf.columns()).isSameAs(ret);
    }

    @Test
    public void it_gets_dbf_version() {
        new Expectations() {{
            mockDbfFile.version();
            result = 13;
        }};

        assertThat(dbf.version()).isEqualTo(13);
    }

    @Test
    public void it_gets_filename() {
        new Expectations() {{
            mockDbfFile.filename();
            result = "some_filename";
        }};

        assertThat(dbf.filename()).isEqualTo("some_filename");
    }

    @Test
    public void it_gets_header_length() {
        new Expectations() {{
            mockDbfFile.headerLength();
            result = 42;
        }};

        assertThat(dbf.headerLength()).isEqualTo(42);
    }

    @Test
    public void it_gets_number_of_rows() {
        new Expectations() {{
            mockDbfFile.rowCount();
            result = 212;
        }};

        assertThat(dbf.rowCount()).isEqualTo(212);
    }

    @Test
    public void it_gets_row_length() {
        new Expectations() {{
            mockDbfFile.rowLength();
            result = 123;
        }};

        assertThat(dbf.rowLength()).isEqualTo(123);
    }

    @Test
    public void it_reads_rows_from_file() {
        List<DbfRow> ret = new ArrayList<>();
        new Expectations() {{
            mockDbfFile.readRows(2);
            result = ret;
        }};

        assertThat(dbf.rows(2)).isSameAs(ret);
    }

    @Test
    public void it_reads_chunk_of_rows_from_file() {
        List<DbfRow> ret = new ArrayList<>();
        new Expectations() {{
            mockDbfFile.rowLength();
            result = 4194304; // MAX_CHUNK_SIZE / 2
            mockDbfFile.readRows(2);
            times = 1;
            result = ret;
        }};

        assertThat(dbf.chunkOfRows()).isSameAs(ret);
    }
}