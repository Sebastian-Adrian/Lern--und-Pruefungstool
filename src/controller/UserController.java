package controller;

import dao.DAO;
import dao.MySqlDao;
import model.Antwort;
import model.Fach;
import model.Frage;
import model.Thema;
import view.ControlPanel;
import view.ThemenEditor.ThemenEditorSwing;
import view.ThemenLernenPanel.ThemenLernenPanel;
import view.ZuweisenPanelJTree.ZuweisenPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController implements Controller {

    private final DAO dao;
    private final ControlPanel view;
    private final boolean istAdmin;
    private final String username;

    public UserController(String username, ControlPanel view, boolean istAdmin) {

        this.username = username;
        this.view = view;
        this.istAdmin = istAdmin;
        this.dao = MySqlDao.getInstance();

        this.view.addShowZuweisenActionListener(this::ShowZuweisenPanel);
        this.view.addFachButtonActionListener(this::addNewFach);

        loadUserFächer();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void updateStatistik(String username, int frage_id, boolean richtigeAntwort) {
        dao.updateStatistik(username, frage_id, richtigeAntwort);
    }

    @Override
    public void starteLernen(Thema thema) {

        ThemenLernenPanel lernenPanel = new ThemenLernenPanel(thema.getAnzeigeName(), thema);
        LernenController controller = new LernenController(lernenPanel);

    }

    private void ShowZuweisenPanel(ActionEvent e) {
        new ZuweisenController(new ZuweisenPanel());
    }

    // TODO: Testen !
    private void addNewFach(ActionEvent e) {
        String fachName = JOptionPane.showInputDialog("Bitte Fachnamen eingeben: ");

        if (fachName != null && !fachName.isEmpty()) {
            dao.insertNewFach(fachName);
            loadUserFächer();
        }
    }

    @Override
    public Thema loadThemaStatistik(Thema thema) {
        return dao.loadThemaStatistikFromUser(username, thema);
    }

    @Override
    public void loadUserFächer() {

        view.reloadTabs();
        List<Fach> userFächer;

        if (istAdmin) {
            userFächer = dao.getAlleFächer();
        } else {
            userFächer = dao.getBenutzerFächer(view.getUsername());
        }

        for (Fach fach : userFächer) {
            view.addFach(fach, this);
        }
    }

    private Set<Integer> getFragenNummern(List<Frage> frageList) {

        Set<Integer> integers = new HashSet<>();

        for (Frage frage : frageList) {
            integers.add(frage.getFrageID());
        }

        return integers;
    }

    public void performNeuesThema(int fachNr) {

        int themaNr = dao.getNextThemaNr();
        ThemenEditorSwing themenEditor;
        themenEditor = new ThemenEditorSwing(this, new Thema(fachNr, "neues Thema", themaNr));
    }

    @Override
    public void removeThema(Thema thema) {

        if (thema != null) {
            dao.removeThema(thema);
            loadUserFächer();
        }

    }

    public void saveChanges(Thema themaEdit, Thema themaOld) {

        if (!dao.themaExists(themaEdit.getThemaNr())) {
            dao.saveThema(themaEdit);
        } else if (!themaEdit.getAnzeigeName().equals(themaOld.getAnzeigeName())) {
            dao.updateThemaName(themaEdit);
        }

        // Geänderte Fragen bleiben übrig
        List<Frage> frageListOld = themaOld.getFrageList();
        List<Frage> frageListEdit = themaEdit.getFrageList();
        List<Frage> geänderteFragen = getGeänderteFragen(frageListEdit, frageListOld);
        // Gelöschte Fragen-Nummern bleiben übrig
        List<Integer> entfernteFragen = getEntfernteFragenNr(frageListEdit, frageListOld);


        /*
        for (Frage geänderteFrage : geänderteFragen) {
            List<Antwort> geänderteAntworten = new ArrayList<>();
            Set<Integer> entfernteAntwortenNr = new HashSet<>();
            for (Frage alteFrage : frageListOld) {
                if (geänderteFrage.getFrageID() == alteFrage.getFrageID()) {
                    geänderteAntworten = getGeänderteAntworten(geänderteFrage, alteFrage);
                    entfernteAntwortenNr = getEntfernteAntwortNr(geänderteFrage, alteFrage);
                    break;
                }
            }

            for (Integer antwort_id : entfernteAntwortenNr) {
                dao.removeAntwort(antwort_id);
            }

            for (Antwort antwort : geänderteAntworten) {
                dao.updateAntwort(antwort);
            }
        }
         */

        for (Integer fragenNr : entfernteFragen) {
            dao.removeFrage(fragenNr);
        }

        for (Frage changedFrage : geänderteFragen) {
            dao.updateFrage(changedFrage);
        }

    }

    private List<Integer> getEntfernteFragenNr(List<Frage> frageListEdit, List<Frage> frageListOld) {

        Set<Integer> fragenNrOld = getFragenNummern(frageListOld);
        Set<Integer> fragenNrEdit = getFragenNummern(frageListEdit);
        fragenNrOld.removeAll(fragenNrEdit);

        return new ArrayList<>(fragenNrOld);
    }

    private Set<Integer> getEntfernteAntwortNr(Frage geänderteFrage, Frage alteFrage) {

        Set<Integer> neueAntwortenNr = new HashSet<>();
        Set<Integer> alteAntwortenNr = new HashSet<>();

        for (Antwort antwort : geänderteFrage.getAntwortmöglichkeiten()) {
            neueAntwortenNr.add(antwort.getAntwortID());
        }

        for (Antwort alteAntwort : alteFrage.getAntwortmöglichkeiten()) {
            alteAntwortenNr.add(alteAntwort.getAntwortID());
        }

        alteAntwortenNr.removeAll(neueAntwortenNr);
        return alteAntwortenNr;
    }

    private List<Antwort> getGeänderteAntworten(Frage geänderteFrage, Frage alteFrage) {

        Set<Antwort> geänderteAntworten = new HashSet<>(geänderteFrage.getAntwortmöglichkeiten());
        Set<Antwort> alteAntworten = new HashSet<>(alteFrage.getAntwortmöglichkeiten());
        // Geänderte Antworten bleiben übrig
        geänderteAntworten.removeAll(alteAntworten);
        return new ArrayList<>(geänderteAntworten);
    }

    private List<Frage> getGeänderteFragen(List<Frage> frageListEdit, List<Frage> frageListOld) {

        Set<Frage> fragenOld = new HashSet<>(frageListOld);
        Set<Frage> fragenEdit = new HashSet<>(frageListEdit);
        // Geänderte Fragen bleiben übrig
        fragenEdit.removeAll(fragenOld);
        return new ArrayList<>(fragenEdit);
    }

}
