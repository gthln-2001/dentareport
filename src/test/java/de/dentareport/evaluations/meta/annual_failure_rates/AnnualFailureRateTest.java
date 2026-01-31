package de.dentareport.evaluations.meta.annual_failure_rates;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.meta.dependencies.AvailableDependencies;
import de.dentareport.evaluations.meta.events.AvailableEvents;
import de.dentareport.evaluations.meta.dependencies.Dependency;
import de.dentareport.evaluations.meta.events.Event;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.Db;
import de.dentareport.utils.db.DbConnection;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class AnnualFailureRateTest {

    private Db db;
    private AnnualFailureRate annualFailureRate;
    @Mocked
    DbConnection mockDbConnection;
    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    AvailableDependencies mockAvailableDependencies;
    @Mocked
    AvailableEvents mockAvailableEvents;
    @Mocked
    PerItemCombination mockPerItemCombination;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;

            mockEvaluation.dbTable();
            result = "some_table";

            mockEvaluation.evaluationType();
            result = "evaluation_type";

            new AvailableDependencies("evaluation_type");
            new AvailableEvents("evaluation_type");
        }};

        annualFailureRate = new AnnualFailureRate(mockEvaluation);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_rebuilds_table_for_afr_data() {
        annualFailureRate.evaluate();

        assertThat(db.hasTable("some_table" + Keys.DB_TABLE_SUFFIX_AFR)).isTrue();
    }

//    // TODO: Fix test
//    @Test
//    public void it_calls_afr_calculator_for_each_possible_item_combinations(
//            @Mocked Event mockEvent1,
//            @Mocked Event mockEvent2,
//            @Mocked Dependency mockDependency1,
//            @Mocked Dependency mockDependency2) {
//
//        new Expectations() {{
//            mockAvailableEvents.events();
//            result = ImmutableList.of(
//                    mockEvent1,
//                    mockEvent2
//            );
//            mockAvailableDependencies.dependencies();
//            result = ImmutableList.of(
//                    mockDependency1,
//                    mockDependency2
//            );
//        }};
//
//        annualFailureRate.evaluate();
//
//        new Verifications() {{
//            mockPerItemCombination.evaluate("some_table", mockEvent1, mockDependency1);
//            times = 1;
//            mockPerItemCombination.evaluate("some_table", mockEvent1, mockDependency2);
//            times = 1;
//            mockPerItemCombination.evaluate("some_table", mockEvent2, mockDependency1);
//            times = 1;
//            mockPerItemCombination.evaluate("some_table", mockEvent2, mockDependency2);
//            times = 1;
//        }};
//    }
}