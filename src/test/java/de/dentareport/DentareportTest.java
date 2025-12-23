package de.dentareport;

//import de.dentareport.gui.Gui;
import de.dentareport.utils.PreStarter;
//import javafx.stage.Stage;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DentareportTest {

    private Dentareport dentareport;

    @BeforeEach
    public void setUp() {
        this.dentareport = new Dentareport();
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