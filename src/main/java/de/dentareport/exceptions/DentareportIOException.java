package de.dentareport.exceptions;

// TODO: TEST?
public class DentareportIOException extends RuntimeException {
    public DentareportIOException(Exception e) {
        e.printStackTrace();
    }
}
