package de.dentareport.gui.tables;

import de.dentareport.gui.elements.TableRow;
import de.dentareport.repositories.AfrRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toCollection;

public class AnnualFailureRate extends Table {

    private final AfrRepository afrRepository;

    public AnnualFailureRate() {
        this.afrRepository = new AfrRepository();
    }

    public ObservableList<TableRow> data(Map<String, String> options) {
        return afrRepository.afrTable(options)
                .entrySet().stream()
                .map(this::tableRow)
                .collect(toCollection(FXCollections::observableArrayList));
    }

    private TableRow tableRow(Map.Entry<String, Map<String, String>> element) {
        return new TableRow(
                translate(element.getKey()),
                afrValueFiveYears(element.getValue()),
                afrValueTenYears(element.getValue())
        );
    }

    private String afrValueFiveYears(Map<String, String> afr) {
        return afrValue(afr, "5");
    }

    private String afrValueTenYears(Map<String, String> afr) {
        return afrValue(afr, "10");
    }

    private String afrValue(Map<String, String> afr,
                            String key) {
        String ret = afr.getOrDefault(key, "");
        return Objects.equals(ret, "")
                ? ret
                : String.format("%s%%", ret);
    }
}
