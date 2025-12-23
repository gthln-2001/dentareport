package de.dentareport;

import com.google.common.collect.ImmutableList;
import de.dentareport.models.Column;
import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentationTest {

    @Test
    public void it_prepares_documentation(@Mocked Column mockColumn1,
                                          @Mocked Column mockColumn2) {
        List<Column> columns = ImmutableList.of(mockColumn1, mockColumn2);

        new Expectations() {{
            mockColumn1.documentation();
            mockColumn2.documentation();
        }};

        new Documentation(columns).document();
    }

    @Test
    public void it_gets_short_documentation(@Mocked Column mockColumn) {
        List<Column> columns = ImmutableList.of(mockColumn);

        new Expectations() {{
            mockColumn.name();
            times = 1;
            result = "foo";
            mockColumn.documentation();
            result = columnDocumentation(Keys.LANG_DE, "Some Short Doc");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("foo")).isEqualTo("Some Short Doc");
    }

    @Test
    public void it_gets_long_documentation(@Mocked Column mockColumn) {
        List<Column> columns = ImmutableList.of(mockColumn);

        new Expectations() {{
            mockColumn.name();
            times = 1;
            result = "foo";
            mockColumn.documentation();
            result = columnDocumentation(Keys.LANG_DE + Keys.SUFFIX_LONG, "Some Long Doc");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.longDoc("foo")).isEqualTo("Some Long Doc");
    }

    @Test
    public void it_parses_short_documentation_for_column_without_parseable_text(@Mocked Column mockColumn) {
        List<Column> columns = ImmutableList.of(mockColumn);

        new Expectations() {{
            mockColumn.name();
            result = "foo";

            mockColumn.documentation();
            result = columnDocumentation(Keys.LANG_DE, "bar");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("foo")).isEqualTo("bar");
    }

    @Test
    public void it_parses_short_documentation_for_column_that_contains_name_of_other_column(
            @Mocked Column mockColumn1,
            @Mocked Column mockColumn2) {

        List<Column> columns = ImmutableList.of(mockColumn1, mockColumn2);

        new Expectations() {{
            mockColumn1.name();
            result = "c1";
            mockColumn1.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c1 doc");

            mockColumn2.name();
            result = "c2";
            mockColumn2.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c2 doc ##c1##");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("c2")).isEqualTo("c2 doc c1 doc");
    }

    @Test
    public void it_parses_short_documentation_recursively_for_columns_that_contains_name_of_other_columns(
            @Mocked Column mockColumn1,
            @Mocked Column mockColumn2,
            @Mocked Column mockColumn3) {

        List<Column> columns = ImmutableList.of(mockColumn1, mockColumn2, mockColumn3);

        new Expectations() {{
            mockColumn1.name();
            result = "c1";
            mockColumn1.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c1 doc");

            mockColumn2.name();
            result = "c2";
            mockColumn2.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c2 doc ##c1##");

            mockColumn3.name();
            result = "c3";
            mockColumn3.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c3 doc ##c2##");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("c3")).isEqualTo("c3 doc c2 doc c1 doc");
    }

    @Test
    public void it_parses_short_documentation_for_column_that_contains_name_of_multiple_other_columns(
            @Mocked Column mockColumn1,
            @Mocked Column mockColumn2,
            @Mocked Column mockColumn3) {

        List<Column> columns = ImmutableList.of(mockColumn1, mockColumn2, mockColumn3);

        new Expectations() {{
            mockColumn1.name();
            result = "c1";
            mockColumn1.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c1 doc");

            mockColumn2.name();
            result = "c2";
            mockColumn2.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c2 doc ##c1##");

            mockColumn3.name();
            result = "c3";
            mockColumn3.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c3 doc ##c1## ##c2##");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("c3")).isEqualTo("c3 doc c1 doc c2 doc c1 doc");
    }

    @Test
    public void it_parses_short_documentation_for_column_that_contains_name_of_non_existing_columns(
            @Mocked Column mockColumn1) {

        List<Column> columns = ImmutableList.of(mockColumn1);

        new Expectations() {{
            mockColumn1.name();
            result = "c1";
            mockColumn1.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c1 doc ##does_not_exist##");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("c1")).isEqualTo("c1 doc does_not_exist");
    }

    @Test
    public void it_parses_short_documentation_for_column_that_contains_name_of_billingposition(
            @Mocked Column mockColumn1,
            @Mocked Translate mockTranslate) {

        List<Column> columns = ImmutableList.of(mockColumn1);

        new Expectations() {{
            mockTranslate.translate("billingposition_1");
            result = "Billingposition 1";

            mockColumn1.name();
            result = "c1";
            mockColumn1.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c1 doc ~~billingposition_1~~");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("c1")).isEqualTo("c1 doc °°Billingposition 1°°");
    }

    @Test
    public void it_parses_short_documentation_for_column_that_contains_name_of_multiple_billingpositions(
            @Mocked Column mockColumn1,
            @Mocked Translate mockTranslate) {

        List<Column> columns = ImmutableList.of(mockColumn1);

        new Expectations() {{
            mockTranslate.translate("billingposition_1");
            result = "Billingposition 1";

            mockTranslate.translate("billingposition_2");
            result = "Billingposition 2";

            mockColumn1.name();
            result = "c1";
            mockColumn1.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c1 doc ~~billingposition_1~~ foo ~~billingposition_2~~ bar ~~billingposition_1~~");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("c1")).isEqualTo("c1 doc °°Billingposition 1°° foo °°Billingposition 2°° bar °°Billingposition 1°°");
    }

    @Test
    public void it_parses_short_documentation_for_column_that_contains_name_of_other_column_and_billingposition(
            @Mocked Column mockColumn1,
            @Mocked Column mockColumn2,
            @Mocked Translate mockTranslate) {

        List<Column> columns = ImmutableList.of(mockColumn1, mockColumn2);

        new Expectations() {{
            mockTranslate.translate("billingposition_1");
            result = "Billingposition 1";

            mockTranslate.translate("billingposition_2");
            result = "Billingposition 2";

            mockColumn1.name();
            result = "c1";
            mockColumn1.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c1 doc ~~billingposition_1~~");

            mockColumn2.name();
            result = "c2";
            mockColumn2.documentation();
            result = columnDocumentation(Keys.LANG_DE, "c2 doc ~~billingposition_2~~ ##c1##");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        assertThat(documentation.shortDoc("c2")).isEqualTo("c2 doc °°Billingposition 2°° c1 doc °°Billingposition 1°°");
    }

    @Test
    public void it_gets_list_of_occuring_billingpositions(@Mocked Column mockColumn1,
                                                          @Mocked Column mockColumn2,
                                                          @Mocked Column mockColumn3,
                                                          @Mocked Column mockColumn4) {

        List<Column> columns = ImmutableList.of(mockColumn1, mockColumn2, mockColumn3, mockColumn4);

        new Expectations() {{
            mockColumn1.documentation();
            result = columnDocumentation(Keys.LANG_DE, "~~billingposition_1~~");

            mockColumn2.documentation();
            result = columnDocumentation(Keys.LANG_DE, "");

            mockColumn3.documentation();
            result = columnDocumentation(Keys.LANG_DE, "~~billingposition_1~~");

            mockColumn4.documentation();
            result = columnDocumentation(Keys.LANG_DE, "~~billingposition_2~~");
        }};

        Documentation documentation = new Documentation(columns);
        documentation.document();

        Set<String> result = documentation.occuringBillingpositions();

        assertThat(result.size()).isEqualTo(2);

        assertThat(result).contains("billingposition_1");
        assertThat(result).contains("billingposition_2");
    }

    private Map<String, String> columnDocumentation(String key, String value) {
        Map<String, String> ret = new HashMap<>();
        ret.put(key, value);
        return ret;
    }
}