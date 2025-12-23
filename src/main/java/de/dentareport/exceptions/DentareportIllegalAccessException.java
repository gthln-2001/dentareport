package de.dentareport.exceptions;

public class DentareportIllegalAccessException extends RuntimeException {
    public DentareportIllegalAccessException(Exception e) {
        e.printStackTrace();
    }
}
