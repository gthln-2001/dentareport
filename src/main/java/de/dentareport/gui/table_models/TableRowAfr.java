package de.dentareport.gui.table_models;

public class TableRowAfr {

    private final String item;
    private final String afr5;
    private final String afr10;

    public TableRowAfr(String item, String afr5, String afr10) {
        this.item = item;
        this.afr5 = afr5;
        this.afr10 = afr10;
    }

    public String getItem() {
        return item;
    }

    public String getAfr5() {
        return afr5;
    }

    public String getAfr10() {
        return afr10;
    }
}

