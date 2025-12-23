package de.dentareport.gui;

import com.google.common.collect.ImmutableMap;
import de.dentareport.License;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.panes.ContentPaneFactory;
import de.dentareport.utils.db.DbConnection;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class GuiTest {

    private Gui gui;
    @Mocked
    ContentPaneFactory mockContentPaneFactory;
    @Mocked
    Stage mockStage;
    @Mocked
    License mockLicense;

    @BeforeEach
    public void setUp() throws Exception {
        this.gui = new Gui(mockStage);
    }

    // TODO: Fix test
//    @Test
//    public void it_initialises_gui(@Mocked Pane mockContentPane,
//                                   @Mocked BorderPane mockLayout,
//                                   @Mocked Scene mockScene) {
//        new Expectations() {{
//            mockContentPaneFactory.create(ImmutableMap.of("view", Keys.GUI_VIEW_START));
//            mockLayout.setCenter(mockContentPane);
//        }};
//
//        gui.start();
//
//        new Verifications() {{
//            mockLicense.isDemo();
//            mockStage.setTitle(anyString);
//            mockStage.setScene((Scene) any);
//            mockStage.show();
//        }};
//    }

    @Test
    public void it_closes_program_when_confirmed(@Mocked Element mockElement,
                                                 @Mocked DbConnection mockDbConnection) {
        new Expectations() {{
            Element.confirmBox().title(anyString).text(anyString).create();
            result = true;
        }};

        gui.closeProgram();

        new Verifications() {{
            DbConnection.db().close();
            mockStage.close();
        }};
    }

    @Test
    public void it_does_not_close_program_when_not_confirmed(@Mocked Element mockElement) {
        new Expectations() {{
            Element.confirmBox().title(anyString).text(anyString).create();
            result = false;
        }};

        gui.closeProgram();

        new Verifications() {{
            mockStage.close();
            times = 0;
        }};
    }

    @Test
    public void it_sets_view_when_called_with_map_of_options(@Mocked Pane mockContentPane,
                                                             @Mocked BorderPane mockLayout) {
        Map<String, String> options = ImmutableMap.of("view", "view_name");

        new Expectations() {{
            mockContentPaneFactory.create(options);
        }};

        gui.setContentPane(options);

        new Verifications() {{
            mockLayout.setCenter(mockContentPane);
        }};
    }

    @Test
    public void it_sets_view_when_called_with_string(@Mocked Pane mockContentPane,
                                                     @Mocked BorderPane mockLayout) {
        Map<String, String> options = ImmutableMap.of("view", "view_name");

        new Expectations() {{
            mockContentPaneFactory.create(options);
        }};

        gui.setContentPane("view_name");

        new Verifications() {{
            mockLayout.setCenter(mockContentPane);
        }};
    }
}