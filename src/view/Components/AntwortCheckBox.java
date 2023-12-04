package view.Components;

import model.Antwort;

import javax.swing.*;

public class AntwortCheckBox extends JCheckBox implements AntwortComponent {

    private Antwort antwort;

    public AntwortCheckBox(String text, boolean selected, Antwort antwort) {
        super(text, selected);
        this.antwort = antwort;
    }

    public void setAntwort(Antwort antwort) {
        this.antwort = antwort;
    }

    public Antwort getAntwort() {
        return antwort;
    }

}
