package de.dentareport.evaluations.meta.averages;

import java.util.List;
import java.util.Map;

public class PerCase extends Calculator {

    private Averages averages;

    public PerCase(Averages averages) {
        super(averages);
        this.averages = averages;
    }

    @Override
    public List<Map<String, String>> items() {
        return items.perCase();
    }

    @Override
    public String queryForItems() {
        return String.format("SELECT COUNT(*) as total %s FROM %s",
                queryPartialForItems(),
                averages.sourceTable());
    }

    @Override
    public String queryForMedians() {
        return "SELECT AVG(%1$s) as medians FROM(" +
                "SELECT %1$s FROM %2$s ORDER BY %1$s " +
                "LIMIT 2 - (SELECT COUNT(*) FROM %2$s) %% 2 " +
                "OFFSET (SELECT (COUNT(*) - 1) / 2 FROM %2$s))";
    }
}
