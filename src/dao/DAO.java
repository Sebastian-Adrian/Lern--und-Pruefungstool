package dao;

import model.*;

import java.util.ArrayList;
import java.util.List;

public interface DAO {

    void insertNewFach(String fachName);

    void changeUserLogin(String username, String pwd);

    List<Fach> getAlleFächer();

    List<Fach> getAlleFächerMitZugewiesenenThemen();

    List<Gruppe> getAlleGruppenMitZugewiesenenThemen();

    String getAnzeigenName(String username);

    List<Fach> getBenutzerFächer(String username);

    String getHashedPasswordFromUser(String username);

    int getNextThemaNr();

    boolean istAdmin(String username);

    Thema loadThemaStatistikFromUser(String username, Thema thema);

    void removeAntwort(int antwort_id);

    void removeThema(Thema thema);

    void saveThema(Thema thema);

    void removeFrage(int fragenNr);

    void updateAntwort(Antwort antwort);

    void updateBenutzerThemen(List<Gruppe> gruppeList);

    void updateFrage(Frage frage);

    void updateThemaName(Thema thema);

    boolean themaExists(int themaNr);

    void updateStatistik(String username, int frageID, boolean richtig);

}
