package de.dentareport.gui.panes;

import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;

import java.util.Map;

public class EvaluationFillingProbabilitiesDisplay extends ContentPaneEvaluationProbabilitiesDisplay {

    public EvaluationFillingProbabilitiesDisplay(Gui gui, Map<String, String> options) {
        super(gui, options);
    }

    protected String parentPane() {
        return Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES;
    }
}
