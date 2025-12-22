package de.dentareport;

public interface MainWindow {

    String getWindowTitle();

    String getMessageText();

    void setMessageText(String text);

    boolean containsMessage();

    void clickButton();
}
