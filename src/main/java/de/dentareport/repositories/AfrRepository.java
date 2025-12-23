package de.dentareport.repositories;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static de.dentareport.utils.db.DbConnection.db;
import static de.dentareport.utils.map.MapUtils.toLinkedMap;

public class AfrRepository {

    private Map<String, String> options;

    public LinkedHashMap<String, Map<String, String>> afrTable(Map<String, String> options) {
        this.options = options;

        return groups()
                .stream()
                .collect(toLinkedMap(group -> group, this::afrForGroup));
    }

    private List<String> groups() {
        try {
            List<String> ret = new ArrayList<>();
            ResultSet rs = db().query(groupsQuery());
            while (rs.next()) {
                ret.add(rs.getString("group_name"));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String groupsQuery() {
        return String.format(
                "SELECT DISTINCT group_name FROM evaluation_%s%s " +
                "WHERE event = '%s' AND dependency = '%s' " +
                "ORDER BY group_order",
                options.get("evaluationId"),
                Keys.DB_TABLE_SUFFIX_AFR,
                options.get("event"),
                options.get("dependency")
        );
    }

    private Map<String, String> afrForGroup(String group) {
        try {
            ResultSet rs = db().query(afrQuery(group));
            Map<String, String> afr = new HashMap<>();
            while (rs.next()) {
                afr.put(rs.getString("interval"), rs.getString("afr"));
            }
            return afr;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String afrQuery(String group) {
        return String.format(
                "SELECT interval, afr FROM evaluation_%s%s " +
                "WHERE event = '%s' AND dependency = '%s' AND group_name = '%s'" +
                "ORDER BY interval",
                options.get("evaluationId"),
                Keys.DB_TABLE_SUFFIX_AFR,
                options.get("event"),
                options.get("dependency"),
                group
        );
    }
}
