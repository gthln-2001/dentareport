package de.dentareport.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.Config;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.columns.EvaluationColumn;
import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class ColumnTest {

    @Test
    public void it_has_name(@Mocked Evaluation mockEvaluation) {
        Column foo = new Column(mockEvaluation, "bar");

        assertThat(foo.name()).isEqualTo("bar");
    }

    @Test
    public void it_parses_classname(@Mocked Evaluation mockEvaluation,
                                    @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
        }};

        assertThat(new Column(mockEvaluation, "tooth").className()).isEqualTo("Tooth");
        assertThat(new Column(mockEvaluation, "patient_index").className()).isEqualTo("PatientIndex");
        assertThat(new Column(mockEvaluation, "date__of__foo").className()).isEqualTo("Date");
        assertThat(new Column(mockEvaluation, "foo_bar__on__baz_boz__position__bez").className()).isEqualTo("FooBar");
    }

    @Test
    public void it_parses_options(@Mocked Evaluation mockEvaluation,
                                  @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
        }};
        assertThat(new Column(mockEvaluation, "tooth").options().size()).isEqualTo(1);
        assertThat(new Column(mockEvaluation, "tooth").options().get(ORIGINAL_NAME)).isEqualTo("tooth");

        assertThat(new Column(mockEvaluation, "patient_index").options().size()).isEqualTo(1);
        assertThat(new Column(mockEvaluation, "patient_index").options().get(ORIGINAL_NAME)).isEqualTo("patient_index");

        assertThat(new Column(mockEvaluation, "date__of__foo").options().size()).isEqualTo(2);
        assertThat(new Column(mockEvaluation, "date__of__foo").options().get(ORIGINAL_NAME)).isEqualTo("date__of__foo");
        assertThat(new Column(mockEvaluation, "date__of__foo").options().get(OF)).isEqualTo("foo");

        assertThat(new Column(mockEvaluation, "age__at__foo_bar").options().size()).isEqualTo(2);
        assertThat(new Column(mockEvaluation, "age__at__foo_bar").options().get(ORIGINAL_NAME)).isEqualTo("age__at__foo_bar");
        assertThat(new Column(mockEvaluation, "age__at__foo_bar").options().get(AT)).isEqualTo("foo_bar");

        new Verifications() {{
            mockAvailableColumns.relatedColumn(anyString);
            times = 0;
        }};
    }

    @Test
    public void it_parses_options_when_they_have_to_be_translated_into_column_names(
            @Mocked Evaluation mockEvaluation,
            @Mocked AvailableColumns mockAvailableColumns) {

        new Expectations() {{
            mockAvailableColumns.has("foo");
            result = false;
            mockAvailableColumns.relatedColumn("foo");
            result = "foo-transformed";
        }};

        assertThat(new Column(mockEvaluation, "date__of__foo").options().size()).isEqualTo(2);
        assertThat(new Column(mockEvaluation, "date__of__foo").options().get(ORIGINAL_NAME))
                .isEqualTo("date__of__foo");
        assertThat(new Column(mockEvaluation, "date__of__foo").options().get(OF))
                .isEqualTo("foo-transformed");

        assertThat(new Column(mockEvaluation, "something__on__bar").options().size()).isEqualTo(2);
        assertThat(new Column(mockEvaluation, "something__on__bar").options().get(ORIGINAL_NAME))
                .isEqualTo("something__on__bar");
        assertThat(new Column(mockEvaluation, "something__on__bar").options().get(ON))
                .isEqualTo("bar");

        new Verifications() {{
            mockAvailableColumns.has("bar");
            times = 0;
        }};
    }

    @Test
    public void it_throws_exception_if_key_for_options_is_not_defined(
            @Mocked Evaluation mockEvaluation,
            @Mocked AvailableColumns mockAvailableColumns) {

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                                                                                     new Column(mockEvaluation,
                                                                                                "date__unknown_keyword__foo"));
    }

    @Test
    public void it_has_fully_qualified_classname(@Mocked Evaluation mockEvaluation,
                                                 @Mocked Config mockConfig) {
        new Expectations() {{
            Config.evaluationColumnsPath();
            result = "Doc.Bar.Biz.";
        }};

        Column column = new Column(mockEvaluation, "baz");

        assertThat(column.fullyQualifiedClassName()).isEqualTo("Doc.Bar.Biz.Baz");
    }

    @Test
    public void it_has_options(@Mocked Evaluation mockEvaluation) {
        Column foo = new Column(mockEvaluation, "bar");

        assertThat(foo.options()).isEqualTo(ImmutableMap.of(ORIGINAL_NAME, "bar"));
    }

    @Test
    public void it_gets_instance_of_evaluation_column(@Mocked Evaluation mockEvaluation,
                                                      @Mocked Config mockConfig) {
        new Expectations() {{
            Config.evaluationColumnsPath();
            result = "de.dentareport.models.";
        }};

        Column column = new Column(mockEvaluation, "foo");

        assertThat(column.instance()).isInstanceOf(EvaluationColumn.class);
        assertThat(column.instance()).isInstanceOf(Foo.class);
    }

    @Test
    public void it_knows_if_it_has_to_be_grouped(@Mocked Evaluation mockEvaluation,
                                                 @Mocked AvailableColumns mockAvailableColumns) {

        new Expectations() {{
            mockAvailableColumns.has("bizbaz");
            result = true;
        }};

        Column column1 = new Column(mockEvaluation, "foo");
        assertThat(column1.isGroupable()).isFalse();

        Column column2 = new Column(mockEvaluation, "groups__of__bizbaz");
        assertThat(column2.isGroupable()).isTrue();
    }

    @Test
    public void it_evaluates_column(@Mocked Evaluation mockEvaluation,
                                    @Mocked Config mockConfig,
                                    @Mocked RawData mockRawData) {
        new Expectations() {{
            Config.evaluationColumnsPath();
            result = "de.dentareport.models.";
        }};

        CaseData caseData = new CaseData("42");
        Column column = new Column(mockEvaluation, "foo");

        caseData = column.evaluate(mockRawData, caseData);

        assertThat(caseData.string("foobar")).isEqualTo("bizbaz");
    }

    @Test
    public void it_gets_required_columns(@Mocked Evaluation mockEvaluation,
                                         @Mocked Config mockConfig,
                                         @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            Config.evaluationColumnsPath();
            result = "de.dentareport.models.";
        }};

        List<String> dependencies = new Column(mockEvaluation, "foo").dependencies();

        assertThat(dependencies.size()).isEqualTo(3);
        assertThat(dependencies).contains("bar");
        assertThat(dependencies).contains("biz");
        assertThat(dependencies).contains("baz");
    }

    @Test
    public void it_checks_if_column_has_to_be_translated(@Mocked Evaluation mockEvaluation,
                                                         @Mocked Config mockConfig,
                                                         @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            Config.evaluationColumnsPath();
            result = "de.dentareport.models.";
        }};

        assertThat(new Column(mockEvaluation, "foo").isTranslatable()).isTrue();
    }

    @Test
    public void it_gets_options(@Mocked Evaluation mockEvaluation) {
        Column column = new Column(mockEvaluation,
                                   "count_treatments__on__dentition__with__some_billingposition__from__date_start_observation");

        assertThat(column.option(ON)).isEqualTo("dentition");
        assertThat(column.option(WITH)).isEqualTo("some_billingposition");
        assertThat(column.option(FROM)).isEqualTo("date_start_observation");
    }

    @Test
    public void it_throws_exception_when_trying_to_access_option_that_doesnot_exist(
            @Mocked Evaluation mockEvaluation) {

        Column column = new Column(mockEvaluation,
                                   "count_treatments__on__dentition__with__some_billingposition__from__date_start_observation");

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> column.option("foobar")
        );
    }

    @Test
    public void it_checks_if_column_value_is_date_to_be_reformated(
            @Mocked Evaluation mockEvaluation,
            @Mocked Config mockConfig,
            @Mocked AvailableColumns mockAvailableColumns) {

        new Expectations() {{
            Config.evaluationColumnsPath();
            result = "de.dentareport.models.";
        }};

        assertThat(new Column(mockEvaluation, "foo").isDate()).isTrue();
    }

    @Test
    public void it_gets_hierarchy_level(@Mocked Evaluation mockEvaluation,
                                        @Mocked Config mockConfig,
                                        @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            Config.evaluationColumnsPath();
            result = "de.dentareport.testclasses.columns.";
        }};

        assertThat(new Column(mockEvaluation, "a").hierarchyLevel()).isEqualTo(0);
        assertThat(new Column(mockEvaluation, "b").hierarchyLevel()).isEqualTo(3);
        assertThat(new Column(mockEvaluation, "c").hierarchyLevel()).isEqualTo(2);
        assertThat(new Column(mockEvaluation, "d").hierarchyLevel()).isEqualTo(1);
        assertThat(new Column(mockEvaluation, "e").hierarchyLevel()).isEqualTo(1);
        assertThat(new Column(mockEvaluation, "f").hierarchyLevel()).isEqualTo(0);
    }

    @Test
    public void it_gets_documentation(@Mocked Evaluation mockEvaluation,
                                      @Mocked Config mockConfig) {
        new Expectations() {{
            Config.evaluationColumnsPath();
            result = "de.dentareport.models.";
        }};

        Column column = new Column(mockEvaluation, "foo");

        assertThat(column.documentation().size()).isEqualTo(2);
        assertThat(column.documentation().get(Keys.LANG_DE)).isEqualTo("Doc Short De");
        assertThat(column.documentation().get(Keys.LANG_DE + Keys.SUFFIX_LONG))
                .isEqualTo("Doc Long De");
    }
}

class Foo extends EvaluationColumn {
    public Foo(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setString("foobar", "bizbaz");
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Doc Short De";
    }

    @Override
    public String documentationLongDe() {
        return "Doc Long De";
    }

    @Override
    public boolean isTranslatable() {
        return true;
    }

    @Override
    public boolean isDate() {
        return true;
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of("bar", "biz", "baz");
    }
}