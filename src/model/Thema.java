package model;

import java.util.ArrayList;
import java.util.List;

public class Thema {

    private final int fachNr;
    private final int themaNr;
    private int anzahlThemaGelernt;

    public int getAnzahlThemaGelernt() {
        return anzahlThemaGelernt;
    }

    public void setAnzahlThemaGelernt(int anzahlThemaGelernt) {
        this.anzahlThemaGelernt = anzahlThemaGelernt;
    }

    private String anzeigeName;
    List<Frage> frageList;

    public int getFachNr() {
        return fachNr;
    }

    public int getThemaNr() {
        return themaNr;
    }

    public Thema(int fachNr, String anzeigeName, int themaNr) {
        this.fachNr = fachNr;
        this.themaNr = themaNr;
        this.anzeigeName = anzeigeName;
        this.frageList = new ArrayList<>();
    }

    public Thema(int fachNr, String anzeigeName, int themaNr, List<Frage> frageList) {
        this.fachNr = fachNr;
        this.themaNr = themaNr;
        this.anzeigeName = anzeigeName;
        this.frageList = frageList;
    }

    public void addFrage(Frage frage) {
        if (frage != null) {
            frageList.add(frage);
        }
    }

    public String getAnzeigeName() {
        return anzeigeName;
    }

    public void removeFrage(Frage frage) {

        if (frage != null) {
            for (Frage f: frageList) {
                if (f.getFrageID() == frage.getFrageID()) {
                    frageList.remove(f);
                    break;
                }
            }
        }
    }

    public void setAnzeigeName(String anzeigeName) {
        this.anzeigeName = anzeigeName;
    }

    public List<Frage> getFrageList() {
        return frageList;
    }

    public void setFrageList(List<Frage> frageList) {
        this.frageList = frageList;
    }

    public int getNextNumber() {

        int nr = Integer.MIN_VALUE;

        for (Frage frage: frageList) {

            if (frage.fragenNummer > nr) {

                nr = frage.fragenNummer;
            }
        }
        if (nr < 0) nr = 0;

        return nr + 1;
    }

    public int getAnzahlFragen() {
        return frageList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Thema thema)) return false;

        if (themaNr != thema.themaNr) return false;
        if (!anzeigeName.equals(thema.anzeigeName)) return false;
        return frageList.equals(thema.frageList);
    }

    @Override
    public int hashCode() {
        int result = themaNr;
        result = 31 * result + anzeigeName.hashCode();
        result = 31 * result + frageList.hashCode();
        return result;
    }

    @Override
    public Thema clone() {

        List<Frage> frageListClone = new ArrayList<>();

        for (Frage frage : frageList) {
            frageListClone.add(frage.clone());
        }

        return new Thema(fachNr, anzeigeName, themaNr, frageListClone);
    }

    @Override
    public String toString() {
        return anzeigeName;
    }
}
