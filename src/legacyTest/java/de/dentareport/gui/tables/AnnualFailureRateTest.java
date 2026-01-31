package de.dentareport.gui.tables;

import com.google.common.collect.ImmutableMap;
import de.dentareport.Translate;
import de.dentareport.gui.elements.TableRow;
import de.dentareport.repositories.AfrRepository;
import javafx.collections.ObservableList;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class AnnualFailureRateTest {

    @Test
    public void it_creates_observable_list_from_afr_data(@Mocked Translate mockTranslate,
                                                         @Mocked AfrRepository mockAfrRepository) {
        Map<String, String> options = ImmutableMap.of("foo", "bar");

        new Expectations() {{
            mockAfrRepository.afrTable(options);
            result = testAfrTable();
            mockTranslate.translate("row1");
            result = "trans1";
            mockTranslate.translate("row2");
            result = "trans2";
            mockTranslate.translate("row3");
            result = "trans3";
        }};

        ObservableList<TableRow> data = new AnnualFailureRate().data(options);

        assertThat(data.size()).isEqualTo(3);

        assertThat(data.get(0).getColumn1()).isEqualTo("trans1");
        assertThat(data.get(0).getColumn2()).isEqualTo("23%");
        assertThat(data.get(0).getColumn3()).isEqualTo("42.42%");

        assertThat(data.get(1).getColumn1()).isEqualTo("trans2");
        assertThat(data.get(1).getColumn2()).isEqualTo("");
        assertThat(data.get(1).getColumn3()).isEqualTo("");

        assertThat(data.get(2).getColumn1()).isEqualTo("trans3");
        assertThat(data.get(2).getColumn2()).isEqualTo("");
        assertThat(data.get(2).getColumn3()).isEqualTo("");
    }

    private LinkedHashMap<String, Map<String, String>> testAfrTable() {
        LinkedHashMap<String, Map<String, String>> ret = new LinkedHashMap<>();
        ret.put("row1", ImmutableMap.of("5", "23", "10", "42.42"));
        ret.put("row2", ImmutableMap.of("5", "", "10", ""));
        ret.put("row3", ImmutableMap.of("4", "11", "9", "22"));
        return ret;
    }
}