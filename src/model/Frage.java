package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Frage {

    protected int themaID;
    // Nummer aus der Datenbank
    protected int frageID;
    // Nummer Intern
    protected int fragenNummer;
    protected FragenTyp fragenTyp;
    protected String fragenText;
    protected int abgefragt = 0;
    protected int davonRichtig = 0;
    protected int davonFalsch = 0;
    protected List<Antwort> antwortmöglichkeiten;

    public Frage(int frageID, String fragenText, int themaID) {
        this.frageID = frageID;
        this.fragenText = fragenText;
        this.themaID = themaID;
        this.antwortmöglichkeiten = new ArrayList<>();
    }

    public Frage(int fragenID, String fragenText, List<Antwort> antwortmöglichkeiten, int themaID) {
        this.themaID = themaID;
        this.frageID = fragenID;
        this.fragenText = fragenText;
        this.antwortmöglichkeiten = antwortmöglichkeiten;
    }

    // neue Frage in Thema
    public Frage(String fragenText, int themaID, int fragenNummer) {
        this.themaID = themaID;
        this.fragenNummer = fragenNummer;
        this.fragenText = fragenText;
        this.antwortmöglichkeiten = new ArrayList<>();
    }

    public void addAntwort(Antwort antwort) {
        if (antwort != null) {
            antwortmöglichkeiten.add(antwort);
        }
    }

    public abstract Frage clone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frage frage)) return false;

        if (themaID != frage.themaID) return false;
        if (frageID != frage.frageID) return false;
        if (fragenTyp != frage.fragenTyp) return false;
        return fragenText.equals(frage.fragenText);
    }

    public int getAbgefragt() {
        return abgefragt;
    }

    public void setAbgefragt(int abgefragt) {
        this.abgefragt = abgefragt;
    }

    public List<Antwort> getAntwortmöglichkeiten() {
        return antwortmöglichkeiten;
    }

    public void setAntwortmöglichkeiten(List<Antwort> antwortmöglichkeiten) {
        this.antwortmöglichkeiten = antwortmöglichkeiten;
    }

    public int getFragenNummer() {
        return this.fragenNummer;
    }

    public void setFragenNummer(int fragenNummer) {
        this.fragenNummer = fragenNummer;
    }

    public int getDavonFalsch() {
        return davonFalsch;
    }

    public void setDavonFalsch(int davonFalsch) {
        this.davonFalsch = davonFalsch;
    }

    public int getDavonRichtig() {
        return davonRichtig;
    }

    public void setDavonRichtig(int davonRichtig) {
        this.davonRichtig = davonRichtig;
    }

    public int getFrageID() {
        return frageID;
    }

    public void setFrageID(int frageID) {
        this.frageID = frageID;
    }

    public String getFragenText() {
        return fragenText;
    }

    public void setFragenText(String fragenText) {
        this.fragenText = fragenText;
    }

    public FragenTyp getFragenTyp() {
        return fragenTyp;
    }

    public void setFragenTyp(FragenTyp fragenTyp) {
        this.fragenTyp = fragenTyp;
    }

    public int getThemaID() {
        return themaID;
    }

    public void setThemaID(int themaID) {
        this.themaID = themaID;
    }

    @Override
    public int hashCode() {
        int result = themaID;
        result = 31 * result + (fragenTyp != null ? fragenTyp.hashCode() : 0);
        result = 31 * result + frageID;
        result = 31 * result + (fragenText != null ? fragenText.hashCode() : 0);
        result = 31 * result + abgefragt;
        result = 31 * result + davonRichtig;
        result = 31 * result + davonFalsch;
        result = 31 * result + (antwortmöglichkeiten != null ? antwortmöglichkeiten.hashCode() : 0);
        return result;
    }

    public void removeAntwort(Antwort antwort) {

        if (antwort != null) {
            antwortmöglichkeiten.remove(antwort);
        }
    }

    @Override
    public String toString() {
        return this.fragenNummer + ": " + fragenText;
    }
}

