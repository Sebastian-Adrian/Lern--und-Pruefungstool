package model;

import java.util.Objects;

public class Antwort {

    private int antwortID;
    private String antwortText;
    private boolean istWahr;

    public Antwort(int antwortID, String antwortText, boolean istWahr) {
        this.antwortID = antwortID;
        this.antwortText = antwortText;
        this.istWahr = istWahr;
    }

    public Antwort(String antwortText, boolean istWahr) {
        this.antwortText = antwortText;
        this.istWahr = istWahr;
    }

    public int getAntwortID() {
        return antwortID;
    }

    public void setAntwortID(int antwortID) {
        this.antwortID = antwortID;
    }

    public boolean isIstWahr() {
        return istWahr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Antwort antwort)) return false;

        if (antwortID != antwort.antwortID) return false;
        if (istWahr != antwort.istWahr) return false;
        return Objects.equals(antwortText, antwort.antwortText);
    }

    @Override
    public int hashCode() {
        int result = antwortID;
        result = 31 * result + (antwortText != null ? antwortText.hashCode() : 0);
        result = 31 * result + (istWahr ? 1 : 0);
        return result;
    }

    @Override
    public Antwort clone() {
        return new Antwort(antwortID, antwortText, istWahr);
    }

    public String getAntwortText() {
        return antwortText;
    }

    public void setAntwortText(String antwortText) {
        this.antwortText = antwortText;
    }

    public boolean istWahr() {
        return istWahr;
    }

    public void setIstWahr(boolean istWahr) {
        this.istWahr = istWahr;
    }
}
