package de.dentareport;

public interface MainWindow {

    String getWindowTitle();

    String getMessageText();

    void setMessageText(String text);

    boolean containsMessage();

    boolean isVisible();

    void clickButton();
}
