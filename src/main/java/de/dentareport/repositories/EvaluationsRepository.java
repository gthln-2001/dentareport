package de.dentareport.repositories;

import de.dentareport.utils.db.DbColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

public class EvaluationsRepository {

    public void rebuildTable(String name,
                             Set<String> columns) {
        List<DbColumn> tableColumns = new ArrayList<>();
        tableColumns.add(new DbColumn("id", "integer primary key"));
        tableColumns.addAll(columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList()));
        db().rebuildTable(name, tableColumns);
    }
}
