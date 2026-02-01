package de.dentareport.evaluations;

import de.dentareport.evaluations.meta.annual_failure_rates.AnnualFailureRate;
import de.dentareport.evaluations.meta.averages.Averages;
import de.dentareport.evaluations.meta.counts.Counts;
import de.dentareport.evaluations.meta.distributions.Distributions;
import de.dentareport.evaluations.meta.kaplan_meier.KaplanMeier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// TODO: TEST?
public class MetaEvaluation {

    private static final Logger log = LogManager.getLogger(MetaEvaluation.class);

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
        log.debug("Start MetaEvaluation");
        log.debug("Averages");
        averages.evaluate();
        log.debug("Counts");
        counts.evaluate();
        log.debug("Distributions");
        distributions.evaluate();
        log.debug("Kaplan Meier");
        kaplanMeier.evaluate();
        log.debug("AFR");
        annualFailureRate.evaluate();
        log.debug("Done");
    }
}