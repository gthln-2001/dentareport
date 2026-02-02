package de.dentareport.gui.views.telescopic_crowns;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.GeneralInformationTelescopicCrownsAverages;
import de.dentareport.gui.table_models.GeneralInformationTelescopicCrownsCountAndDistribution;
import de.dentareport.gui.table_models.TableRowGeneralInformationTelescopicCrownsAverages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GeneralInformationTelescopicCrownsPresenter {

    private final UiController uiController;
    private final Translate translate;
    private GeneralInformationTelescopicCrownsView view;

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

    public void setView(GeneralInformationTelescopicCrownsView view) {
        this.view = view;
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
                translate.translate(rs.getString("name")),
                translate.translate(rs.getString("unit")),
                rs.getString("average"),
                rs.getString("median"),
                rs.getString("minimum"),
                rs.getString("maximum")
        );
    }

    public GeneralInformationTelescopicCrownsCountAndDistribution getGeneralInformationTelescopicCrownsCountAndDistribution() {
        return null;
    }
}
