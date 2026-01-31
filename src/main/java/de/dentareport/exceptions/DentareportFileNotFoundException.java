package de.dentareport.exceptions;

// TODO: TEST?
public class DentareportFileNotFoundException extends RuntimeException {
    public DentareportFileNotFoundException(Exception e) {
        e.printStackTrace();
    }
}
