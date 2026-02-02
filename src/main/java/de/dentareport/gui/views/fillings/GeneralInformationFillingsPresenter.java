package de.dentareport.gui.views.fillings;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.GeneralInformationFillingsAverages;
import de.dentareport.gui.table_models.GeneralInformationFillingsCountAndDistribution;
import de.dentareport.gui.table_models.TableRowGeneralInformationFillingsAverages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GeneralInformationFillingsPresenter {

    private final UiController uiController;
    private final Translate translate;
    private GeneralInformationFillingsView view;

    public GeneralInformationFillingsPresenter(UiController uiController) {
        this.uiController = uiController;
        this.translate = new Translate();
    }

    public void onBack() {
        uiController.showView(ViewId.FILLINGS);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void setView(GeneralInformationFillingsView view) {
        this.view = view;
    }

    public GeneralInformationFillingsAverages getGeneralInformationFillingsAverages() {
        try {
            List<TableRowGeneralInformationFillingsAverages> tableRows = new ArrayList<>();
            ResultSet rs = db().query("SELECT * FROM evaluation_7_averages");
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
                translate.translate(rs.getString("name")),
                translate.translate(rs.getString("unit")),
                rs.getString("average"),
                rs.getString("median"),
                rs.getString("minimum"),
                rs.getString("maximum")
        );
    }

    public GeneralInformationFillingsCountAndDistribution getGeneralInformationFillingsCountAndDistribution() {
        return null;
    }
}
