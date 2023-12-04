package view;

import model.Fach;
import model.Gruppe;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public interface ZuweisenPanel {

    public void addFach(Fach fach);

    public void addGruppe(Gruppe gruppe);

    public void addSaveButtonActionListener(ActionListener listener);

    public List<Gruppe> getGruppenNodes();

}
