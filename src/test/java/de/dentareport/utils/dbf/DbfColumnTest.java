package de.dentareport.utils.dbf;

import com.google.common.collect.ImmutableList;
import de.dentareport.utils.file.ByteSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DbfColumnTest {

    private DbfColumn dbfColumn;
    private byte[] sequence;

    @BeforeEach
    public void setUp() {
        sequence = generateTestSequence();
        dbfColumn = new DbfColumn(new ByteSequence(sequence));
    }

    @Test
    public void it_gets_column_length() {
        assertThat(dbfColumn.length()).isEqualTo(112);
    }

    @Test
    public void it_gets_column_length_for_signed_byte_values() {
        sequence = generateTestSequence();
        sequence[16] = (byte) -112;
        dbfColumn = new DbfColumn(new ByteSequence(sequence));

        assertThat(dbfColumn.length()).isEqualTo(144);
    }

    @Test
    public void it_gets_column_name() {
        assertThat(dbfColumn.name()).isEqualTo("ABCDEFGHIJ");
    }

    @Test
    public void it_gets_trimmed_column_name() {
        sequence = generateTestSequence();
        sequence[0] = (byte) 0;
        sequence[9] = (byte) 0;
        dbfColumn = new DbfColumn(new ByteSequence(sequence));

        assertThat(dbfColumn.name()).isEqualTo("BCDEFGHI");
    }

    @Test
    public void it_gets_column_type() {
        assertThat(dbfColumn.type()).isEqualTo("K");
    }

    @Test
    public void it_gets_decimal_count() {
        assertThat(dbfColumn.decimalCount()).isEqualTo(4);
    }

    @Test
    public void it_throws_exception_when_byte_sequence_is_not_exactly_32_characters_long() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                new DbfColumn(new ByteSequence(new byte[31])) // 31 chars
        );
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                new DbfColumn(new ByteSequence(new byte[33])) // 33 chars
        );
    }

    private byte[] generateTestSequence() {
        byte[] testSequence = new byte[32];
        List<Integer> values = ImmutableList.of(
                65, 66, 67, 68, 69, 70, 71, 72, 73, 74,     // Name
                0,
                75,                                         // Type
                0, 0, 0, 0,
                112,                                        // Size
                4,                                          // Decimal Count
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        );
        for (int i = 0; i < 32; i++) {
            testSequence[i] = (byte) (int) values.get(i);
        }
        return testSequence;
    }
}