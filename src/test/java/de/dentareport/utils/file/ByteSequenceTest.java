package de.dentareport.utils.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteSequenceTest {

    private ByteSequence byteSequence;

    @BeforeEach
    public void setUp() {
        byte[] b = new byte[5];
        b[0] = 65;
        b[1] = 66;
        b[2] = 67;
        b[3] = 68;
        b[4] = 69;
        byteSequence = new ByteSequence(b);
    }

    @Test
    public void it_gets_one_byte_out_of_sequence() {
        assertThat(byteSequence.value(1)).isEqualTo((byte) 66);
    }

    @Test
    public void it_gets_one_byte_out_of_sequence_as_integer() {
        assertThat(byteSequence.valueAsInt(3)).isEqualTo(68);
    }

    @Test
    public void it_gets_a_subsequence_as_string() {
        assertThat(byteSequence.stringSegment(1, 3)).isEqualTo("BCD");
    }

    @Test
    public void it_gets_length_of_sequence() {
        assertThat(byteSequence.length()).isEqualTo(5);
    }
}