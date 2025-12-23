package de.dentareport.evaluations.meta.dependencies;

public class Dependency {

    private String name;
    private String groupColumn;
    private String orderColumn;

    public Dependency(String name,
                      String groupColumn,
                      String orderColumn) {
        this.name = name;
        this.groupColumn = groupColumn;
        this.orderColumn = orderColumn;
    }

    public String name() {
        return name;
    }

    public String groupColumn() {
        return groupColumn;
    }

    public String orderColumn() {
        return orderColumn;
    }
}
