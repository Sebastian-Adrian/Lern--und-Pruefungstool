package model;

import com.alee.managers.animation.easing.Back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gruppe {

    private String gruppenNr;
    private String ausbildungName;
    private HashMap<Integer, String> zugewieseneThemen;

    public Gruppe(String gruppenNr, String ausbildungName) {
        this.gruppenNr = gruppenNr;
        this.ausbildungName = ausbildungName;
        this.zugewieseneThemen = new HashMap<>();
    }

    public void addZugewiesenesThema(Map.Entry<?, ?> object) {

        if (zugewieseneThemen == null) {
            zugewieseneThemen = new HashMap<>();
        }
        zugewieseneThemen.put((Integer) object.getKey(), (String) object.getValue());
    }

    public HashMap<Integer, String> getZugewieseneThemen() {
        return zugewieseneThemen;
    }

    public String getGruppenNr() {
        return gruppenNr;
    }

    public void setGruppenNr(String gruppenNr) {
        this.gruppenNr = gruppenNr;
    }

    public String getAusbildungName() {
        return ausbildungName;
    }

    public void setAusbildungName(String ausbildungName) {
        this.ausbildungName = ausbildungName;
    }

    @Override
    public String toString() {
        return gruppenNr;
    }

    public void addZugewiesenesThema(Integer themaNr, String themaAnzeigeName) {

        this.zugewieseneThemen.put(themaNr, themaAnzeigeName);

    }

    public void setZugewieseneThemen(HashMap<Integer, String> zugewieseneThemen) {
        this.zugewieseneThemen = zugewieseneThemen;
    }

    public void removeThema(String themaNr) {

        this.zugewieseneThemen.remove(themaNr);
    }

    @Override
    public Gruppe clone() throws CloneNotSupportedException {
        return new Gruppe(gruppenNr, ausbildungName);
    }
}
