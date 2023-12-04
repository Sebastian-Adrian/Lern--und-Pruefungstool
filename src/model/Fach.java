package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fach {

    private final List<Thema> themaList;
    private int fachNr;
    private String fachname;
    private HashMap<Integer, String> zugewieseneThemen;


    public Fach(int fachNr, String fachname) {
        this.fachname = fachname;

        this.themaList = new ArrayList<>();
        this.fachNr = fachNr;
    }

    public void addThema(Thema thema) {
        if (thema != null) {
            themaList.add(thema);
        }
    }

    public int getFachNr() {
        return fachNr;
    }

    public HashMap<Integer, String> getZugewieseneThemen() {
        return zugewieseneThemen;
    }

    public void setFachNr(int fachNr) {
        this.fachNr = fachNr;
    }

    public String getFachname() {
        return fachname;
    }

    public void setFachname(String fachname) {
        this.fachname = fachname;
    }

    public List<Thema> getThemaList() {
        return themaList;
    }

    @Override
    public String toString() {
        return fachname;
    }

    public void setZugewieseneThemen(HashMap<Integer, String> zugewieseneThemenNr) {

        this.zugewieseneThemen = zugewieseneThemenNr;

    }
}
