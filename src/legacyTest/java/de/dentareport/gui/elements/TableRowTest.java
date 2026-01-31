package de.dentareport.gui.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class TableRowTest {

    private TableRow row;

    @BeforeEach
    public void setUp() {
        row = new TableRow("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10"
                , "c11", "c12", "c13", "c14", "c15");
    }

    @Test
    public void it_gets_up_to_fifteen_columns() {
        assertThat(row.getColumn1()).isEqualTo("c1");
        assertThat(row.getColumn2()).isEqualTo("c2");
        assertThat(row.getColumn3()).isEqualTo("c3");
        assertThat(row.getColumn4()).isEqualTo("c4");
        assertThat(row.getColumn5()).isEqualTo("c5");
        assertThat(row.getColumn6()).isEqualTo("c6");
        assertThat(row.getColumn7()).isEqualTo("c7");
        assertThat(row.getColumn8()).isEqualTo("c8");
        assertThat(row.getColumn9()).isEqualTo("c9");
        assertThat(row.getColumn10()).isEqualTo("c10");
        assertThat(row.getColumn11()).isEqualTo("c11");
        assertThat(row.getColumn12()).isEqualTo("c12");
        assertThat(row.getColumn13()).isEqualTo("c13");
        assertThat(row.getColumn14()).isEqualTo("c14");
        assertThat(row.getColumn15()).isEqualTo("c15");
    }

    @Test
    public void it_sets_up_to_fifteen_columns() {
        row.setColumn1("new1");
        row.setColumn2("new2");
        row.setColumn3("new3");
        row.setColumn4("new4");
        row.setColumn5("new5");
        row.setColumn6("new6");
        row.setColumn7("new7");
        row.setColumn8("new8");
        row.setColumn9("new9");
        row.setColumn10("new10");
        row.setColumn11("new11");
        row.setColumn12("new12");
        row.setColumn13("new13");
        row.setColumn14("new14");
        row.setColumn15("new15");

        assertThat(row.getColumn1()).isEqualTo("new1");
        assertThat(row.getColumn2()).isEqualTo("new2");
        assertThat(row.getColumn3()).isEqualTo("new3");
        assertThat(row.getColumn4()).isEqualTo("new4");
        assertThat(row.getColumn5()).isEqualTo("new5");
        assertThat(row.getColumn6()).isEqualTo("new6");
        assertThat(row.getColumn7()).isEqualTo("new7");
        assertThat(row.getColumn8()).isEqualTo("new8");
        assertThat(row.getColumn9()).isEqualTo("new9");
        assertThat(row.getColumn10()).isEqualTo("new10");
        assertThat(row.getColumn11()).isEqualTo("new11");
        assertThat(row.getColumn12()).isEqualTo("new12");
        assertThat(row.getColumn13()).isEqualTo("new13");
        assertThat(row.getColumn14()).isEqualTo("new14");
        assertThat(row.getColumn15()).isEqualTo("new15");
    }

    @Test
    public void it_adds_columns_to_row() {
        TableRow row = new TableRow();
        row.addColumn("foo");
        row.addColumn("bar");
        row.addColumn("biz");

        assertThat(row.getColumn1()).isEqualTo("foo");
        assertThat(row.getColumn2()).isEqualTo("bar");
        assertThat(row.getColumn3()).isEqualTo("biz");
    }
}