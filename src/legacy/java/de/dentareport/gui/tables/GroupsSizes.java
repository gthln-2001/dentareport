package de.dentareport.gui.tables;

import de.dentareport.evaluations.meta.dependencies.Dependencies;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.elements.TableRow;
import de.dentareport.utils.Keys;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GroupsSizes extends Table {

    private String dependency;
    private final Dependencies dependencies;

    public GroupsSizes() {
        this.dependencies = new Dependencies();
    }

    public ObservableList<TableRow> data(Map<String, String> options) {
        this.dependency = options.get("dependency");
        return tableRows(groupSizes(options));
    }

    private ObservableList<TableRow> tableRows(ResultSet rs) {
        try {
            ObservableList<TableRow> ret = FXCollections.observableArrayList();
            while (rs.next()) {
                ret.add(tableRow(rs));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private TableRow tableRow(ResultSet rs) throws SQLException {
        return new TableRow(translate(rs.getString("item")), rs.getString("item_count"));
    }

    private ResultSet groupSizes(Map<String, String> options) {
        if (Objects.equals(dependency, Keys.GUI_TEXT_NO_DEPENDENCY)) {
            return db().query(String.format(
                    "SELECT '%s' AS item, COUNT(*) as item_count FROM evaluation_%s",
                    Keys.GUI_TEXT_TOTAL,
                    options.get("evaluationId")
            ));
        }
        return db().query(queryGroupSizes(options));
    }

    private String queryGroupSizes(Map<String, String> options) {
        return String.format(
                "SELECT %1$s AS item, COUNT(%1$s) as item_count " +
                        "FROM evaluation_%2$s " +
                        "WHERE %1$s NOT IN ('', '%3$s') " +
                        "GROUP BY %1$s " +
                        "ORDER BY %4$s",
                dependencies.groupColumn(dependency),
                options.get("evaluationId"),
                Keys.NO_DATA,
                dependencies.orderColumn(dependency)
        );
    }
}
