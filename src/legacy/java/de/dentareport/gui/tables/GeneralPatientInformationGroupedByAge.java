package de.dentareport.gui.tables;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.elements.TableRow;
import de.dentareport.utils.Keys;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GeneralPatientInformationGroupedByAge extends Table {

    public ObservableList<TableRow> data() {
        ObservableList<TableRow> ret = FXCollections.observableArrayList();

        items().forEach(item -> ret.add(row(item)));
        ret.sort(Comparator.comparing(TableRow::getColumn1));

        return ret;
    }

    public List<String> groups() {
        try {
            List<String> ret = new ArrayList<>();
            ResultSet rs = db().query(String.format("SELECT DISTINCT group_name FROM %s ORDER BY group_name + 0",
                    Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP));
            while (rs.next()) {
                ret.add(rs.getString("group_name"));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String query(String item) {
        return String.format("SELECT * FROM %s WHERE item = '%s' ORDER BY group_name + 0",
                Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP,
                item);
    }

    private List<String> items() {
        try {
            List<String> ret = new ArrayList<>();
            ResultSet rs = db().query(String.format("SELECT DISTINCT item FROM %s",
                    Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP));
            while (rs.next()) {
                ret.add(rs.getString("item"));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private TableRow row(String item) {
        try {
            TableRow row = new TableRow();
            ResultSet rs = db().query(query(item));
            rs.next();
            row.addColumn(translate(rs.getString("name")));
            row.addColumn(translate(rs.getString("unit")));
            row.addColumn(rs.getString("value"));
            while (rs.next()) {
                row.addColumn(rs.getString("value"));
            }
            return row;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
