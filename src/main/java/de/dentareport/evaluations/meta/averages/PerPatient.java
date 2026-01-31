package de.dentareport.evaluations.meta.averages;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: TEST?
public class PerPatient extends Calculator {

    private Averages averages;

    public PerPatient(Averages averages) {
        super(averages);
        this.averages = averages;
    }

    @Override
    public List<Map<String, String>> items() {
        return items.perPatient();
    }

    @Override
    public String queryForItems() {
        return String.format("SELECT COUNT(*) as total %s FROM (SELECT %s FROM %s GROUP BY patient_index)",
                queryPartialForItems(),
                items().stream().map(i -> i.get("column")).collect(Collectors.joining(",")),
                averages.sourceTable());
    }

    @Override
    public String queryForMedians() {
        return "SELECT AVG(%1$s) as medians FROM(" +
                "SELECT %1$s FROM (SELECT * FROM %2$s GROUP BY patient_index) ORDER BY %1$s " +
                "LIMIT 2 - (SELECT COUNT(*) FROM (SELECT * FROM %2$s GROUP BY patient_index)) %% 2 " +
                "OFFSET (SELECT (COUNT(*) - 1) / 2 FROM (SELECT * FROM %2$s GROUP BY patient_index)))";
    }
}
