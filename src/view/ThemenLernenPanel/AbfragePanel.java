package view.ThemenLernenPanel;

import model.Frage;

import javax.swing.*;
import java.awt.*;

public class AbfragePanel extends JPanel {

    private JPanel rootPanel;
    private JTextArea frageTextArea;


    public AbfragePanel(Frage frage) {

        int x = 0, y = 0;

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 5, 5, 15);
        constraints.gridx = x;
        constraints.gridy = y;
        this.setLayout(gridBagLayout);

        createFragenComponents();

    }

    private void createFragenComponents() {




    }
}
