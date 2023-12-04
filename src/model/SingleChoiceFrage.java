package model;

import java.util.ArrayList;
import java.util.List;

public class SingleChoiceFrage extends Frage {

    public SingleChoiceFrage(int fragenNummer, String fragenText, int thema_id) {
        super(fragenNummer, fragenText, thema_id);
        this.fragenTyp = FragenTyp.SingleChoice;
    }

    public SingleChoiceFrage(int fragenNummer, String fragenText, List<Antwort> antwortmöglichkeiten, int themaID) {
        super(fragenNummer, fragenText, antwortmöglichkeiten, themaID);
        this.fragenTyp = FragenTyp.SingleChoice;
    }

    public SingleChoiceFrage(String fragenText, int themaID, int fragenNummer) {
        super(fragenText, themaID, fragenNummer);
        this.fragenTyp = FragenTyp.SingleChoice;
    }

    @Override
    public Frage clone() {

        List<Antwort> antwortList = new ArrayList<>();

        for (Antwort antwort: antwortmöglichkeiten) {
            antwortList.add(antwort.clone());
        }
        return new SingleChoiceFrage(getFrageID(), getFragenText(), antwortList, getThemaID());
    }
}
