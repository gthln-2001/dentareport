package de.dentareport.evaluations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.dentareport.utils.ColumnsService;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class AvailableColumnsTest {

    private AvailableColumns availableColumns;

    @BeforeEach
    public void setUp() {
        this.availableColumns = new AvailableColumns();
    }

    @Test
    public void it_checks_if_column_is_available() {
        assertThat(availableColumns.has("patient_index")).isTrue();
        assertThat(availableColumns.has("tooth")).isTrue();
        assertThat(availableColumns.has("some_column_that_definitely_does_not_exit")).isFalse();
    }

    @Test
    public void for_each_available_column_exists_an_evaluation_column_class(@Mocked Evaluation mockEvaluation) {
        // This test makes sure we have an evaluation column class for every column that is
        // listed as available.
        // We check this by simulating an evaluation which uses all available columns
        // and try to prepare the column list for these columns.
        // The prepare method has to get an instance for every column to check its dependency.
        // So this will throw an exception if a class for a column is missing.
        ColumnsService columnsService = new ColumnsService(mockEvaluation);
        columnsService.prepare(availableColumns.columns());
    }

    @Test
    public void each_column_is_unique() {
        int countMap = availableColumns.columnGroups().values().stream().mapToInt(List::size).sum();
        int countFlat = availableColumns.columns().size();

        assertThat(countMap).isEqualTo(countFlat);
    }

    @Test
    public void it_gets_related_columns() {
        new MockUp<AvailableColumns>() {
            @Mock
            Set<String> columns() {
                return ImmutableSet.of(
                        "foo",
                        "foo_bar",
                        "foo_bar__biz___baz"
                );
            }
        };

        assertThat(availableColumns.relatedColumn("foo")).isEqualTo("foo");
        assertThat(availableColumns.relatedColumn("foo_bar")).isEqualTo("foo_bar");
        assertThat(availableColumns.relatedColumn("foo_bar_biz_baz")).isEqualTo("foo_bar__biz___baz");
    }

    @Test
    public void it_throws_exception_when_related_column_produces_no_result() {
        new MockUp<AvailableColumns>() {
            @Mock
            Set<String> columns() {
                return ImmutableSet.of(
                        "foo"
                );
            }
        };

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                availableColumns.relatedColumn("bar"));
    }

    @Test
    public void it_throws_exception_when_related_column_produces_more_than_one_result() {
        new MockUp<AvailableColumns>() {
            @Mock
            Set<String> columns() {
                return ImmutableSet.of(
                        "foo_bar",
                        "foo____bar"
                );
            }
        };

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                availableColumns.relatedColumn("foo__bar"));
    }

    @Test
    public void it_gets_column_index() {
        new MockUp<AvailableColumns>() {
            @Mock
            Map<String, List<String>> columnGroups() {
                return ImmutableMap.of(
                        "level_1", ImmutableList.of(
                                "column_1",
                                "column_2"
                        ),
                        "level_2", ImmutableList.of(
                                "column_3",
                                "column_4"
                        )
                );
            }
        };

        AvailableColumns availableColumns = new AvailableColumns();

        assertThat(availableColumns.index("column_1")).isEqualTo("1.1");
        assertThat(availableColumns.index("column_2")).isEqualTo("1.2");
        assertThat(availableColumns.index("column_3")).isEqualTo("2.1");
        assertThat(availableColumns.index("column_4")).isEqualTo("2.2");
    }

    @Test
    public void it_throws_exception_when_trying_to_get_index_for_column_that_does_not_exist() {
        new MockUp<AvailableColumns>() {
            @Mock
            Map<String, List<String>> columnGroups() {
                return ImmutableMap.of(
                        "level_1", ImmutableList.of(
                                "column_1"
                        )
                );
            }
        };

        AvailableColumns availableColumns = new AvailableColumns();
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                availableColumns.index("foobar"));
    }

    @Test
    public void it_gets_group_name_for_column() {
        new MockUp<AvailableColumns>() {
            @Mock
            Map<String, List<String>> columnGroups() {
                return ImmutableMap.of(
                        "level_1", ImmutableList.of(
                                "column_1",
                                "column_2"
                        ),
                        "level_2", ImmutableList.of(
                                "column_3",
                                "column_4"
                        )
                );
            }
        };


        AvailableColumns availableColumns = new AvailableColumns();

        assertThat(availableColumns.group("column_1")).isEqualTo("level_1");
        assertThat(availableColumns.group("column_2")).isEqualTo("level_1");
        assertThat(availableColumns.group("column_3")).isEqualTo("level_2");
        assertThat(availableColumns.group("column_4")).isEqualTo("level_2");
    }

    @Test
    public void it_throws_exception_when_trying_to_get_group_name_for_column_that_does_not_exist() {
        new MockUp<AvailableColumns>() {
            @Mock
            Map<String, List<String>> columnGroups() {
                return ImmutableMap.of(
                        "level_1", ImmutableList.of(
                                "column_1"
                        )
                );
            }
        };

        AvailableColumns availableColumns = new AvailableColumns();
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                availableColumns.group("foobar"));
    }
}