package de.dentareport.evaluations.office;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.IntStream;

import static de.dentareport.utils.db.DbConnection.db;

public interface ItemGroupedByAge extends Item {

    @Override
    default void evaluate() {
        List<DbRow> rows = new ArrayList<>();
        Map<String, String> result = results();
        for (Map.Entry<String, String> entry : result.entrySet()) {
            DbRow row = new DbRow();
            row.addCell(new DbCell("item", item()));
            row.addCell(new DbCell("name", name()));
            row.addCell(new DbCell("unit", unit()));
            row.addCell(new DbCell("group_name", entry.getKey()));
            row.addCell(new DbCell("value", entry.getValue()));

            rows.add(row);
        }

        db().writeRows(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP, rows);
    }

    String query();

    default Map<String, String> results() {
        try {
            Map<String, String> ret = new HashMap<>();
            ResultSet rs = db().query(query());
            while (rs.next()) {
                ret.put(rs.getString("group_age_last_01"), rs.getString("value"));
            }
            return fillMissing(ret);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    default Map<String, String> fillMissing(Map<String, String> groups) {
        if (!groups.containsKey("0-19")) {
            groups.put("0-19", defaultValueForEmptyGroups());
        }
        // We start with decade 2 because 0-9 and 10-19 are combined to 0-19
        IntStream.range(2, maxDecade(groups))
                .filter(v -> !groups.containsKey(decadeKey(v)))
                .forEach(v -> groups.put(decadeKey(v), defaultValueForEmptyGroups()));
        return groups;
    }

    default String defaultValueForEmptyGroups() {
        return Keys.NO_DATA;
    }

    default int maxDecade(Map<String, String> groups) {
        return groups.keySet().stream()
                .map(this::firstChunkAsInt)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    default Integer firstChunkAsInt(String k) {
        return Arrays.stream(k.split("-"))
                .map(v -> Integer.parseInt(v) / 10)
                .findFirst()
                .orElse(0);
    }

    default String decadeKey(int decade) {
        return String.format("%s-%s",
                             decade * 10,
                             (decade * 10 + 9));
    }
}
