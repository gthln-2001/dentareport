package de.dentareport.utils;

import com.google.common.collect.ImmutableSet;
import de.dentareport.Config;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.Column;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ColumnsServiceTest {

    private ColumnsService columnsService;

    @Mocked
    Evaluation mockEvaluation;

    @BeforeEach
    public void setup() {
        this.columnsService = new ColumnsService(mockEvaluation);
    }

    @Test
    public void it_gets_hierarchy_of_dependencies(@Mocked AvailableColumns mockAvailableColumns,
                                                  @Mocked Config mockConfig) {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            Config.evaluationColumnsPath();
            result = "de.dentareport.testclasses.columns.";
        }};

        List<Column> hierarchy = columnsService.prepare(new HashSet<>(Arrays.asList("a", "b", "e")));

        assertThat(hierarchy.size()).isEqualTo(6);
        assertThat(hierarchy.get(0).name()).isEqualTo("a");
        assertThat(hierarchy.get(1).name()).isEqualTo("f");
        assertThat(hierarchy.get(2).name()).isEqualTo("e");
        assertThat(hierarchy.get(3).name()).isEqualTo("d");
        assertThat(hierarchy.get(4).name()).isEqualTo("c");
        assertThat(hierarchy.get(5).name()).isEqualTo("b");
    }

    @Test
    public void it_gets_required_billing_codes(@Mocked AvailableColumns mockAvailableColumns,
                                               @Mocked Config mockConfig,
                                               @Mocked Billingcodes mockBillingCodes) {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            Config.evaluationColumnsPath();
            result = "de.dentareport.testclasses.columns.";
            Billingcodes.forPosition(ImmutableSet.of("foo", "bar"));
            result = ImmutableSet.of("1", "2", "3", "4");
        }};

        List<Column> columnList = columnsService.prepare(new HashSet<>(Arrays.asList("a", "b", "e")));

        Set<String> billingCodes = columnsService.requiredBillingCodes(mockEvaluation, columnList);

        assertThat(billingCodes.size()).isEqualTo(4);
        assertThat(billingCodes).contains("1");
        assertThat(billingCodes).contains("2");
        assertThat(billingCodes).contains("3");
        assertThat(billingCodes).contains("4");
    }

    @Test
    public void it_gets_required_evidence_types(@Mocked AvailableColumns mockAvailableColumns,
                                                @Mocked Config mockConfig) {
        new Expectations() {{
            mockAvailableColumns.has(anyString);
            result = true;
            Config.evaluationColumnsPath();
            result = "de.dentareport.testclasses.columns.";
        }};

        List<Column> columnList = columnsService.prepare(new HashSet<>(Arrays.asList("a", "b", "e")));

        Set<String> billingCodes = columnsService.requiredEvidenceTypes(mockEvaluation, columnList);

        assertThat(billingCodes.size()).isEqualTo(3);
        assertThat(billingCodes).contains(Keys.EVIDENCE_TYPE_01);
        assertThat(billingCodes).contains("H");
        assertThat(billingCodes).contains("X");
    }
}