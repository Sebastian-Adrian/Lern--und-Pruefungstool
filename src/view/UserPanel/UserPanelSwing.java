package view.UserPanel;

import controller.UserController;
import model.Fach;
import view.ControlPanel;
import view.FachPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserPanelSwing extends JFrame implements ControlPanel {

    private JTabbedPane tabbedPaneFaecher;
    private JPanel UserControlMainPanel;
    private JPanel themenPanel;
    private JButton logoutButton;
    private String username;

    public UserPanelSwing(String title, String username) {
        super(title);
        this.username = username;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(UserControlMainPanel);
        Dimension frameSize = new Dimension(800, 600);
        this.setPreferredSize(frameSize);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void addFach(Fach fach, UserController controller) {
        FachPanel fachPanel = new FachPanel(fach, false, controller);
        tabbedPaneFaecher.addTab(fach.getFachname(), fachPanel);
    }

    @Override
    public void addFachButtonActionListener(ActionListener listener) {
    }

    @Override
    public void addShowZuweisenActionListener(ActionListener listener) {
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void reloadTabs() {
        tabbedPaneFaecher.removeAll();
    }
}
