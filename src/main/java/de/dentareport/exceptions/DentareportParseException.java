package de.dentareport.exceptions;

public class DentareportParseException extends RuntimeException {
    public DentareportParseException(Exception e) {
        e.printStackTrace();
    }
}
