package de.dentareport.exceptions;

public class DentareportUnsupportedEncodingException extends RuntimeException {
    public DentareportUnsupportedEncodingException(Exception e) {
        e.printStackTrace();
    }
}
