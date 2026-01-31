package de.dentareport.gui.elements;

import de.dentareport.utils.Keys;

// TODO: TEST?
public class Element {

    public static ButtonElement button() {
        return new ButtonElement();
    }

    public static ConfirmBoxElement confirmBox() {
        return new ConfirmBoxElement();
    }

    public static InfoBoxElement infoBox() {
        return new InfoBoxElement();
    }

    public static void notAvailableInDemoVersion() {
        infoBox()
                .title(Keys.GUI_TEXT_DENTAREPORT)
                .text(Keys.GUI_TEXT_FUNCTION_NOT_AVAILABLE_IN_DEMO_VERSION)
                .create();
    }

    public static LabelElement label() {
        return new LabelElement();
    }

    public static LineChartElement lineChart() {
        return new LineChartElement();
    }

    public static MenuElement menu() {
        return new MenuElement();
    }

    public static RadioButtonElement radioButton() {
        return new RadioButtonElement();
    }

    public static TableElement table() {
        return new TableElement();
    }
}
