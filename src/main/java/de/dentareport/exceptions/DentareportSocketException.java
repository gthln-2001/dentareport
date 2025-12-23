package de.dentareport.exceptions;

public class DentareportSocketException extends RuntimeException {
    public DentareportSocketException(Exception e) {
        e.printStackTrace();
    }
}
