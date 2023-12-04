package view.Components;

import model.Antwort;

import javax.swing.*;

public class AntwortRadioButton extends JRadioButton implements AntwortComponent {

    private Antwort antwort;

    public AntwortRadioButton(String text, boolean selected, Antwort antwort) {
        super(text, selected);
        this.antwort = antwort;
    }

    public Antwort getAntwort() {
        return antwort;
    }

    public void setAntwort(Antwort antwort) {
        this.antwort = antwort;
    }
}
