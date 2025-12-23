package de.dentareport.exceptions;

public class DentareportIOException extends RuntimeException {
    public DentareportIOException(Exception e) {
        e.printStackTrace();
    }
}
