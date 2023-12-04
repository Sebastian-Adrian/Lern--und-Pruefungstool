package controller;

import dao.DAO;
import dao.MySqlDao;
import model.Fach;
import model.Gruppe;
import view.ZuweisenPanel;

import java.awt.event.ActionEvent;
import java.util.List;

public class ZuweisenController {

    private final DAO dao;
    private final ZuweisenPanel view;

    public ZuweisenController(ZuweisenPanel view) {

        this.view = view;
        this.dao = MySqlDao.getInstance();

        view.addSaveButtonActionListener(this::saveZuweisungen);

        List<Gruppe> gruppeList = dao.getAlleGruppenMitZugewiesenenThemen();

        for (Gruppe gruppe : gruppeList) {
            view.addGruppe(gruppe);
        }

        List<Fach> fachList = dao.getAlleFächerMitZugewiesenenThemen();

        for (Fach fach : fachList) {
            view.addFach(fach);
        }
    }

    private void saveZuweisungen(ActionEvent event) {

        List<Gruppe> gruppeList = view.getGruppenNodes();

        if (!gruppeList.isEmpty()) {
            dao.updateBenutzerThemen(gruppeList);
        }
    }
}
