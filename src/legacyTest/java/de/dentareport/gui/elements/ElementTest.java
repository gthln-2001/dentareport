package de.dentareport.gui.elements;

import com.sun.javafx.application.PlatformImpl;
import de.dentareport.utils.Keys;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementTest {

    @BeforeEach
    public void setUp() throws Exception {
        // initialize JavaFx Toolkit
        PlatformImpl.startup(() -> {
        });
    }

    @Test
    public void it_gets_button_element() {
        assertThat(Element.button()).isInstanceOf(ButtonElement.class);
    }

    @Test
    public void it_gets_confirm_box_element() {
        assertThat(Element.confirmBox()).isInstanceOf(ConfirmBoxElement.class);
    }

    @Test
    public void it_gets_info_box_element() {
        assertThat(Element.infoBox()).isInstanceOf(InfoBoxElement.class);
    }

    @Test
    public void it_gets_label_element() {
        assertThat(Element.label()).isInstanceOf(LabelElement.class);
    }

    @Test
    public void it_gets_line_chart_element() {
        assertThat(Element.lineChart()).isInstanceOf(LineChartElement.class);
    }

    @Test
    public void it_gets_menu_element() {
        assertThat(Element.menu()).isInstanceOf(MenuElement.class);
    }

    @Test
    public void it_gets_radio_button_element() {
        assertThat(Element.radioButton()).isInstanceOf(RadioButtonElement.class);
    }

    @Test
    public void it_gets_table_element() {
        assertThat(Element.table()).isInstanceOf(TableElement.class);
    }

    @Test
    public void it_shows_info_box_for_function_that_is_not_available_in_demo_version(@Mocked InfoBoxElement mockInfoBox) {
        Element.notAvailableInDemoVersion();

        new Verifications() {{
            mockInfoBox.title(Keys.GUI_TEXT_DENTAREPORT)
                    .text(Keys.GUI_TEXT_FUNCTION_NOT_AVAILABLE_IN_DEMO_VERSION)
                    .create();
            times = 1;
        }};
    }
}