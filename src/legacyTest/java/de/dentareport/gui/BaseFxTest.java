package de.dentareport.gui;

import com.sun.javafx.application.PlatformImpl;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

public abstract class BaseFxTest extends ApplicationTest {

    @BeforeAll
    public static void setUpSpec() throws TimeoutException {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        if (!PlatformImpl.isFxApplicationThread()) {
            FxToolkit.registerPrimaryStage();
        }
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    protected abstract Pane pane();

    protected String backButtonTarget() {
        return null;
    }

    protected Boolean shouldHaveQuitButton() {
        return false;
    }

    protected Gui gui() {
        return null;
    }

    protected <T extends Node> T find(final String query) {
        return lookup(query).query();
    }
}
