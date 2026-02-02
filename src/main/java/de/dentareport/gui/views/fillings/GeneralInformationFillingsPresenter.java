package de.dentareport.gui.views.fillings;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.GeneralInformationFillingsAverages;
import de.dentareport.gui.table_models.GeneralInformationFillingsCountAndDistribution;
import de.dentareport.gui.table_models.TableRowGeneralInformationFillingsAverages;
import de.dentareport.gui.table_models.TableRowGeneralInformationFillingsCountAndDistribution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GeneralInformationFillingsPresenter {

    private final UiController uiController;
    private final Translate translate;
    private Map<String, TableRowGeneralInformationFillingsCountAndDistribution> count;
    private Map<String, TableRowGeneralInformationFillingsCountAndDistribution> distribution;

    public GeneralInformationFillingsPresenter(UiController uiController) {
        this.uiController = uiController;
        this.translate = new Translate();
    }

    public void onBack() {
        uiController.showView(ViewId.TELESCOPIC_CROWNS);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public GeneralInformationFillingsAverages getGeneralInformationFillingsAverages() {
        try {
            List<TableRowGeneralInformationFillingsAverages> tableRows = new ArrayList<>();
            ResultSet rs = db().query("SELECT * FROM evaluation_9_averages");
            while (rs.next()) {
                tableRows.add(rowAverages(rs));
            }
            return new GeneralInformationFillingsAverages(tableRows);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private TableRowGeneralInformationFillingsAverages rowAverages(ResultSet rs) throws SQLException {
        return new TableRowGeneralInformationFillingsAverages(
                translate.translate(rs.getString("name"), "9"),
                translate.translate(rs.getString("unit")),
                rs.getString("average"),
                rs.getString("median"),
                rs.getString("minimum"),
                rs.getString("maximum")
        );
    }

    public GeneralInformationFillingsCountAndDistribution getGeneralInformationFillingsCountAndDistribution() {
        prepareData();
        List<TableRowGeneralInformationFillingsCountAndDistribution> tableRows = new ArrayList<>();
        tableRows.add(count.get("patient_count"));
        tableRows.add(distribution.get("gender"));
        tableRows.add(distribution.get("insurance"));
        tableRows.add(count.get("case_count"));
        tableRows.add(count.get("tooth_loss_count"));
        tableRows.add(count.get("rezementierung_count"));
        tableRows.add(count.get("endodontie_count"));
        tableRows.add(count.get("wurzelstift_count"));
        tableRows.add(distribution.get("toothtype"));
        tableRows.add(distribution.get("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation"));
        tableRows.add(distribution.get("toothcontacts__of__evidence_01_position_first_after_date_start_observation"));

        return new GeneralInformationFillingsCountAndDistribution(tableRows);
    }

    private void prepareData() {
        try {
            count = count();
            distribution = distribution();
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private Map<String, TableRowGeneralInformationFillingsCountAndDistribution> count() throws SQLException {
        Map<String, TableRowGeneralInformationFillingsCountAndDistribution> ret = new HashMap<>();
        ResultSet rs = db().query("SELECT * FROM evaluation_7_counts");
        while (rs.next()) {
            ret.put(rs.getString("item"), rowCount(rs));
        }
        return ret;
    }

    private TableRowGeneralInformationFillingsCountAndDistribution rowCount(ResultSet rs) throws SQLException {
        return new TableRowGeneralInformationFillingsCountAndDistribution(
                translate.translate(rs.getString("name"), "7"),
                rs.getString("value")
        );
    }

    private Map<String, TableRowGeneralInformationFillingsCountAndDistribution> distribution() throws SQLException {
        Map<String, TableRowGeneralInformationFillingsCountAndDistribution> ret = new HashMap<>();
        concatenateValues(db().query("SELECT * FROM evaluation_7_distributions")).forEach(
                (key, value) -> ret.put(key, rowDistribution(value))
        );
        return ret;
    }

    private TableRowGeneralInformationFillingsCountAndDistribution rowDistribution(Map<String, String> value) {
        return new TableRowGeneralInformationFillingsCountAndDistribution(translate.translate(value.get("name"), "7")
                , value.get("value"));
    }

    private Map<String, Map<String, String>> concatenateValues(ResultSet rs) throws SQLException {
        Map<String, Map<String, String>> data = new HashMap<>();
        while (rs.next()) {
            if (data.containsKey(rs.getString("item"))) {
                data.get(rs.getString("item"))
                        .put("value", String.format("%s, %s: %s",
                                data.get(rs.getString("item")).get("value"),
                                translate.translate(rs.getString("value")),
                                rs.getString("value_count")));
            } else {
                Map<String, String> newValue = new HashMap<>();
                newValue.put("name", rs.getString("name"));
                newValue.put("value", String.format("%s: %s",
                        translate.translate(rs.getString("value")),
                        rs.getString("value_count")));
                data.put(rs.getString("item"), newValue);
            }
        }
        return data;
    }


}
