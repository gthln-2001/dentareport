package de.dentareport.exceptions;

public class DentareportSqlException extends RuntimeException {
    public DentareportSqlException(Exception e) {
        e.printStackTrace();
    }
}
