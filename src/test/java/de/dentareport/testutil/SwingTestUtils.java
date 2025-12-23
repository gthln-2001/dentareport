package de.dentareport.testutil;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public final class SwingTestUtils {

    private SwingTestUtils() {
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

    public static JButton findButtonByText(Container root, String text) {
        return findComponent(root, JButton.class, button ->
                text.equals(button.getText())
        ).orElseThrow(() ->
                new AssertionError("Button not found: " + text)
        );
    }

    public static JButton findButtonByName(Container root, String name) {
        return findComponent(root, JButton.class, button ->
                name.equals(button.getName())
        ).orElseThrow(() ->
                new AssertionError("Button not found with name: " + name)
        );
    }

    public static void clickButtonByText(Container root, String text) {
        findButtonByText(root, text).doClick();
    }

    public static void clickButtonByName(Container root, String name) {
        findButtonByName(root, name).doClick();
    }


    public static <T extends Component> Optional<T> findComponent(
            Container root,
            Class<T> type,
            java.util.function.Predicate<T> predicate
    ) {
        for (Component component : root.getComponents()) {
            if (type.isInstance(component)) {
                T casted = type.cast(component);
                if (predicate.test(casted)) {
                    return Optional.of(casted);
                }
            }
            if (component instanceof Container container) {
                Optional<T> child = findComponent(container, type, predicate);
                if (child.isPresent()) {
                    return child;
                }
            }
        }
        return Optional.empty();
    }
}
