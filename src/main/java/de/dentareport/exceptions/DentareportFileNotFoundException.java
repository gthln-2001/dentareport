package de.dentareport.exceptions;

public class DentareportFileNotFoundException extends RuntimeException {
    public DentareportFileNotFoundException(Exception e) {
        e.printStackTrace();
    }
}
