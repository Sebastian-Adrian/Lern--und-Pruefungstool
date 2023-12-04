package model;

import java.util.ArrayList;
import java.util.List;

public class TextEingabeFrage extends Frage {

    public TextEingabeFrage(int fragenNummer, String fragenText, int thema_id) {
        super(fragenNummer, fragenText, thema_id);
        this.fragenTyp = FragenTyp.TextEingabe;
    }

    public TextEingabeFrage(int fragenNummer, String fragenText, List<Antwort> antwortmöglichkeiten, int themaID) {
        super(fragenNummer, fragenText, antwortmöglichkeiten, themaID);
        this.fragenTyp = FragenTyp.TextEingabe;
    }

    public TextEingabeFrage(String fragenText, int themaID, int fragenNummer) {
        super(fragenText, themaID, fragenNummer);
        this.fragenTyp = FragenTyp.TextEingabe;
    }

    @Override
    public Frage clone() {
        List<Antwort> antwortList = new ArrayList<>();

        for (Antwort antwort: antwortmöglichkeiten) {
            antwortList.add(antwort.clone());
        }
        return new TextEingabeFrage(getFrageID(), getFragenText(), antwortList, getThemaID());
    }
}
