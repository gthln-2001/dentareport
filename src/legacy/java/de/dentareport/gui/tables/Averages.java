package de.dentareport.gui.tables;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.elements.TableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class Averages extends Table {

    private final Map<String, String> options;

    public Averages(Map<String, String> options) {
        this.options = options;
    }

    public ObservableList<TableRow> data() {
        ObservableList<TableRow> ret = FXCollections.observableArrayList();

        ResultSet rs = db().query("SELECT * FROM evaluation_" + this.options.get("evaluationId") + "_averages");
        try {
            while (rs.next()) {
                ret.add(row(rs));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private TableRow row(ResultSet rs) throws SQLException {
        return new TableRow(
                translate(rs.getString("name"), this.options.get("evaluationId")),
                translate(rs.getString("unit")),
                rs.getString("average"),
                rs.getString("median"),
                rs.getString("minimum"),
                rs.getString("maximum")
        );
    }
}
