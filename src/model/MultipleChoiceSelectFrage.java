package model;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceSelectFrage extends Frage {

    public MultipleChoiceSelectFrage(int fragenNummer, String fragenText, int thema_id) {
        super(fragenNummer, fragenText, thema_id);
        this.fragenTyp = FragenTyp.MultipleChoiceSelect;
    }

    public MultipleChoiceSelectFrage(int fragenNummer, String fragenText, List<Antwort> antwortmöglichkeiten, int themaID) {
        super(fragenNummer, fragenText, antwortmöglichkeiten, themaID);
        this.fragenTyp = FragenTyp.MultipleChoiceSelect;
    }

    public MultipleChoiceSelectFrage(String fragenText, int themaID, int fragenNummer) {
        super(fragenText, themaID, fragenNummer);
        this.fragenTyp = FragenTyp.MultipleChoiceSelect;
    }

    @Override
    public Frage clone() {
        List<Antwort> antwortList = new ArrayList<>();

        for (Antwort antwort: antwortmöglichkeiten) {
            antwortList.add(antwort.clone());
        }
        return new MultipleChoiceSelectFrage(getFrageID(), getFragenText(), antwortList, getThemaID());
    }
}
