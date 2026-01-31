package de.dentareport;

import de.dentareport.utils.db.Db;

import java.sql.DriverManager;

// TODO: TEST?
public class TestHelper {

    public static Db dbInMemory() throws Exception {
        return new Db(DriverManager.getConnection("jdbc:sqlite::memory:"));
    }
}
