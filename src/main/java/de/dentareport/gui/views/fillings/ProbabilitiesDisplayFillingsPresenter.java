package de.dentareport.gui.views.fillings;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.evaluations.meta.dependencies.Dependencies;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.TableGroupSizes;
import de.dentareport.gui.table_models.TableRowGroupSizes;
import de.dentareport.utils.Keys;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.dentareport.utils.Keys.GUI_TEXT_NO_DEPENDENCY;
import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class ProbabilitiesDisplayFillingsPresenter {

    private final UiController uiController;
    private final Dependencies dependencies;
    private final Translate translate;

    public ProbabilitiesDisplayFillingsPresenter(UiController uiController) {
        this.uiController = uiController;
        this.translate = new Translate();
        this.dependencies = new Dependencies();
    }

    public void onBack() {
        uiController.showView(ViewId.PROBABILITIES_FILLINGS);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public TableGroupSizes getTableGroupSizes(String event, String dependency) {
        try {
            List<TableRowGroupSizes> tableRows = new ArrayList<>();

            ResultSet rs;
            if (Objects.equals(dependency, GUI_TEXT_NO_DEPENDENCY)) {
                rs = db().query(String.format(
                        "SELECT '%s' AS item, COUNT(*) as item_count FROM evaluation_%s",
                        Keys.GUI_TEXT_TOTAL,
                        "7"
                ));
            } else {
                rs = db().query(
                        String.format(
                                "SELECT %1$s AS item, COUNT(%1$s) as item_count " +
                                        "FROM evaluation_%2$s " +
                                        "WHERE %1$s NOT IN ('', '%3$s') " +
                                        "GROUP BY %1$s " +
                                        "ORDER BY %4$s",
                                dependencies.groupColumn(dependency),
                                "7",
                                Keys.NO_DATA,
                                dependencies.orderColumn(dependency)
                        ));
            }

            while (rs.next()) {
                tableRows.add(rowGroupSizes(rs));
            }
            return new TableGroupSizes(tableRows);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private TableRowGroupSizes rowGroupSizes(ResultSet rs) throws SQLException {
        return new TableRowGroupSizes(
                translate.translate(rs.getString("item")),
                rs.getString("item_count")
        );
    }
}
