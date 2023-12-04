package controller;

import dao.DAO;
import dao.MySqlDao;
import org.mindrot.jbcrypt.BCrypt;
import pojo.Access;
import pojo.UserAccess;
import view.ControlPanel;
import view.AdminPanel.AdminPanelSwing;
import view.MainView.MainView;
import view.MainView.MainViewSwing;
import view.UserPanel.UserPanelSwing;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainController{

    private final MainView mainView;
    private static DAO dao;
    private ControlPanel controlPanel;
    private Access access;

    public static DAO getDao() {
        return dao;
    }

    public MainController(MainView mainView, DAO dao) {
        this.mainView = mainView;
        MainController.dao = dao;
        this.access = new UserAccess();

        this.mainView.addLoginHandler(this::performLogin);
    }

    private void performShowUserControlPanel(String username) {
        String anzeigenname = dao.getAnzeigenName(username);
        UserPanelSwing userPanelSwing = new UserPanelSwing("Willkommen " + anzeigenname, username);
        userPanelSwing.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainView.showWindow();
            }
        });
        new UserController(username, userPanelSwing, false);
        this.mainView.hideWindow();
    }

    private void performShowAdminControlPanel(String username) {
        AdminPanelSwing adminPanelSwing = new AdminPanelSwing(username);

        adminPanelSwing.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainView.showWindow();
            }
        });
        new UserController(username, adminPanelSwing, true);
        mainView.hideWindow();
    }

    private void performLogin(ActionEvent event) {

            String username = mainView.getUserName();
            char[] eingabePasswort = mainView.getPassword();
            String hashedPasswortVonDB = dao.getHashedPasswordFromUser(username);

            if (hashedPasswortVonDB != null) {
                if (BCrypt.checkpw(String.valueOf(eingabePasswort), hashedPasswortVonDB)) {
                    if (dao.istAdmin(username)) {
                        dao.changeUserLogin(access.getDbDozentUser(), access.getDbDozentPwd());
                        performShowAdminControlPanel(username);
                    } else {
                        dao.changeUserLogin(access.getDbUser(), access.getDbPasswd());
                        performShowUserControlPanel(username);
                    }
                } else {
                    mainView.showErrorMessage("Unbekannte Kombination von Benutzername und Passwort, probiere es erneut");
                }
            } else {
                mainView.showErrorMessage("Unbekannte Kombination von Benutzername und Passwort, probiere es erneut");
            }
            eingabePasswort = null;
        //}
    }

    public static void main(String[] args) {
        new MainController(new MainViewSwing("Lern- und Prüfungstool"), MySqlDao.getInstance());
    }
}
