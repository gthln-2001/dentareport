package de.dentareport.exceptions;

// TODO: TEST?
public class DentareportParseException extends RuntimeException {
    public DentareportParseException(Exception e) {
        e.printStackTrace();
    }
}
