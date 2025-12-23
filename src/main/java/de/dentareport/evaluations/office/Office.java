package de.dentareport.evaluations.office;

import com.google.common.collect.ImmutableList;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbColumn;

import java.util.List;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

public class Office {

    private final Items items;

    public Office() {
        this.items = new Items();
    }

    public void evaluate() {
        rebuildTables();
        items.items().forEach(Item::evaluate);
    }

    private void rebuildTables() {
        db().rebuildTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES, columnsAverages());
        db().rebuildTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP, columnsCountPerAgeGroup());
    }

    private List<DbColumn> columnsAverages() {
        List<String> columns = ImmutableList.of(
                "item",
                "name",
                "unit",
                "average",
                "minimum",
                "maximum",
                "median"
        );
        return columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
    }

    private List<DbColumn> columnsCountPerAgeGroup() {
        List<String> columns = ImmutableList.of(
                "item",
                "name",
                "unit",
                "group_name",
                "value"
        );
        return columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
    }
}
