package de.dentareport;

import java.io.File;

public class Config {

    public static String defaultDatabaseUrl() {
        return String.format("jdbc:sqlite:%1$s%2$ssqlite%2$sdentareport.db",
                System.getProperty("user.dir"),
                File.separator);
    }

    public static String importPath() {
        return String.format("%1$s%2$simport%2$s",
                System.getProperty("user.dir"),
                File.separator);
    }

    public static String evaluationColumnsPath() {
        return "de.dentareport.evaluations.columns.";
    }
}
