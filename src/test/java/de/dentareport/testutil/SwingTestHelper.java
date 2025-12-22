package de.dentareport.testutil;

import javax.swing.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

// TODO: MAybe remove later, this is just an example. Or re-use it.
public final class SwingTestHelper {

    private SwingTestHelper() {
    }

    public static void runOnEdt(Runnable task) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            SwingUtilities.invokeAndWait(task);
        }
    }

    public static <T> T callOnEdt(Callable<T> task) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            return task.call();
        }

        FutureTask<T> future = new FutureTask<>(task);
        SwingUtilities.invokeAndWait(future);
        return future.get();
    }
}
