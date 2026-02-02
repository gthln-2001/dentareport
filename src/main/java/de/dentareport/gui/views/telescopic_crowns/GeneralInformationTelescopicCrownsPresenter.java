package de.dentareport.gui.views.telescopic_crowns;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.GeneralInformationTelescopicCrownsAverages;
import de.dentareport.gui.table_models.GeneralInformationTelescopicCrownsCountAndDistribution;
import de.dentareport.gui.table_models.TableRowGeneralInformationTelescopicCrownsAverages;
import de.dentareport.gui.table_models.TableRowGeneralInformationTelescopicCrownsCountAndDistribution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GeneralInformationTelescopicCrownsPresenter {

    private final UiController uiController;
    private final Translate translate;

    public GeneralInformationTelescopicCrownsPresenter(UiController uiController) {
        this.uiController = uiController;
        this.translate = new Translate();
    }

    public void onBack() {
        uiController.showView(ViewId.TELESCOPIC_CROWNS);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public GeneralInformationTelescopicCrownsAverages getGeneralInformationTelescopicCrownsAverages() {
        try {
            List<TableRowGeneralInformationTelescopicCrownsAverages> tableRows = new ArrayList<>();
            ResultSet rs = db().query("SELECT * FROM evaluation_9_averages");
            while (rs.next()) {
                tableRows.add(rowAverages(rs));
            }
            return new GeneralInformationTelescopicCrownsAverages(tableRows);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private TableRowGeneralInformationTelescopicCrownsAverages rowAverages(ResultSet rs) throws SQLException {
        return new TableRowGeneralInformationTelescopicCrownsAverages(
                translate.translate(rs.getString("name"), "9"),
                translate.translate(rs.getString("unit")),
                rs.getString("average"),
                rs.getString("median"),
                rs.getString("minimum"),
                rs.getString("maximum")
        );
    }

    public GeneralInformationTelescopicCrownsCountAndDistribution getGeneralInformationTelescopicCrownsCountAndDistribution() {
//        try {
        List<TableRowGeneralInformationTelescopicCrownsCountAndDistribution> tableRows = new ArrayList<>();
        tableRows.add(new TableRowGeneralInformationTelescopicCrownsCountAndDistribution("tk1", "1"));
        tableRows.add(new TableRowGeneralInformationTelescopicCrownsCountAndDistribution("tk2", "2"));
//            ResultSet rs = db().query("SELECT * FROM evaluation_9_averages");
//            while (rs.next()) {
//                tableRows.add(rowAverages(rs));
//            }
        return new GeneralInformationTelescopicCrownsCountAndDistribution(tableRows);
//        } catch (SQLException e) {
//            throw new DentareportSqlException(e);
//        }
    }

//
//    public ObservableList<TableRow> data() {
//        prepareData();
//
//        ObservableList<TableRow> ret = FXCollections.observableArrayList();
//        ret.add(count.get("patient_count"));
//        ret.add(distribution.get("gender"));
//        ret.add(distribution.get("insurance"));
//        ret.add(count.get("case_count"));
//        ret.add(count.get("tooth_loss_count"));
//        ret.add(count.get("rezementierung_count"));
//        ret.add(count.get("endodontie_count"));
//        ret.add(count.get("wurzelstift_count"));
//        ret.add(distribution.get("toothtype"));
//        ret.add(distribution.get("endstaendigkeit__of__evidence_01_position_first_after_date_start_observation"));
//        ret.add(distribution.get("toothcontacts__of__evidence_01_position_first_after_date_start_observation"));
//
//        return ret;
//    }
//
//    private void prepareData() {
//        try {
//            count = count();
//            distribution = distribution();
//        } catch (SQLException e) {
//            throw new DentareportSqlException(e);
//        }
//    }
//
//    private Map<String, TableRow> count() throws SQLException {
//        Map<String, TableRow> ret = new HashMap<>();
//        ResultSet rs = db().query("SELECT * FROM evaluation_" + this.options.get("evaluationId") + "_counts");
//        while (rs.next()) {
//            ret.put(rs.getString("item"), rowCount(rs));
//        }
//        return ret;
//    }
//
//    private TableRow rowCount(ResultSet rs) throws SQLException {
//        return new TableRow(
//                translate(rs.getString("name"), this.options.get("evaluationId")),
//                rs.getString("value")
//        );
//    }
//
//    private Map<String, TableRow> distribution() throws SQLException {
//        Map<String, TableRow> ret = new HashMap<>();
//        concatenateValues(db().query("SELECT * FROM evaluation_"
//                + this.options.get("evaluationId")
//                + "_distributions")).forEach(
//                (key, value) -> ret.put(key, rowDistribution(value))
//        );
//        return ret;
//    }
//
//    private TableRow rowDistribution(Map<String, String> value) {
//        return new TableRow(translate(value.get("name"), this.options.get("evaluationId")), value.get("value"));
//    }
//
//    private Map<String, Map<String, String>> concatenateValues(ResultSet rs) throws SQLException {
//        Map<String, Map<String, String>> data = new HashMap<>();
//
//        while (rs.next()) {
//            if (data.containsKey(rs.getString("item"))) {
//                data.get(rs.getString("item"))
//                        .put("value", String.format("%s, %s: %s",
//                                data.get(rs.getString("item")).get("value"),
//                                translate(rs.getString("value")),
//                                rs.getString("value_count")));
//            } else {
//                Map<String, String> newValue = new HashMap<>();
//                newValue.put("name", rs.getString("name"));
//                newValue.put("value", String.format("%s: %s",
//                        translate(rs.getString("value")),
//                        rs.getString("value_count")));
//                data.put(rs.getString("item"), newValue);
//            }
//        }
//        return data;
//    }


}
