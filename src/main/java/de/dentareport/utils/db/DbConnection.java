package de.dentareport.utils.db;

public class DbConnection {

    private static Db db;

    public static Db db() {
        if (db == null || db.isDisconnected()) {
            db = new Db();
        }
        return db;
    }
}
