package de.dentareport.utils.xls;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.models.Column;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class XlsColumnComparerByIndexTest {

    @Test
    public void it_compares_xls_columns_by_index_in_natural_order(
            @Mocked Column mockColumn1,
            @Mocked Column mockColumn2,
            @Mocked AvailableColumns mockAvailablecolumns) {

        XlsColumnComparerByIndex comparer = new XlsColumnComparerByIndex();

        Map<List<String>, Integer> provider = ImmutableMap.<List<String>, Integer>builder()
                .put(ImmutableList.of("1.1", "1.1"), 0)
                .put(ImmutableList.of("0.0", "0.0"), 0)
                .put(ImmutableList.of("17.52", "17.52"), 0)
                .put(ImmutableList.of("1.1", "1.2"), -1)
                .put(ImmutableList.of("1.1", "2.1"), -1)
                .put(ImmutableList.of("1.1", "1.10"), -1)
                .put(ImmutableList.of("1.7", "2.1"), -1)
                .put(ImmutableList.of("3.285", "7.56"), -1)
                .put(ImmutableList.of("1.2", "1.1"), 1)
                .put(ImmutableList.of("2.1", "1.1"), 1)
                .put(ImmutableList.of("1.10", "1.1"), 1)
                .put(ImmutableList.of("7.56", "3.285"), 1)
                .build();


        for (List<String> values : provider.keySet()) {
            new Expectations() {{
                mockColumn1.name();
                result = "foo";
                mockColumn2.name();
                result = "bar";
                mockAvailablecolumns.index("foo");
                result = values.get(0);
                mockAvailablecolumns.index("bar");
                result = values.get(1);
            }};

            XlsColumn foo = new XlsColumn(mockColumn1, ImmutableSet.of());
            XlsColumn bar = new XlsColumn(mockColumn2, ImmutableSet.of());

            assertThat(comparer.compare(foo, bar)).isEqualTo(provider.get(values));
        }
    }
}