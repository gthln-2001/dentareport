package de.dentareport.utils.db;

import java.util.Objects;

public class DbColumn {

    private String name;
    private String type;

    public DbColumn(String name,
                    String type) {
        validateType(type);
        this.name = name;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    private static void validateType(String type) {
        if (!Objects.equals(type, "text")
                && !Objects.equals(type, "integer primary key")) {
            throw new IllegalArgumentException("Illegal column type: " + type);
        }
    }
}
