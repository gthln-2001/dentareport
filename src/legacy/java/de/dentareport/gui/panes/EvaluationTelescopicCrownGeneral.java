package de.dentareport.gui.panes;

import de.dentareport.Metadata;
import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;
import de.dentareport.utils.date.DateRange;

import java.util.Map;

public class EvaluationTelescopicCrownGeneral extends ContentPaneEvaluationGeneral {

    private final String evaluationId;

    public EvaluationTelescopicCrownGeneral(Gui gui, Map<String, String> options) {
        super(gui, options);
        evaluationId = options.get("evaluationId");
    }

    @Override
    protected String centerLabel() {
        DateRange evaluationPeriod = Metadata.evaluationPeriod(evaluationId);
        return String.format(Keys.GUI_TEXT_GENERAL_INFORMATION_TELESCOPIC_CROWNS,
                evaluationPeriod.formattedStart(),
                evaluationPeriod.formattedEnd()
        );
    }

    @Override
    protected String parentPane() {
        return Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN;
    }
}
