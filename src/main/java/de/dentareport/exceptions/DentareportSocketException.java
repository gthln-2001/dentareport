package de.dentareport.exceptions;

// TODO: TEST?
public class DentareportSocketException extends RuntimeException {
    public DentareportSocketException(Exception e) {
        e.printStackTrace();
    }
}
