package controller;

import model.Thema;

import java.awt.event.ActionEvent;

public interface Lernen {


    void checkAntwort(ActionEvent event);

    void initFrageList();

    void initStatistik();

    void loadFrage(int nr);

    Thema loadThemaStatistik(Thema thema);

    void nächsteFrage(ActionEvent event);

    void updateLabels(ActionEvent event);

    void updateStatistik(String username, int frage_id, boolean richtigeAntwort);

    void vorherigeFrage(ActionEvent event);

}
