package de.dentareport.repositories;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
//import javafx.scene.chart.XYChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class KaplanMeierRepository {

    private Map<String, String> options;

//    public List<XYChart.Series<Number, Number>> lines(Map<String, String> options) {
//        this.options = options;
//
//        return groups().stream()
//                .map(this::kaplanMeierForGroup)
//                .collect(Collectors.toList());
//    }

    private List<String> groups() {
        try {
            List<String> ret = new ArrayList<>();
            ResultSet rs = db().query(groupsQuery());
            while (rs.next()) {
                ret.add(rs.getString("group_name"));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String groupsQuery() {
        return String.format(
                "SELECT DISTINCT group_name FROM evaluation_%s%s " +
                        "WHERE event = '%s' AND dependency = '%s' " +
                        "ORDER BY group_order",
                options.get("evaluationId"),
                Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER,
                options.get("event"),
                options.get("dependency")
        );
    }

//    private XYChart.Series<Number, Number> kaplanMeierForGroup(String group) {
//        try {
//            XYChart.Series<Number, Number> chart = new XYChart.Series<>();
//            chart.setName(group);
//            ResultSet rs = db().query(kaplanMeierQuery(group));
//            double previousY = 1.0;
//            while (rs.next()) {
//                double x = rs.getDouble("x");
//                double y = rs.getDouble("y");
//                chart.getData().add(new XYChart.Data<>(x / 365, previousY));
//                chart.getData().add(new XYChart.Data<>(x / 365, y));
//                previousY = y;
//            }
//            return chart;
//        } catch (SQLException e) {
//            throw new DentareportSqlException(e);
//        }
//    }

    private String kaplanMeierQuery(String group) {
        return String.format(
                "SELECT x, y FROM evaluation_%s%s " +
                        "WHERE event = '%s' AND dependency = '%s' AND group_name = '%s'" +
                        "ORDER BY x + 0",
                options.get("evaluationId"),
                Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER,
                options.get("event"),
                options.get("dependency"),
                group
        );
    }
}
