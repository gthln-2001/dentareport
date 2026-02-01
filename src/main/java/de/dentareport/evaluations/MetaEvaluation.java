package de.dentareport.evaluations;

import de.dentareport.evaluations.meta.annual_failure_rates.AnnualFailureRate;
import de.dentareport.evaluations.meta.averages.Averages;
import de.dentareport.evaluations.meta.counts.Counts;
import de.dentareport.evaluations.meta.distributions.Distributions;
import de.dentareport.evaluations.meta.kaplan_meier.KaplanMeier;


// TODO: TEST?
public class MetaEvaluation {

    private final Averages averages;
    private final Counts counts;
    private final Distributions distributions;
    private final KaplanMeier kaplanMeier;
    private final AnnualFailureRate annualFailureRate;

    public MetaEvaluation(Evaluation evaluation) {
        this.averages = new Averages(evaluation);
        this.counts = new Counts(evaluation);
        this.distributions = new Distributions(evaluation);
        this.kaplanMeier = new KaplanMeier(evaluation);
        this.annualFailureRate = new AnnualFailureRate(evaluation);
    }

    public void evaluate() {
        averages.evaluate();
        counts.evaluate();
        distributions.evaluate();
        kaplanMeier.evaluate();
        annualFailureRate.evaluate();
    }
}