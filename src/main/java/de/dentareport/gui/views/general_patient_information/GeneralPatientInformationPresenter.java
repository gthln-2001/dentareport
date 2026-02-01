package de.dentareport.gui.views.general_patient_information;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.GeneralPatientInformationAverages;
import de.dentareport.gui.table_models.GeneralPatientInformationGroupedByAge;
import de.dentareport.gui.table_models.TableRowGeneralPatientInformationAverages;
import de.dentareport.gui.table_models.TableRowGeneralPatientInformationGroupedByAge;
import de.dentareport.utils.Keys;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GeneralPatientInformationPresenter {

    private final UiController uiController;
    private final Translate translate;

    public GeneralPatientInformationPresenter(UiController uiController) {
        this.uiController = uiController;
        this.translate = new Translate();
    }

    public GeneralPatientInformationAverages getGeneralPatientInformationAverages() {
        try {
            List<TableRowGeneralPatientInformationAverages> tableRows = new ArrayList<>();
            ResultSet rs = db().query(String.format("SELECT * FROM %s",
                    Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES));
            while (rs.next()) {
                tableRows.add(rowAverages(rs));
            }
            return new GeneralPatientInformationAverages(tableRows);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    public GeneralPatientInformationGroupedByAge getGeneralPatientInformationGroupedByAge() {
        List<TableRowGeneralPatientInformationGroupedByAge> tableRows = new ArrayList<>();
        items().forEach(item -> tableRows.add(rowGroupedByAge(item)));

        return new GeneralPatientInformationGroupedByAge(tableRows);
    }

    public void onBack() {
        uiController.showView(ViewId.EVALUATION);
    }

    public void onExitRequested() {
        uiController.confirmExit();
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

    private TableRowGeneralPatientInformationAverages rowAverages(ResultSet rs) throws SQLException {
        return new TableRowGeneralPatientInformationAverages(
                translate.translate(rs.getString("name")),
                translate.translate(rs.getString("unit")),
                rs.getString("average"),
                rs.getString("median"),
                rs.getString("minimum"),
                rs.getString("maximum")
        );
    }

    private TableRowGeneralPatientInformationGroupedByAge rowGroupedByAge(String item) {
        try {
            ResultSet rs = db().query(String.format("SELECT * FROM %s WHERE item = '%s' ORDER BY group_name + 0",
                    Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP,
                    item));
            rs.next();
            String name = translate.translate(rs.getString("name"));
            String unit = translate.translate(rs.getString("unit"));
            String ageGroup1 = rs.getString("value");
            rs.next();
            String ageGroup2 = rs.getString("value");
            rs.next();
            String ageGroup3 = rs.getString("value");
            rs.next();
            String ageGroup4 = rs.getString("value");
            rs.next();
            String ageGroup5 = rs.getString("value");
            rs.next();
            String ageGroup6 = rs.getString("value");
            rs.next();
            String ageGroup7 = rs.getString("value");
            rs.next();
            String ageGroup8 = rs.getString("value");
            rs.next();
            String ageGroup9 = rs.getString("value");

            return new TableRowGeneralPatientInformationGroupedByAge(
                    name,
                    unit,
                    ageGroup1,
                    ageGroup2,
                    ageGroup3,
                    ageGroup4,
                    ageGroup5,
                    ageGroup6,
                    ageGroup7,
                    ageGroup8,
                    ageGroup9
            );
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }

    }
}
