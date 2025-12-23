package de.dentareport;

//import de.dentareport.gui.Gui;
//import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;


public class DentareportTest {

    private Dentareport_TO_REMOVE dentareport;

    @BeforeEach
    public void setUp() {
        this.dentareport = new Dentareport_TO_REMOVE();
    }

//    @Test
//    public void it_starts_application(@Mocked Stage mockStage,
//                                      @Mocked PreStarter mockPreStarter,
//                                      @Mocked Gui mockGui) {
//        dentareport.start(mockStage);
//
//        new Verifications() {{
//            mockPreStarter.runPreStartTasks();
//            times = 1;
//            new Gui(mockStage);
//            times = 1;
//            mockGui.start();
//            times = 1;
//        }};
//    }
}