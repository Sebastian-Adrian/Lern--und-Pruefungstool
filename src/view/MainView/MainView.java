package view.MainView;

import java.awt.event.ActionListener;

public interface MainView {

    void showInfoMessage(String message);

    void showErrorMessage(String message);

    void addLoginHandler(ActionListener actionListener);

    void closeWindow();

    String getUserName();

    char[] getPassword();

    void hideWindow();

    void showWindow();
}
