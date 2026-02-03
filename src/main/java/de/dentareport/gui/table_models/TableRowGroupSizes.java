package de.dentareport.gui.table_models;

public class TableRowGroupSizes {

    private final String item;
    private final String itemCount;

    public TableRowGroupSizes(String item, String item_count) {
        this.item = item;
        this.itemCount = item_count;
    }

    public String getItem() {
        return item;
    }

    public String getItemCount() {
        return itemCount;
    }
}

