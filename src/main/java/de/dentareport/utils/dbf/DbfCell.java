package de.dentareport.utils.dbf;

public class DbfCell {

    private String column;
    private String value;

    public DbfCell(String column,
                   String value) {
        this.column = column;
        this.value = value;
    }

    public String column() {
        return column;
    }

    public String value() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
