package de.dentareport.utils.dbf;

import de.dentareport.utils.file.ByteSequence;

// TODO: TEST?
public class DbfColumn {

    private static final int COLUMN_LENGTH_TYPE = 1;
    private static final int COLUMN_LENGTH_NAME = 10;
    private static final int OFFSET_DECIMAL_COUNT = 17;
    private static final int OFFSET_LENGTH = 16;
    private static final int OFFSET_TYPE = 11;
    private static final int OFFSET_NAME = 0;

    private Integer decimalCount;
    private Integer length;
    private String name;
    private String type;

    public DbfColumn(ByteSequence byteSequence) {
        setInstanceVariables(byteSequence);
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public Integer length() {
        return length;
    }

    public Integer decimalCount() {
        return decimalCount;
    }

    private void setInstanceVariables(ByteSequence byteSequence) {
        validateByteSequence(byteSequence);
        this.decimalCount = byteSequence.valueAsInt(OFFSET_DECIMAL_COUNT);
        this.length = byteSequence.valueAsInt(OFFSET_LENGTH) & 0xff;
        this.name = byteSequence.stringSegment(OFFSET_NAME, COLUMN_LENGTH_NAME).trim();
        this.type = byteSequence.stringSegment(OFFSET_TYPE, COLUMN_LENGTH_TYPE);
    }

    private void validateByteSequence(ByteSequence byteSequence) {
        if (byteSequence.length() != 32) {
            throw new IllegalArgumentException(
                    String.format("Length of byteSequence must be exactly 32 characters. (%d given)",
                            byteSequence.length())
            );
        }
    }
}
