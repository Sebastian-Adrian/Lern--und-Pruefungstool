package view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MainViewSwing extends JFrame implements MainView {
    private JPanel mainPanel;
    private JLabel labelWelcome;
    private JLabel labelInstructions;
    private JTextField textFieldUsername;
    private JLabel labelUsername;
    private JPasswordField passwordField;
    private JLabel labelPassword;
    private JButton buttonLogin;
    private JButton buttonAbbrechen;

    public MainViewSwing(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        Dimension frameSize = new Dimension(600, 300);
        this.setPreferredSize(frameSize);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(buttonLogin);
    }

    @Override
    public void showInfoMessage(String message) {

    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Fehler", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void addLoginHandler(ActionListener actionListener) {
        buttonLogin.addActionListener(actionListener);
    }

    @Override
    public void closeWindow() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void hideWindow() {
        this.passwordField.setText("");
        this.textFieldUsername.setText("");
        this.setVisible(false);
    }

    public void showWindow() {
        this.textFieldUsername.requestFocus();
        this.setVisible(true);
    }

    @Override
    public String getUserName() {
        return textFieldUsername.getText();
    }

    @Override
    public char[] getPassword() {
        return passwordField.getPassword();
    }
}
