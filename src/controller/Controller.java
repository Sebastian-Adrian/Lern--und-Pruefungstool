package controller;

import model.Thema;

public interface Controller {

    Thema loadThemaStatistik(Thema thema);

    void loadUserFächer();

    void performNeuesThema(int fachNr);

    void removeThema(Thema thema);

    void saveChanges(Thema thema, Thema themaOld);

    String getUsername();

    void updateStatistik(String username, int frage_id, boolean richtigeAntwort);

    void starteLernen(Thema thema);


}
