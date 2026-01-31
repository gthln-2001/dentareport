package de.dentareport.gui;

import de.dentareport.gui.panes.ContentPane;
import de.dentareport.gui.tasks.BaseTask;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// TODO: TEST?
public class ProgressUpdateTest {

    @Mocked
    BaseTask mockBaseTask;
    @Mocked
    ContentPane mockContenPane;

    @BeforeEach
    public void setUp() {
        ProgressUpdate.setTask(mockBaseTask);
    }

    @Test
    public void it_updates_progress() {
        ProgressUpdate.tick();

        new Verifications() {{
            mockBaseTask.updateProgress(1, 1);
            times = 1;
            mockBaseTask.updateMessage("\n100,00%");
            times = 1;
        }};
    }

    @Test
    public void it_initializes_total() {
        ProgressUpdate.init(2);
        ProgressUpdate.tick();
        ProgressUpdate.tick();

        new Verifications() {{
            mockBaseTask.updateProgress(1, 2);
            times = 1;
            mockBaseTask.updateMessage("\n50,00%");
            times = 1;
            mockBaseTask.updateProgress(2, 2);
            times = 1;
            mockBaseTask.updateMessage("\n100,00%");
            times = 1;
        }};
    }

    @Test
    public void it_initializes_text() {
        ProgressUpdate.init(1, "some text");
        ProgressUpdate.tick();

        new Verifications() {{
            mockBaseTask.updateMessage("some text\n100,00%");
            times = 1;
        }};
    }

    @Test
    public void it_sets_text_prefix() {
        ProgressUpdate.init(1, "some text");
        ProgressUpdate.setTextPrefix("some prefix");
        ProgressUpdate.tick();

        new Verifications() {{
            mockBaseTask.updateMessage("some prefix\nsome text\n100,00%");
            times = 1;
        }};
    }

    @Test
    public void it_updates_message_on_task() {
        ProgressUpdate.updateMessage("foo message");

        new Verifications() {{
            mockBaseTask.updateMessage("foo message");
        }};

    }

    @Test
    public void it_calls_finished_on_content_pane() {
        ProgressUpdate.setGui(mockContenPane);
        ProgressUpdate.finished();

        new Verifications() {{
            mockContenPane.finished();
        }};
    }
}