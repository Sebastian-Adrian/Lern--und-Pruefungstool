package model;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceCheckFrage extends Frage {

    public MultipleChoiceCheckFrage(int fragenID, String fragenText, int them_id) {
        super(fragenID, fragenText, them_id);
        this.fragenTyp = FragenTyp.MultipleChoiceCheck;
    }

    public MultipleChoiceCheckFrage(int fragenID, String fragenText, List<Antwort> antwortmöglichkeiten, int thema_id) {
        super(fragenID, fragenText, antwortmöglichkeiten, thema_id);
        this.fragenTyp = FragenTyp.MultipleChoiceCheck;
    }

    public MultipleChoiceCheckFrage(String fragenText, int themaID, int fragenNummer) {
        super(fragenText, themaID, fragenNummer);
        this.fragenTyp = FragenTyp.MultipleChoiceCheck;
    }

    @Override
    public Frage clone() {

        List<Antwort> antwortList = new ArrayList<>();

        for (Antwort antwort: antwortmöglichkeiten) {
            antwortList.add(antwort.clone());
        }
        return new MultipleChoiceCheckFrage(getFrageID(), getFragenText(), antwortList, getThemaID());
    }
}
