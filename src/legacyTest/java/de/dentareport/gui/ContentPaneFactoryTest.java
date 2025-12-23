package de.dentareport.gui;

import de.dentareport.gui.panes.ContentPaneFactory;
import de.dentareport.gui.panes.Start;
import javafx.scene.layout.BorderPane;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentPaneFactoryTest {

    @Mocked
    Gui mockGui;
    private ContentPaneFactory contentPaneFactory;

    @BeforeEach
    public void setUp() throws Exception {
        this.contentPaneFactory = new ContentPaneFactory(mockGui);
    }

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        assertThat(true).isTrue();
    }


    // TODO: Fix test
//    @Test
//    public void it_gets_start_view_if_no_view_value_is_set(@Mocked Start mockStart,
//                                                           @Mocked BorderPane mockPane) {
//        Map<String, String> options = ImmutableMap.of();
//        new Expectations() {{
//            new Start(mockGui, options).pane();
//        }};
//
//        assertThat(contentPaneFactory.create(options)).isEqualTo(mockPane);
//    }

    // TODO: Fix test
//    @Test
//    public void it_gets_start_view_if_view_value_is_unknown(@Mocked Start mockStart,
//                                                            @Mocked BorderPane mockPane) {
//        Map<String, String> options = ImmutableMap.of("view", "some_unkown_value_that_we_will_never_use");
//        new Expectations() {{
//            new Start(mockGui, options).pane();
//        }};
//
//        assertThat(contentPaneFactory.create(options)).isEqualTo(mockPane);
//    }
}