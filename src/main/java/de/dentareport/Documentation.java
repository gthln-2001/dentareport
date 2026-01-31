package de.dentareport;

import de.dentareport.models.Column;
import de.dentareport.utils.Keys;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// TODO: TEST?
public class Documentation {

    private final Translate translate;
    private Map<String, Map<String, String>> documentation = new HashMap<>();
    private Pattern columnname = Pattern.compile(Pattern.quote("##") + "(.*?)" + Pattern.quote("##"));
    private Pattern billingposition = Pattern.compile(Pattern.quote("~~") + "(.*?)" + Pattern.quote("~~"));
    private Set<String> occuringBillingpositions = new HashSet<>();
    private String documentationType;
    private List<Column> columns;

    public Documentation(List<Column> columns) {
        this.columns = columns;
        this.translate = new Translate();
    }

    public void document() {
        columns.forEach(c -> documentation.put(
                c.name(),
                parseColumnDocumentation(c.documentation())));
    }

    public String shortDoc(String column) {
        return documentation.get(column).get(Keys.LANG_DE);
    }

    public String longDoc(String column) {
        return documentation.get(column).get(Keys.LANG_DE + Keys.SUFFIX_LONG);
    }

    public Set<String> occuringBillingpositions() {
        return occuringBillingpositions;
    }

    private Map<String, String> parseColumnDocumentation(Map<String, String> columnDocumentation) {
        return columnDocumentation
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, this::parseDocumentation));
    }

    private String parseDocumentation(Map.Entry<String, String> documentation) {
        documentationType = documentation.getKey();
        return parseBillingposition(parseColumnname(documentation.getValue()));
    }

    private String parseColumnname(String text) {
        return replaceAllColumnnames(text, columnname.matcher(text));
    }

    private String replaceAllColumnnames(String text, Matcher matcher) {
        while (matcher.find()) {
            text = replaceColumnname(matcher.group(1), text);
        }
        return text;
    }

    private String replaceColumnname(String columnname, String text) {
        return text.replace(
                String.format("##%s##", columnname),
                replacement(columnname));
    }

    private String replacement(String columnname) {
        return documentation
                .getOrDefault(columnname, new HashMap<>())
                .getOrDefault(documentationType, columnname);
    }

    private String parseBillingposition(String text) {
        return replaceAllBillingpositions(text, billingposition.matcher(text));
    }

    private String replaceAllBillingpositions(String text,
                                              Matcher matcher) {
        while (matcher.find()) {
            occuringBillingpositions.add(matcher.group(1));
            text = replaceBillingposition(matcher.group(1), text);
        }
        return text;
    }

    private String replaceBillingposition(String billingposition,
                                          String text) {
        return text.replace(
                String.format("~~%s~~", billingposition),
                String.format("°°%s°°", translate.translate(billingposition)));
    }
}