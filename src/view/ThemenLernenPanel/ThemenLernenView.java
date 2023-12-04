package view.ThemenLernenPanel;

import model.Frage;
import model.Thema;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface ThemenLernenView {

    void addNächsteFrageHandler(ActionListener listener);

    void addVorherigeFrageHandler(ActionListener listener);

    void addCheckFrageHandler(ActionListener listener);

    void addBeendenHandler(ActionListener listener);

    Frage getAktuelleFrage();

    Thema getThema();

    void addFrageJListDefaultListModel(DefaultListModel<Frage> listModel);

}
