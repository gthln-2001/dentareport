package de.dentareport.gui.tables;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.elements.TableRow;
import de.dentareport.utils.Keys;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GeneralPatientInformationAverages extends Table {

    public ObservableList<TableRow> data() {
        ObservableList<TableRow> ret = dataFromDb();
        ret.sort(Comparator.comparing(TableRow::getColumn1));

        return ret;
    }

    private ObservableList<TableRow> dataFromDb() {
        try {
            ObservableList<TableRow> ret = FXCollections.observableArrayList();

            ResultSet rs = db().query(String.format("SELECT * FROM %s",
                    Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES));
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
                translate(rs.getString("name")),
                translate(rs.getString("unit")),
                rs.getString("average"),
                rs.getString("median"),
                rs.getString("minimum"),
                rs.getString("maximum")
        );
    }
}
