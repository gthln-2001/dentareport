package de.dentareport.gui.panes;

import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;

import java.util.Map;

// TODO: TEST?
public class EvaluationTelescopicCrownProbabilitiesDisplay extends ContentPaneEvaluationProbabilitiesDisplay {

    public EvaluationTelescopicCrownProbabilitiesDisplay(Gui gui, Map<String, String> options) {
        super(gui, options);
    }

    @Override
    protected String parentPane() {
        return Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_PROBABILITIES;
    }
}
