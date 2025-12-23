package de.dentareport.utils.file;

import de.dentareport.exceptions.DentareportUnsupportedEncodingException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ByteSequence {

    private byte[] sequence;

    public ByteSequence(byte[] sequence) {
        this.sequence = sequence;
    }

    public byte value(int offset) {
        return sequence[offset];
    }

    public int valueAsInt(int offset) {
        return (int) sequence[offset];
    }

    public String stringSegment(int start, int length) {
        return new String(byteSegment(start, length));
    }

    public String stringSegment(int start, int length, String charset) {
        try {
            return new String(byteSegment(start, length), charset);
        } catch (UnsupportedEncodingException e) {
            throw new DentareportUnsupportedEncodingException(e);
        }
    }

    public int length() {
        return sequence.length;
    }

    private byte[] byteSegment(int start, int length) {
        return Arrays.copyOfRange(sequence, start, (start + length));
    }
}
