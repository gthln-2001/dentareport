package de.dentareport.utils.file;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class FileTest {

    private File file;

    @Mocked
    private RandomAccessFile mockRandomAccessFile;

    @BeforeEach
    public void setUp() {
        this.file = new File(mockRandomAccessFile);
    }

    // TODO: Fix tests
//
//    @Test
//    public void it_closes_the_file_connection() throws Exception {
//        file.close();
//        new Verifications() {{
//            mockRandomAccessFile.close();
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_gets_the_current_file_pointer_position() throws Exception {
//        new Expectations() {{
//            mockRandomAccessFile.getFilePointer();
//            result = (long) 42;
//        }};
//
//        long position = file.pointerPosition();
//
//        assertThat(position).isEqualTo(42);
//    }
//
//    @Test
//    public void it_sets_the_file_pointer_to_a_given_position() throws Exception {
//        file.setPointerPosition(7);
//
//        new Verifications() {{
//            mockRandomAccessFile.seek(7);
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_moves_the_file_pointer_forwards() throws Exception {
//        file.movePointer(77);
//
//        new Verifications() {{
//            mockRandomAccessFile.skipBytes(77);
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_moves_the_file_pointer_backwards() throws Exception {
//        new Expectations() {{
//            mockRandomAccessFile.getFilePointer();
//            result = (long) 123;
//        }};
//
//        file.movePointer(-5);
//
//        new Verifications() {{
//            mockRandomAccessFile.seek(118);
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_reads_a_byte_sequence_with_a_given_length() throws Exception {
//        ByteSequence result = file.readByteSequence(7);
//
//        assertThat(result.length()).isEqualTo(7);
//
//        new Verifications() {{
//            mockRandomAccessFile.read(new byte[7]);
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_reads_several_byte_sequences_as_a_list() throws Exception {
//        new Expectations() {{
//            mockRandomAccessFile.read(new byte[8]);
//            returns(8, 8, -1); // return value of -1 indicates end of file
//            times = 3;
//        }};
//
//        List<ByteSequence> result = file.readByteSequences(3, 8);
//
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.get(0).length()).isEqualTo(8);
//        assertThat(result.get(1).length()).isEqualTo(8);
//    }

    @Test
    public void it_reads_a_byte_as_integer() throws Exception {
        new Expectations() {{
            mockRandomAccessFile.read();
            result = 23;
        }};

        int result = file.readByteAsInteger();

        assertThat(result).isEqualTo(23);
    }

    // TODO: Fix tests
//
//    @Test
//    public void it_reads_a_little_endian_sequence_of_bytes_and_returns_them_as_integer() throws Exception {
//        new Expectations() {{
//            mockRandomAccessFile.read();
//            returns(6, 5, 4);
//        }};
//
//        int result = file.readLittleEndian(3);
//
//        assertThat(result).isEqualTo(263430); // 6*256^0 + 5*256^1 + 4*256^2 = 6 + 1280 + 262144
//    }

    @Test
    public void it_returns_zero_when_trying_to_get_integer_value_of_byte_sequence_with_length_of_zero() {
        int result = file.readLittleEndian(0);

        assertThat(result).isEqualTo(0);
    }
}