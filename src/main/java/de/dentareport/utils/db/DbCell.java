package de.dentareport.utils.db;

// TODO: TEST?
public class DbCell {

    private String column;
    private String value;

    public DbCell(String column,
                  String value) {
        this.column = column;
        this.value = value;
    }

    public DbCell(DbCell origin) {
        this(origin.column(), origin.value());
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
