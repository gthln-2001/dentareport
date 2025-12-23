//package de.dentareport.gui;
//
//import com.google.common.collect.ImmutableMap;
//import de.dentareport.License;
//import de.dentareport.gui.elements.Element;
//import de.dentareport.gui.panes.ContentPaneFactory;
//import de.dentareport.utils.Keys;
//import de.dentareport.utils.db.DbConnection;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//
//import java.util.Map;
//

// TODO: Check if we have re-implemented everything and remove afterwards

//public class Gui {
//
//    private final Stage primaryStage;
//    private BorderPane mainLayout = new BorderPane();
//    private final ContentPaneFactory contentPaneFactory;
//    private final License license;
//
//    public Gui(Stage primaryStage) {
//        this.license = new License();
//        this.primaryStage = primaryStage;
//        this.contentPaneFactory = new ContentPaneFactory(this);
//        prepareCloseRequest();
//    }
//
//    public void start() {
//        setContentPane(Keys.GUI_VIEW_START);
//        Scene scene = new Scene(mainLayout, 1024, 768);
//        scene.getStylesheets().add("styles.css");
//        setWindowtitle();
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public void closeProgram() {
//        if (Element.confirmBox()
//                .title(Keys.GUI_WINDOW_TITLE_CONFIRM_EXIT)
//                .text(Keys.GUI_TEXT_CONFIRM_EXIT)
//                .create()) {
//            DbConnection.db().close();
//            primaryStage.close();
//        }
//    }
//
//    public Stage primaryStage() {
//        return primaryStage;
//    }
//
//    public void setContentPane(String view) {
//        setContentPane(ImmutableMap.of("view", view));
//    }
//
//    public void setContentPane(Map<String, String> options) {
//        mainLayout.setCenter(contentPaneFactory.create(options));
//    }
//
//    private void setWindowtitle() {
//        primaryStage.setTitle(defaultWindowtitle());
//    }
//
//    private String defaultWindowtitle() {
//        if (license.isDemo()) {
//            return String.format("%s - %s", Keys.GUI_WINDOW_TITLE_MAIN, Keys.GUI_TEXT_DEMO_VERSION);
//        }
//        return Keys.GUI_WINDOW_TITLE_MAIN;
//    }
//
//    private void prepareCloseRequest() {
//        primaryStage.setOnCloseRequest(e -> {
//            e.consume();
//            closeProgram();
//        });
//    }
//}