package de.dentareport.utils.file;

import de.dentareport.exceptions.DentareportFileNotFoundException;
import de.dentareport.exceptions.DentareportIOException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

// TODO: TEST?
public class File {

    private final RandomAccessFile raFile;

    public File(RandomAccessFile raFile) {
        this.raFile = raFile;
    }

    public File(String filename) {
        this(file(filename));
    }

    public long pointerPosition() {
        try {
            return raFile.getFilePointer();
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    /**
     * @param position Position of next read operation relative to start of file.
     */
    public void setPointerPosition(long position) {
        try {
            raFile.seek(position);
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    /**
     * @param distance Number of bytes that pointer will be moved. Negative values move pointer backwards.
     */
    public void movePointer(int distance) {
        if (distance >= 0) {
            movePointerForwards(distance);
        } else {
            movePointerBackwards(Math.abs(distance));
        }
    }

    public int readByteAsInteger() {
        try {
            return raFile.read();
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    public ByteSequence readByteSequence(int length) {
        try {
            byte[] b = new byte[length];
            raFile.read(b);
            return new ByteSequence(b);
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    public List<ByteSequence> readByteSequences(int number, int length) {
        try {
            List<ByteSequence> ret = new ArrayList<>();
            for (int i = 0; i < number; i++) {
                byte[] b = new byte[length];
                if (raFile.read(b) == -1) {
                    return ret;
                }
                ret.add(new ByteSequence(b));
            }
            return ret;
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    /**
     * Read a little endian sequence of n bytes from the file and combine them to an integer value.
     * The result is the sum of the byte values multiplied with 256^0, 256^1,...,256^(n-1)
     *
     * @param length Number of bytes to readByteAsInt.
     * @return Integer value
     */
    public int readLittleEndian(int length) {
        try {
            int ret = 0;
            for (int i = 0; i < length; i++) {
                ret += raFile.read() * (int) Math.pow(256, i);
            }
            return ret;
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    public void close() {
        try {
            raFile.close();
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    private static RandomAccessFile file(String filename) {
        try {
            return new RandomAccessFile(filename, "r");
        } catch (FileNotFoundException e) {
            throw new DentareportFileNotFoundException(e);
        }
    }

    private void movePointerForwards(int distance) {
        try {
            raFile.skipBytes(distance);
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }

    private void movePointerBackwards(int distance) {
        try {
            raFile.seek(raFile.getFilePointer() - distance);
        } catch (IOException e) {
            throw new DentareportIOException(e);
        }
    }
}
