package de.dentareport.exceptions;

// TODO: TEST?
public class DentareportIllegalAccessException extends RuntimeException {
    public DentareportIllegalAccessException(Exception e) {
        e.printStackTrace();
    }
}
