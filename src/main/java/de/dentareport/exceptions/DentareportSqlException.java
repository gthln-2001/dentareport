package de.dentareport.exceptions;

// TODO: TEST?
public class DentareportSqlException extends RuntimeException {
    public DentareportSqlException(Exception e) {
        e.printStackTrace();
    }
}
