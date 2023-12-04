package view;

import model.Antwort;
import model.Frage;
import model.FragenTyp;
import view.Components.AntwortCheckBox;
import view.Components.AntwortComponent;
import view.Components.AntwortRadioButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragePanel extends JPanel {

    private final FragenTyp fragenTyp;
    private final Frage originalFrage;
    private final Frage userFrage;
    private final boolean istAdmin;
    private List<AntwortComponent> antwortComponents;
    private List<Antwort> antwortList;
    private int x, y;
    private ButtonGroup buttonGroup;

    public FragePanel(Frage originalFrage, boolean istAdmin) {

        this.istAdmin = istAdmin;
        this.originalFrage = originalFrage;
        // Im LernenPanel zum Vergleichen der Antworten
        this.userFrage = originalFrage.clone();
        this.fragenTyp = originalFrage.getFragenTyp();
        this.antwortComponents = new ArrayList<>();

        createFragenComponents();
    }



    private void createAddAntwortButton(GridBagConstraints constraints, Frage frage) {

        try {
            constraints = new GridBagConstraints();
            constraints.insets = new Insets(10, 5, 5, 0);
            constraints.gridwidth = 1;
            constraints.gridx = 3;
            constraints.gridy = y + 1;
            constraints.fill = GridBagConstraints.NONE;

            JButton addAntwortButton = new JButton();
            Image image = ImageIO.read(Objects.requireNonNull(getClass().getResource("..\\img\\add_FILL0_20.png")));
            Icon icon = new ImageIcon(image);
            Dimension dimension = new Dimension(20, 20);

            addAntwortButton.setIcon(icon);
            addAntwortButton.setMinimumSize(dimension);
            addAntwortButton.setMaximumSize(dimension);
            addAntwortButton.setPreferredSize(dimension);
            addAntwortButton.setSize(dimension);
            addAntwortButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frage.addAntwort(new Antwort("",false));
                    createFragenComponents();
                }
            });
            this.add(addAntwortButton, constraints);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAntwortTextArea(GridBagConstraints constraints, Antwort antwort) {

        JTextArea jTextArea = new JTextArea(antwort.getAntwortText());
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setBorder(new EmptyBorder(2,2,2,2));

        Dimension textAreaDimension = new Dimension(200, 40);

        JScrollPane scrollPane = new JScrollPane(jTextArea);
        scrollPane.setPreferredSize(textAreaDimension);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        if (istAdmin) {
            jTextArea.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    antwort.setAntwortText(jTextArea.getText());
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    antwort.setAntwortText(jTextArea.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    antwort.setAntwortText(jTextArea.getText());
                }
            });
        }
        constraints.gridx = 1;
        constraints.gridy = y;
        constraints.gridwidth = 2;
        constraints.ipadx = 300;
        constraints.ipady = 20;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        this.add(scrollPane, constraints);
    }

    private void createDeleteButton(GridBagConstraints constraints, Antwort antwort) {

        // TODO: Buttons nicht immer einheitlich groß?
        try {
            constraints = new GridBagConstraints();
            constraints.insets = new Insets(10, 5, 5, 0);
            constraints.gridwidth = 1;
            constraints.gridx = 3;
            constraints.gridy = y;
            constraints.fill = GridBagConstraints.NONE;

            JButton deleteButton = new JButton();
            Image image = ImageIO.read(Objects.requireNonNull(getClass().getResource("..\\img\\delete_FILL0_20.png")));
            Icon icon = new ImageIcon(image);
            Dimension dimension = new Dimension(20, 20);
            deleteButton.setIcon(icon);

            deleteButton.setMinimumSize(dimension);
            deleteButton.setMaximumSize(dimension);
            deleteButton.setPreferredSize(dimension);
            deleteButton.setSize(dimension);
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    originalFrage.removeAntwort(antwort);
                    createFragenComponents();
                }
            });
            this.add(deleteButton, constraints);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createFragenComponents() {

        this.removeAll();

        x = 0;
        y = 0;

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 5, 5, 15);

        LayoutManager gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);

        createFragenTextArea(constraints);

        if (fragenTyp == FragenTyp.SingleChoice) {
            buttonGroup = new ButtonGroup();
        }

        for (Antwort antwort : istAdmin ? originalFrage.getAntwortmöglichkeiten() : userFrage.getAntwortmöglichkeiten()) {

            y++;

            // Radio/Check -Box
            constraints = new GridBagConstraints();
            constraints.insets = new Insets(10, 5, 5, 0);
            constraints.gridwidth = 1;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.LINE_START;
            constraints.gridx = 0;
            constraints.gridy = y;

            if (fragenTyp == FragenTyp.MultipleChoiceCheck || fragenTyp == FragenTyp.MultipleChoiceSelect) {
                createMultipleChoiceBox(antwort, constraints);
            } else if (fragenTyp == FragenTyp.SingleChoice) {
                createSingleChoiceBox(antwort, constraints);
            } else if (fragenTyp == FragenTyp.TextEingabe) {
                createTextEingabeLabel(y, constraints);
            }

            createAntwortTextArea(constraints, antwort);

            if (istAdmin) {
                createDeleteButton(constraints, antwort);
            }
        }

        if (istAdmin) {
            createAddAntwortButton(constraints, originalFrage);
        }

        this.updateUI();
    }

    private void createFragenTextArea(GridBagConstraints constraints) {

        Dimension textAreaDimension = new Dimension(420, 120);

        JTextArea fragenTextArea = new JTextArea(originalFrage.getFragenText());
        fragenTextArea.setLineWrap(true);
        fragenTextArea.setWrapStyleWord(true);
        fragenTextArea.setBorder(new EmptyBorder(2,2,2,2));;
        fragenTextArea.setMinimumSize(textAreaDimension);


        JScrollPane fragenTextScrollPane = new JScrollPane(fragenTextArea);
        fragenTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        fragenTextScrollPane.setPreferredSize(textAreaDimension);

        if (istAdmin) {
            fragenTextArea.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    originalFrage.setFragenText(fragenTextArea.getText());
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    originalFrage.setFragenText(fragenTextArea.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    originalFrage.setFragenText(fragenTextArea.getText());
                }
            });
        } else {
            fragenTextArea.setEditable(false);
        }

        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.ipadx = 140;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = 4;
        constraints.weightx = 1;
        constraints.weighty = 1;

        this.add(fragenTextScrollPane, constraints);
    }

    private void createMultipleChoiceBox(Antwort antwort, GridBagConstraints constraints) {

        AntwortCheckBox antwortCheckBox = new AntwortCheckBox("", istAdmin && antwort.istWahr(), antwort);
        // Wichtig zur visuellen Anzeige von korrekt/unkorrekt gegebenen Antworten im Lernen-Panel
        antwortCheckBox.setBorderPainted(true);
        antwortCheckBox.setBorder(new EmptyBorder(1,1,1,1));
        antwortCheckBox.addActionListener(e -> antwort.setIstWahr(antwortCheckBox.isSelected()));
        this.add(antwortCheckBox, constraints);
        this.antwortComponents.add(antwortCheckBox);
    }

    private void createSingleChoiceBox(Antwort antwort, GridBagConstraints constraints) {

        AntwortRadioButton antwortRadioButton = new AntwortRadioButton("", istAdmin && antwort.istWahr(), antwort);
        // Wichtig zur visuellen Anzeige von korrekt/unkorrekt gegebenen Antworten im Lernen-Panel
        antwortRadioButton.setBorderPainted(true);
        antwortRadioButton.setBorder(new EmptyBorder(1,1,1,1));
        antwortRadioButton.addItemListener(e -> {
            antwort.setIstWahr(antwortRadioButton.isSelected());
        });

        buttonGroup.add(antwortRadioButton);
        this.add(antwortRadioButton, constraints);
        this.antwortComponents.add(antwortRadioButton);
    }

    private void createTextEingabeLabel(int nr, GridBagConstraints constraints) {
        JLabel jLabel = new JLabel(String.valueOf(nr));
        this.add(jLabel, constraints);
    }

    public List<AntwortComponent> getAntwortComponents() {
        return antwortComponents;
    }

    public Frage getOriginalFrage() {
        return originalFrage;
    }

    public Frage getUserFrage() {
        return userFrage;
    }

}
