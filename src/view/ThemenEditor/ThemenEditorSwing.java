package view.ThemenEditor;

import model.Frage;
import model.Thema;
import controller.Controller;
import view.FragePanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ThemenEditorSwing extends JFrame {

    DefaultListModel<Frage> listModel;
    private JList<Frage> fragenList;
    private JScrollPane fragenListScrollPane;
    private JScrollPane fragenEditorScrollPane;
    private JToolBar toolBar;
    private JButton buttonNewFrage;
    private JButton buttonRemoveFrage;
    private JButton buttonSave;
    private JPanel themenJPanel;
    private JTextField textFieldThemaName;
    private JButton buttonEditThemaName;
    private JPanel fragenEditorPanel;
    private final Controller controller;
    private final Thema themaEdit;
    private Thema themaOld;


    public ThemenEditorSwing(Controller adminController, Thema themaOld) throws HeadlessException {

        super("Themen-Editor – " + themaOld.getAnzeigeName());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.themaEdit = themaOld.clone();
        this.themaOld = themaOld;
        this.controller = adminController;
        this.setContentPane(themenJPanel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double frameWidth = screenSize.width * 0.75;
        double screenHeight = screenSize.height * 0.75;


        Dimension frameSize = new Dimension((int) frameWidth, (int) screenHeight);
        this.setPreferredSize(frameSize);
        this.setMinimumSize(new Dimension(1000, 420));
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);

        textFieldThemaName.setText(themaOld.getAnzeigeName());

        listModel = new DefaultListModel<>();
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            // gewählte Frage ins Panel laden
            if (!e.getValueIsAdjusting()) {
                if (fragenList.getSelectedIndex() != -1) {
                    initEditorPanel(fragenList.getSelectedValue());
                }
            }
        });

        for (Frage frage : themaEdit.getFrageList()) {
            int fragenNummer = themaEdit.getNextNumber();
            frage.setFragenNummer(fragenNummer);
            listModel.addElement(frage);
        }

        fragenList.setModel(listModel);
        fragenList.setSelectionModel(selectionModel);

        initToolbar();
    }

    public void addFrage(Frage frage) {
        themaEdit.addFrage(frage);
        listModel.addElement(frage);
    }

    public Thema getThemaEdit() {
        return themaEdit;
    }

    private void initEditorPanel(Frage frage) {
        fragenEditorPanel.removeAll();
        fragenEditorPanel.add(new FragePanel(frage, true));
        fragenEditorPanel.updateUI();
    }

    private void initToolbar() {

        buttonNewFrage.addActionListener(e -> {
            AddNewFrage addNewFrage = new AddNewFrage(this);
        });

        buttonRemoveFrage.addActionListener(e -> {
            int selectedIndex = fragenList.getSelectedIndex();
            Frage frage = fragenList.getSelectedValue();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                themaEdit.removeFrage(frage);
            }
        });

        buttonSave.addActionListener(e -> {
            controller.saveChanges(themaEdit, themaOld);
            themaOld = themaEdit.clone();
            controller.loadUserFächer();
        });

        buttonEditThemaName.addActionListener(e -> {
            textFieldThemaName.setEditable(true);
        });

        textFieldThemaName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                themaEdit.setAnzeigeName(textFieldThemaName.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                themaEdit.setAnzeigeName(textFieldThemaName.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                themaEdit.setAnzeigeName(textFieldThemaName.getText());
            }
        });

        textFieldThemaName.addMouseListener(new MouseListener() {
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
                textFieldThemaName.setEditable(false);
            }
        });


    }
}
