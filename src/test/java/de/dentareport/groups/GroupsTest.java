package de.dentareport.groups;

import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

import java.util.Set;

// TODO: TEST?
public class GroupsTest {

    @Test
    public void it_groups_data(@Mocked Evaluation mockEvaluation,
                               @Mocked AvailableColumns mockAvailableColumns,
                               @Mocked ColumnGroups mockColumnGroups) {

        new Expectations() {{
            mockAvailableColumns.has("value_foo");
            result = true;
            mockAvailableColumns.has("bar");
            result = false;
            mockAvailableColumns.relatedColumn("bar");
            result = "value_bar";
            mockEvaluation.requiredColumns();
            result = testColumns();
            mockEvaluation.dbTable();
            result = "test_table";
        }};

        Groups groups = new Groups(mockEvaluation);
        groups.group();

        new Verifications() {{
            new ColumnGroups();
            times = 1;
            mockColumnGroups.group("test_table", "value_foo", "groups__of__value_foo");
            times = 1;
            mockColumnGroups.group("test_table", "value_bar", "groups__of__bar");
            times = 1;
        }};
    }

    private Set<String> testColumns() {
        return ImmutableSet.of(
                "groups__of__value_foo",
                "groups__of__bar",
                "biz"
        );
    }
}