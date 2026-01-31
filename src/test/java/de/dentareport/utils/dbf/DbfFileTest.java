package de.dentareport.utils.dbf;

import com.google.common.collect.ImmutableList;
import de.dentareport.exceptions.DentareportUnexpectedEndOfDbfHeaderException;
import de.dentareport.utils.file.ByteSequence;
import de.dentareport.utils.file.File;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class DbfFileTest {

    private List<String> requiredColumns = ImmutableList.of("FOO", "BAR");

    @Test
    public void it_closes_file_connection(@Mocked File mockFile,
                                          @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);
        dbfFile.close();

        new Verifications() {{
            mockFile.close();
            times = 1;
        }};
    }

    @Test
    public void it_gets_filename(@Mocked File mockFile,
                                 @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        assertThat(dbfFile.filename()).isEqualTo("some_filename");
    }

    // TODO: Fix test
//    @Test
//    public void it_gets_file_pointer_position(@Mocked File mockFile,
//                                              @Mocked DbfHeader mockHeader) {
//        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);
//
//        new Expectations() {{
//            mockFile.pointerPosition();
//            result = (long) 23;
//        }};
//
//        assertThat(dbfFile.pointerPosition()).isEqualTo(23);
//    }

    @Test
    public void it_sets_file_pointer_to_given_position(@Mocked File mockFile,
                                                       @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);
        dbfFile.setPointerPosition(37);

        new Verifications() {{
            mockFile.setPointerPosition(37);
            times = 1;
        }};
    }

    @Test
    public void it_moves_file_pointer(@Mocked File mockFile,
                                      @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);
        dbfFile.movePointer(34);

        new Verifications() {{
            mockFile.movePointer(34);
            times = 1;
        }};
    }

    @Test
    public void it_reads_one_byte_from_file(@Mocked File mockFile,
                                            @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        new Expectations() {{
            mockFile.readByteAsInteger();
            result = 49;
        }};

        assertThat(dbfFile.readByteAsInt()).isEqualTo(49);
    }

    @Test
    public void it_reads_sequence_of_bytes_as_one_integer(@Mocked File mockFile,
                                                          @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        new Expectations() {{
            mockFile.readLittleEndian(6);
            result = 149;
        }};

        assertThat(dbfFile.readBytesAsInt(6)).isEqualTo(149);
    }

    @Test
    public void it_reads_column_information(@Mocked File mockFile,
                                            @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        new Expectations() {{
            mockFile.readByteAsInteger();
            times = 1;
            result = 100;  // Anything but 13 (Dbf.END_OF_HEADER)

            mockFile.readByteSequence(32);
            times = 1;
            result = new ByteSequence(new byte[32]);
        }};

        assertThat(dbfFile.readColumn()).isInstanceOf(DbfColumn.class);
    }

    @Test
    public void it_throws_exception_if_end_of_header_is_reached(@Mocked File mockFile,
                                                                @Mocked DbfHeader mockHeader) throws IOException {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        new Expectations() {{
            mockFile.readByteAsInteger();
            times = 1;
            result = 13;  // Dbf.END_OF_HEADER

            mockFile.readByteSequence(32);
            times = 0;
        }};

        assertThatExceptionOfType(DentareportUnexpectedEndOfDbfHeaderException.class).isThrownBy(
                dbfFile::readColumn
        );
    }

    @Test
    public void it_gets_the_column_information(@Mocked File mockFile,
                                               @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);
        List<DbfColumn> ret = new ArrayList<>();

        new Expectations() {{
            mockHeader.columns();
            result = ret;
        }};

        List<DbfColumn> result = dbfFile.columns();

        assertThat(result).isSameAs(ret);
    }

    @Test
    public void it_gets_the_header_length(@Mocked File mockFile,
                                          @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        new Expectations() {{
            mockHeader.headerLength();
            result = 4222;
        }};

        assertThat(dbfFile.headerLength()).isEqualTo(4222);
    }

    @Test
    public void it_gets_date_of_last_modification(@Mocked File mockFile,
                                                  @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);
        LocalDate modified = LocalDate.of(2015, 10, 10);

        new Expectations() {{
            mockHeader.lastModified();
            result = modified;
        }};

        assertThat(dbfFile.lastModified()).isEqualTo(modified);
    }

    @Test
    public void it_gets_number_of_rows(@Mocked File mockFile,
                                       @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        new Expectations() {{
            mockHeader.rowCount();
            result = 2125;
        }};

        assertThat(dbfFile.rowCount()).isEqualTo(2125);
    }

    @Test
    public void it_gets_the_row_length(@Mocked File mockFile,
                                       @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        new Expectations() {{
            mockHeader.rowLength();
            result = 1231;
        }};

        assertThat(dbfFile.rowLength()).isEqualTo(1231);
    }

    @Test
    public void it_gets_dbf_version(@Mocked File mockFile,
                                    @Mocked DbfHeader mockHeader) {
        DbfFile dbfFile = new DbfFile("some_filename", requiredColumns, mockFile);

        new Expectations() {{
            mockHeader.version();
            result = 13;
        }};

        assertThat(dbfFile.version()).isEqualTo(13);
    }

    @Test
    public void it_reads_rows_of_data() {
        List<String> columns = ImmutableList.of("Shape", "Condition", "Max_PDOP");
        DbfFile file = new DbfFile(pathToTestFile(), columns);

        List<DbfRow> result = file.readRows(8);

        assertThat(result.size()).isEqualTo(8);
        assertThat(result.get(0).value("Shape")).isEqualTo("circular            ");
        assertThat(result.get(0).value("Condition")).isEqualTo("Good                ");
        assertThat(result.get(0).value("Max_PDOP")).isEqualTo("  5.2");
        assertThat(result.get(1).value("Max_PDOP")).isEqualTo("  4.9");
        assertThat(result.get(7).value("Condition")).isEqualTo("Plugged             ");
    }

    private String pathToTestFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        @SuppressWarnings("ConstantConditions") java.io.File file = new java.io.File(classLoader.getResource("testfile.dbf").getFile());
        return file.getAbsolutePath();
    }
}