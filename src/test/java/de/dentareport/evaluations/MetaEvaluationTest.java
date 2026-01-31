package de.dentareport.evaluations;

import de.dentareport.evaluations.meta.annual_failure_rates.AnnualFailureRate;
import de.dentareport.evaluations.meta.averages.Averages;
import de.dentareport.evaluations.meta.counts.Counts;
import de.dentareport.evaluations.meta.distributions.Distributions;
import de.dentareport.evaluations.meta.kaplan_meier.KaplanMeier;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TODO: TEST?
public class MetaEvaluationTest {

    private MetaEvaluation metaEvaluation;
    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    Averages mockAverages;
    @Mocked
    Counts mockCounts;
    @Mocked
    Distributions mockDistributions;
    @Mocked
    KaplanMeier mockKaplanMeier;
    @Mocked
    AnnualFailureRate mockAnnualFailureRate;

    @BeforeEach
    public void setUp() {
        metaEvaluation = new MetaEvaluation(mockEvaluation);
    }

    @Test
    public void it_evaluates_meta_evaluations() {
        metaEvaluation.evaluate();

        new Verifications() {{
            mockAverages.evaluate();
            mockCounts.evaluate();
            mockDistributions.evaluate();
            mockKaplanMeier.evaluate();
            mockAnnualFailureRate.evaluate();
        }};
    }
}