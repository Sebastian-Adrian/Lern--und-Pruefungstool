package view;

import controller.UserController;
import model.Fach;

import java.awt.event.ActionListener;

public interface ControlPanel {

    void addFachButtonActionListener(ActionListener listener);

    void addFach(Fach fach, UserController userController);

    void addShowZuweisenActionListener(ActionListener listener);

    void reloadTabs();

    String getUsername();

}
