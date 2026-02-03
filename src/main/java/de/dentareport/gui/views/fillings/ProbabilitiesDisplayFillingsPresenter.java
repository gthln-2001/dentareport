package de.dentareport.gui.views.fillings;

// TODO: Test?

import de.dentareport.Translate;
import de.dentareport.evaluations.meta.dependencies.Dependencies;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.table_models.TableAfr;
import de.dentareport.gui.table_models.TableGroupSizes;
import de.dentareport.gui.table_models.TableRowAfr;
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

    public TableAfr getTableAfr(String event, String dependency) {
        List<TableRowAfr> tableRows = new ArrayList<>();
        List<String> afrGroups = afrGroups(event, dependency);

        for (String group : afrGroups) {
            ResultSet rs = db().query(afrQuery(group, event, dependency));
            String afr5 = "";
            String afr10 = "";
            try {
                while (rs.next()) {
                    String interval = rs.getString("interval");
                    String afr = rs.getString("afr");
                    if (Objects.equals(interval, "5")) afr5 = String.format("%s%%", afr);
                    if (Objects.equals(interval, "10")) afr10 = String.format("%s%%", afr);
                }
            } catch (SQLException e) {
                throw new DentareportSqlException(e);
            }
            tableRows.add(new TableRowAfr(group, afr5, afr10));
        }

        return new TableAfr(tableRows);
    }

    private String groupsQuery(String event, String dependency) {
        return String.format(
                "SELECT DISTINCT group_name FROM evaluation_%s%s " +
                        "WHERE event = '%s' AND dependency = '%s' " +
                        "ORDER BY group_order",
                "7",
                Keys.DB_TABLE_SUFFIX_AFR,
                event,
                dependency
        );
    }

    private String afrQuery(String group, String event, String dependency) {
        return String.format(
                "SELECT interval, afr FROM evaluation_%s%s " +
                        "WHERE event = '%s' AND dependency = '%s' AND group_name = '%s'" +
                        "ORDER BY interval",
                "7",
                Keys.DB_TABLE_SUFFIX_AFR,
                event,
                dependency,
                group
        );
    }

    private List<String> afrGroups(String event, String dependency) {
        try {
            List<String> ret = new ArrayList<>();
            ResultSet rs = db().query(groupsQuery(event, dependency));
            while (rs.next()) {
                ret.add(rs.getString("group_name"));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
