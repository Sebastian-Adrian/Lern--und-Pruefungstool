package controller;

import model.Frage;
import model.Thema;
import view.ThemenLernenPanel.ThemenLernenPanel;
import view.ThemenLernenPanel.ThemenLernenView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

public class LernenController implements Lernen{

    private ThemenLernenView view;


    public LernenController(ThemenLernenView lernenPanel) {

        this.view = lernenPanel;
        this.view.addCheckFrageHandler(this::checkAntwort);
        this.view.addNächsteFrageHandler(this::nächsteFrage);
        this.view.addVorherigeFrageHandler(this::vorherigeFrage);


        initFrageList();

    }


    @Override
    public void checkAntwort(ActionEvent event) {
    }

    @Override
    public void initFrageList() {

        Thema thema = view.getThema();

        List<Frage> frageList = thema.getFrageList();
        Collections.shuffle(frageList);

        frageJListDefaultListModel = new DefaultListModel<Frage>();
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            // gewählte Frage laden
            if (!e.getValueIsAdjusting()) {

            }
        });


        this.view.addFrageJListDefaultListModel();
    }

    @Override
    public void initStatistik() {

    }

    @Override
    public void loadFrage(int nr) {

    }

    @Override
    public Thema loadThemaStatistik(Thema thema) {
        return null;
    }

    @Override
    public void nächsteFrage(ActionEvent event) {

    }

    @Override
    public void updateLabels(ActionEvent event) {

    }

    @Override
    public void updateStatistik(String username, int frage_id, boolean richtigeAntwort) {

    }

    @Override
    public void vorherigeFrage(ActionEvent event) {

    }
}
