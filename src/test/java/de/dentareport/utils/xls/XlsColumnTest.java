package de.dentareport.utils.xls;

import com.google.common.collect.ImmutableSet;
import de.dentareport.Translate;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.models.Column;
import de.dentareport.utils.string.DateStringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class XlsColumnTest {

    @Test
    public void it_gets_name(@Mocked Column mockColumn) {
        new Expectations() {{
            mockColumn.name();
            result = "foobar";
        }};
        XlsColumn xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of());

        assertThat(xlsColumn.name()).isEqualTo("foobar");
    }

    @Test
    public void it_gets_index(@Mocked Column mockColumn,
                              @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockColumn.name();
            result = "bizbaz";

            mockAvailableColumns.index("bizbaz");
            times = 1;
            result = "some.index";
        }};

        XlsColumn xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of());

        assertThat(xlsColumn.index()).isEqualTo("some.index");
        assertThat(xlsColumn.index()).isEqualTo("some.index");
    }

    @Test
    public void it_gets_background_color(@Mocked Column mockColumn,
                                          @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockColumn.name();
            result = "bizbaz";
            mockAvailableColumns.index("bizbaz");
            result = "first.second";
        }};

        XlsColumn xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of());

        assertThat(xlsColumn.backgroundColor()).isEqualTo("first");
    }

    @Test
    public void it_checks_if_it_is_in_evaluation(@Mocked Column mockColumn,
                                                 @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockColumn.name();
            result = "bizbaz";
        }};

        XlsColumn xlsColumn;

        xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of());
        assertThat(xlsColumn.isInEvaluation()).isFalse();

        xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of("foo"));
        assertThat(xlsColumn.isInEvaluation()).isFalse();

        xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of("bizbaz"));
        assertThat(xlsColumn.isInEvaluation()).isTrue();

        xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of("foo", "bizbaz"));
        assertThat(xlsColumn.isInEvaluation()).isTrue();
    }

    @Test
    public void it_checks_if_it_is_hidden_in_documentation(@Mocked Column mockColumn1,
                                                           @Mocked Column mockColumn2,
                                                           @Mocked Column mockColumn3,
                                                           @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockColumn1.name();
            result = "foo";
            mockColumn2.name();
            result = "bar";
            mockColumn3.name();
            result = "biz";

            mockAvailableColumns.group("foo");
            result = "replaced_with_shortcut";

            mockAvailableColumns.group("bar");
            result = "events";

            mockAvailableColumns.group("biz");
            result = "something_else";
        }};

        XlsColumn xlsColumn;

        xlsColumn = new XlsColumn(mockColumn1, ImmutableSet.of());
        assertThat(xlsColumn.isHiddenInDocumentation()).isTrue();

        xlsColumn = new XlsColumn(mockColumn2, ImmutableSet.of());
        assertThat(xlsColumn.isHiddenInDocumentation()).isTrue();

        xlsColumn = new XlsColumn(mockColumn3, ImmutableSet.of());
        assertThat(xlsColumn.isHiddenInDocumentation()).isFalse();
    }

    @Test
    public void it_checks_if_it_is_visible_in_documentation(@Mocked Column mockColumn1,
                                                           @Mocked Column mockColumn2,
                                                           @Mocked Column mockColumn3,
                                                           @Mocked AvailableColumns mockAvailableColumns) {
        new Expectations() {{
            mockColumn1.name();
            result = "foo";
            mockColumn2.name();
            result = "bar";
            mockColumn3.name();
            result = "biz";

            mockAvailableColumns.group("foo");
            result = "replaced_with_shortcut";

            mockAvailableColumns.group("bar");
            result = "events";

            mockAvailableColumns.group("biz");
            result = "something_else";
        }};

        XlsColumn xlsColumn;

        xlsColumn = new XlsColumn(mockColumn1, ImmutableSet.of());
        assertThat(xlsColumn.isVisibleInDocumentation()).isFalse();

        xlsColumn = new XlsColumn(mockColumn2, ImmutableSet.of());
        assertThat(xlsColumn.isVisibleInDocumentation()).isFalse();

        xlsColumn = new XlsColumn(mockColumn3, ImmutableSet.of());
        assertThat(xlsColumn.isVisibleInDocumentation()).isTrue();
    }

    @Test
    public void it_gets_value_from_sql_resultset(@Mocked Column mockColumn,
                                                 @Mocked ResultSet mockResultSet,
                                                 @Mocked Translate mockTranslate) throws Exception {
        new Expectations() {{
            mockColumn.name();
            result = "foobar";

            mockColumn.isTranslatable();
            result = false;

            mockColumn.isDate();
            result = false;

            mockResultSet.getString("foobar");
            result = "bizbaz";

            mockTranslate.translate(anyString);
            times = 0;
        }};
        XlsColumn xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of());

        assertThat(xlsColumn.value(mockResultSet)).isEqualTo("bizbaz");
    }

    @Test
    public void it_translates_value_from_sql_resultset_if_necessary(@Mocked Column mockColumn,
                                                                    @Mocked ResultSet mockResultSet,
                                                                    @Mocked Translate mockTranslate) throws Exception {
        new Expectations() {{
            mockColumn.name();
            result = "foobar";

            mockColumn.isTranslatable();
            result = true;

            mockResultSet.getString("foobar");
            result = "bizbaz";

            mockTranslate.translate("bizbaz");
            result = "translated value";
        }};

        XlsColumn xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of());

        assertThat(xlsColumn.value(mockResultSet)).isEqualTo("translated value");
    }

    @Test
    public void it_reformats_date_value_from_sql_resultset_if_necessary(
            @Mocked Column mockColumn,
            @Mocked ResultSet mockResultSet,
            @Mocked Translate mockTranslate,
            @Mocked DateStringUtils mockDateStringUtils) throws Exception {

        new Expectations() {{
            mockColumn.name();
            result = "foobar";

            mockColumn.isTranslatable();
            result = false;

            mockColumn.isDate();
            result = true;

            mockResultSet.getString("foobar");
            result = "bizbaz";

            DateStringUtils.reformatSqlToGerman("bizbaz");
            result = "reformated date";

            mockTranslate.translate(anyString);
            times = 0;
        }};

        XlsColumn xlsColumn = new XlsColumn(mockColumn, ImmutableSet.of());

        assertThat(xlsColumn.value(mockResultSet)).isEqualTo("reformated date");
    }
}