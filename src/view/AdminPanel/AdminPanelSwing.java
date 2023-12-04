package view.AdminPanel;

import controller.UserController;
import model.Fach;
import view.ControlPanel;
import view.FachPanel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminPanelSwing extends JFrame implements ControlPanel {

    private JPanel AdminControlMainPanel;
    private JTabbedPane tabbedPaneFaecher;
    private JPanel themenPanel;
    private JButton addFachButton;
    private JToolBar toolBar;
    private JButton showThemenZuweisenButton;
    private DefaultTreeModel benutzerThemenModel;

    public AdminPanelSwing(String username) throws HeadlessException {

        super(username + " - Administration");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(AdminControlMainPanel);
        Dimension frameSize = new Dimension(800, 600);
        this.setPreferredSize(frameSize);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void addFach(Fach fach, UserController controller) {
        FachPanel fachPanel = new FachPanel(fach, true, controller);
        tabbedPaneFaecher.addTab(fach.getFachname(),fachPanel);
    }

    @Override
    public void addShowZuweisenActionListener(ActionListener listener) {
        showThemenZuweisenButton.addActionListener(listener);
    }

    @Override
    public void reloadTabs() {
        tabbedPaneFaecher.removeAll();
    }

    @Override
    public String getUsername() {
        return null;
    }

    public void addFachButtonActionListener(ActionListener listener) {
        addFachButton.addActionListener(listener);
    }

}

