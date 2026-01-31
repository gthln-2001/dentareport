package de.dentareport;

import com.google.common.collect.ImmutableList;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.date.DateRange;
import de.dentareport.utils.db.Db;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class Metadata {

    public static void init() {
        if (!db().hasTable(Keys.METADATA_DB_TABLENAME)) {
            createDbTable(db());
        }
    }

    public static void set(String key, String value) {
        delete(key);
        DbRow row = new DbRow();
        row.addCell(new DbCell("key", key));
        row.addCell(new DbCell("value", value));
        db().writeRow(Keys.METADATA_DB_TABLENAME, row);
    }

    public static boolean has(String key) {
        return db().hasEntry(Keys.METADATA_DB_TABLENAME, "key", key);
    }

    public static String get(String key) {
        try {
            ResultSet rs = db().query(String.format("SELECT value FROM %s WHERE key='%s'",
                    Keys.METADATA_DB_TABLENAME,
                    key));
            if (!rs.next()) {
                throw new IllegalArgumentException("Metadata key not found: " + key);
            }
            return rs.getString("value");
        } catch (SQLException | IllegalArgumentException e) {
            throw new DentareportSqlException(e);
        }
    }

    public static void delete(String key) {
        db().execute(String.format("DELETE FROM %s WHERE key='%s'",
                Keys.METADATA_DB_TABLENAME,
                key));
    }

    public static DateRange evaluationPeriod(String evaluationId) {
        return db().evaluationPeriod(evaluationId);
    }

    private static void createDbTable(Db db) {
        db.rebuildTable(Keys.METADATA_DB_TABLENAME, ImmutableList.of(
                new DbColumn("key", "text"),
                new DbColumn("value", "text")
        ));
    }
}
