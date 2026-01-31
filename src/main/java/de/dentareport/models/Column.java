package de.dentareport.models;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.dentareport.Config;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.columns.EvaluationColumn;
import de.dentareport.exceptions.DentareportIllegalAccessException;
import de.dentareport.utils.Keys;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static de.dentareport.utils.Keys.*;

// TODO: TEST?
public class Column {

    private final AvailableColumns availableColumns;
    private final Evaluation evaluation;
    private final String name;
    private final Set<String> transformableKeywords = ImmutableSet.of(
            AFTER,
            AT,
            BEFORE,
            FROM,
            OF,
            OR_UNTIL,
            UNTIL
    );
    private final Set<String> simpleKeywords = ImmutableSet.of(
            AS,
            FILTER_ONLY,
            FORMAT,
            IS,
            ON,
            PER,
            POSITION,
            SPECIFICATION,
            SURFACE,
            WITH
    );
    private String className;
    private String fullyQualifiedClassName;
    private Map<String, String> options;

    public Column(Evaluation evaluation, String name) {
        this.evaluation = evaluation;
        this.name = name;
        this.availableColumns = new AvailableColumns();
        setProperties();
    }

    public String name() {
        return name;
    }

    public String className() {
        return className;
    }

    public String fullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    public CaseData evaluate(RawData rawData, CaseData caseData) {
        return this.instance().evaluate(rawData, caseData);
    }

    public EvaluationColumn instance() {
        try {
            return (EvaluationColumn) Class.forName(fullyQualifiedClassName())
                                           .getConstructor(Evaluation.class, Map.class)
                                           .newInstance(evaluation, options);
        } catch (InstantiationException
                | IllegalAccessException
                | ClassNotFoundException
                | NoSuchMethodException
                | InvocationTargetException e) {
            throw new DentareportIllegalAccessException(e);
        }
    }

    public List<String> dependencies() {
        return instance().dependencies();
    }

    public int hierarchyLevel() {
        int level = 0;
        for (String column : dependencies()) {
            level = Math.max(level, (new Column(evaluation, column).hierarchyLevel() + 1));
        }
        return level;
    }

    public Map<String, String> options() {
        return options;
    }

    public Map<String, String> documentation() {
        EvaluationColumn column = instance();
        Map<String, String> ret = new HashMap<>();
        ret.put(Keys.LANG_DE, column.documentationShortDe());
        ret.put(Keys.LANG_DE + Keys.SUFFIX_LONG, column.documentationLongDe());
        return ret;
    }

    public boolean isTranslatable() {
        return instance().isTranslatable();
    }

    public boolean isDate() {
        return instance().isDate();
    }

    public boolean isGroupable() {
        return Objects.equals(className(), Keys.CLASSNAME_OF_COLUMNS_TO_GROUP);
    }

    public String option(String key) {
        if (!options().containsKey(key)) {
            throw new IllegalArgumentException(key);
        }
        return options().get(key);
    }

    private void setProperties() {
        className = parseClassname(name);
        options = parseOptions(name);
        fullyQualifiedClassName = Config.evaluationColumnsPath() + className;
    }

    private String parseClassname(String name) {
        if (isNotParseable(name)) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
        }
        return parseClassname(name.split("__")[0]);
    }

    private Map<String, String> parseOptions(String name) {
        if (isNotParseable(name)) {
            return ImmutableMap.of(ORIGINAL_NAME, name);
        }
        return parseNameIntoOptions(name);
    }

    private boolean isNotParseable(String name) {
        return !name.contains("__");
    }

    private Map<String, String> parseNameIntoOptions(String name) {
        Map<String, String> ret = parseChunks(splitNameToChunks(name));
        ret.put(ORIGINAL_NAME, name);
        return ret;
    }

    private List<String> splitNameToChunks(String name) {
        List<String> chunks = new ArrayList<>(Arrays.asList(name.split("__")));
        chunks.remove(0);
        return chunks;
    }

    private Map<String, String> parseChunks(List<String> chunks) {
        Map<String, String> ret = new HashMap<>();
        for (int i = 0; i < chunks.size(); i = i + 2) {
            String key = chunks.get(i);
            String value = chunks.get(i + 1);
            if (valueHasToBeTransformed(key, value)) {
                value = availableColumns.relatedColumn(value);
            }
            validateKeyword(key);
            ret.put(key, value);
        }
        return ret;
    }

    private boolean valueHasToBeTransformed(String key, String value) {
        return transformableKeywords.contains(key) && !availableColumns.has(value);
    }

    private void validateKeyword(String key) {
        if (!simpleKeywords.contains(key) && !transformableKeywords.contains(key)) {
            throw new IllegalArgumentException(key);
        }
    }
}
