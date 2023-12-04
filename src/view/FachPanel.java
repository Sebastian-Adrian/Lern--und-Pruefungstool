package view;

import controller.Controller;
import model.Fach;
import model.Thema;
import view.ThemenEditor.ThemenEditorSwing;
import view.ThemenLernenPanel.ThemenLernenPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class FachPanel extends JPanel {

    private JButton buttonThema;
    private Controller controller;
    private JPanel toolBarPanel;
    private JPanel middlePanel;

    public FachPanel(Fach fach, boolean istAdmin, Controller controlPanel) {

        this.controller = controlPanel;
        this.setLayout(new BorderLayout());

        toolBarPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new GridBagLayout());

        this.add(toolBarPanel, BorderLayout.PAGE_START);
        this.add(middlePanel, BorderLayout.CENTER);

        JToolBar jToolBar = new JToolBar();
        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);
        toolBarPanel.add(jToolBar);

        int y = 0;

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 5, 5, 15);
        constraints.ipadx = 5;
        constraints.gridx = 1;
        constraints.gridy = y;
        constraints.anchor = GridBagConstraints.PAGE_START;


        if (istAdmin) {

            // Button zum neuen Thema Erstellen
            Image newTopicImage = null;
            try {
                newTopicImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("..\\img\\add_topic_FILL0_24.png")));
            } catch (IOException e) {
                // TODO: was wenn IMG nicht vorhanden?
                e.printStackTrace();
            }
            Icon newTopicIcon = new ImageIcon(Objects.requireNonNull(newTopicImage));
            Dimension toolBarButtonDimension = new Dimension(30, 30);
            JButton neuesThemaButton = new JButton();
            neuesThemaButton.setToolTipText("neues Thema erstellen");
            neuesThemaButton.setMaximumSize(toolBarButtonDimension);
            neuesThemaButton.setPreferredSize(toolBarButtonDimension);
            neuesThemaButton.setIcon(newTopicIcon);
            neuesThemaButton.addActionListener(e ->
                    controlPanel.performNeuesThema(fach.getFachNr()));
            jToolBar.add(neuesThemaButton, BorderLayout.PAGE_START);
            jToolBar.addSeparator(new Dimension(30, 30));

            // TextField und Button zum Ändern des Fachnamens
            Image renameFachImage = null;
            try {
                renameFachImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("..\\img\\rename_outline_FILL0_24.png")));
            } catch (IOException e) {
                // TODO: was wenn IMG nicht vorhanden?
                e.printStackTrace();
            }
            Icon renameFachIcon = new ImageIcon(Objects.requireNonNull(renameFachImage));
            JTextField fachNameTextField = new JTextField(fach.getFachname());
            Dimension fachNameDimension = new Dimension(200, 30);
            fachNameTextField.setPreferredSize(fachNameDimension);
            fachNameTextField.setMaximumSize(fachNameDimension);
            fachNameTextField.setEditable(false);
            fachNameTextField.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    fachNameTextField.setEditable(false);
                }
            });


            // Umbenennen Button
            JButton renameFachButton = new JButton(renameFachIcon);
            renameFachButton.setMaximumSize(toolBarButtonDimension);
            renameFachButton.setPreferredSize(toolBarButtonDimension);
            renameFachButton.addActionListener(e -> fachNameTextField.setEditable(true));

            jToolBar.add(fachNameTextField);
            jToolBar.add(renameFachButton);
        }

        for (Thema thema : fach.getThemaList()) {
            int x = 0;
            constraints.insets = new Insets(10, 5, 5, 15);
            constraints.ipadx = 5;
            constraints.gridx = x;
            constraints.gridy = y;

            // Themen Name
            JTextPane themaNamePane = new JTextPane();
            themaNamePane.setText(thema.getAnzeigeName());
            themaNamePane.setEditable(false);
            themaNamePane.setBackground(null);

            middlePanel.add(themaNamePane, constraints);
            x++;
            constraints.gridx = x;

            // Fragen:
            JTextPane fragenTextPane = new JTextPane();
            fragenTextPane.setText("Fragen: ");
            fragenTextPane.setEditable(false);
            fragenTextPane.setBackground(null);
            Font f = fragenTextPane.getFont();
            fragenTextPane.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
            middlePanel.add(fragenTextPane, constraints);

            x++;
            constraints.gridx = x;

            // Anzahl Fragen
            JTextPane statistikTextPane = new JTextPane();
            statistikTextPane.setText(String.valueOf(thema.getAnzahlFragen()));
            statistikTextPane.setEditable(false);
            statistikTextPane.setBackground(null);
            Color labelColor = new Color(0, 150, 0);
            statistikTextPane.setForeground(labelColor);
            statistikTextPane.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
            middlePanel.add(statistikTextPane, constraints);


            x++;
            constraints.gridx = x;
            constraints.gridy = y;

            if (!istAdmin) {
                addLernenButton(thema, constraints);
            } else {
                addBearbeitenButton(thema, constraints);
                x++;
                constraints.gridx = x;
                addRemoveThemaButton(thema, constraints);
            }
            y++;
        }
    }

    private void addRemoveThemaButton(Thema thema, GridBagConstraints constraints) {
        JButton removeThemaButton = new JButton("löschen");
        removeThemaButton.addActionListener( e -> {
            int ergebnis = JOptionPane.showConfirmDialog(this, "Soll das Thema: " + thema.getAnzeigeName() +
                                                " wirklich gelöscht werden?\nDer Vorgang lässt sich nicht rückgängig machen.",
                    "Thema " + thema.getAnzeigeName() + " löschen", JOptionPane.YES_NO_OPTION);
            if (ergebnis == 0) {
                controller.removeThema(thema);
            }
        });
        middlePanel.add(removeThemaButton, constraints);
    }

    private void addBearbeitenButton(Thema thema, GridBagConstraints constraints) {
        this.buttonThema = new JButton("Bearbeiten");
        buttonThema.addActionListener(e -> new ThemenEditorSwing(controller, thema));
        middlePanel.add(buttonThema, constraints);
    }

    private void addLernenButton(Thema thema, GridBagConstraints constraints) {
        this.buttonThema = new JButton("Lernen");
        buttonThema.addActionListener(e -> new ThemenLernenPanel(controller, thema));
        middlePanel.add(buttonThema, constraints);
    }
}
