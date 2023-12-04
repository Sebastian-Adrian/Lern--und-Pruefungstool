package view.ThemenLernenPanel;

import model.Antwort;
import model.Frage;
import model.Thema;
import view.Components.AntwortCheckBox;
import view.Components.AntwortComponent;
import view.Components.AntwortRadioButton;
import view.FragePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ThemenLernenPanel extends JFrame implements ThemenLernenView {

    JButton vorherigeButton;
    JButton nächsteButton;
    JButton checkFrageButton;
    JButton beendenButton;
    private JPanel rootPanel;
    private JPanel centerPanel;
    private JPanel frageListPanel;
    private JScrollPane frageListScrollPane;
    private JPanel statistikPanel;
    private JPanel abfragePanel;
    private JProgressBar progressBar;
    private JPanel aktiveFragePanel;
    private JList<Frage> fragenJList;
    private JLabel statistikLabelGesamtName;
    private JLabel themaGelerntGesamtLabelName;
    private JLabel themaGelerntGesamtLabelWert;
    private JLabel fragenBeantwortetGesamtLabelName;
    private JLabel fragenBeantwortetGesamtLabelWert;
    private JLabel davonRichtigGesamtLabelName;
    private JLabel davonRichtigGesamtLabelWert;
    private JLabel davonFalschGesamtLabelName;
    private JLabel davonFalschGesamtLabelWert;
    private JLabel erfolgsquoteGesamtLabelName;
    private JLabel erfolgsquoteGesamtLabelWert;
    private JLabel statistikLabelSitzungName;
    private JLabel themaGelerntSitzungLabelName;
    private JLabel themaGelerntSitzungLabelWert;
    private JLabel fragenBeantwortetSitzungLabelName;
    private JLabel fragenBeantwortetSitzungLabelWert;
    private JLabel davonRichtigSitzungLabelName;
    private JLabel davonRichtigSitzungLabelWert;
    private JLabel davonFalschSitzungLabelName;
    private JLabel davonFalschSitzungLabelWert;
    private JLabel erfolgsquoteSitzungLabelName;
    private JLabel erfolgsquoteSitzungLabelWert;
    private DefaultListModel<Frage> frageJListDefaultListModel;
    private FragePanel fragePanel;
    private Thema thema;
    private int fragenIndex = 0;
    private List<Frage> frageList;
    private int fragenBeantwortetGesamt = 0;
    private int fragenDavonFalschGesamt = 0;
    private int fragenDavonRichtigGesamt = 0;
    public ThemenLernenPanel(String anzeigeName, Thema thema) {

        super(anzeigeName);
        this.thema = thema;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(rootPanel);
        Dimension frameSize = new Dimension(1280, 640);
        this.setPreferredSize(frameSize);
        this.setMinimumSize(new Dimension(1000, 380));
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);

        frageList = thema.getFrageList();

        if (frageList.size() > 0) {
            initFrageList();
            initButtons();
            initStatistik();
        }
    }

    @Override
    public void addBeendenHandler(ActionListener listener) {
        beendenButton.addActionListener(listener);
    }

    @Override
    public void addCheckFrageHandler(ActionListener listener) {
        checkFrageButton.addActionListener(listener);
    }

    @Override
    public void addFrageJListDefaultListModel(DefaultListModel<Frage> listModel) {

    }

    @Override
    public void addNächsteFrageHandler(ActionListener listener) {
        nächsteButton.addActionListener(listener);
    }

    @Override
    public void addVorherigeFrageHandler(ActionListener listener) {
        vorherigeButton.addActionListener(listener);
    }

    private void checkAntwort() {

        // Frage mit gegebenen Antworten vom User
        Frage userFrage = fragePanel.getUserFrage();
        Frage originalFrage = fragePanel.getOriginalFrage();
        List<Antwort> userAntworten = userFrage.getAntwortmöglichkeiten();
        List<Antwort> originalAntworten = originalFrage.getAntwortmöglichkeiten();
        HashMap<Integer, AntwortComponent> antwortComponents = new HashMap<>();

        for (AntwortComponent component : fragePanel.getAntwortComponents()) {
            int antwortID = component.getAntwort().getAntwortID();
            antwortComponents.put(antwortID, component);
        }

        boolean richtigeAntwort = true;

        Border rightBorder = new LineBorder(Color.GREEN);
        Border wrongBorder = new LineBorder(Color.RED);

        for (Antwort userAntwort : userAntworten) {
            for (Antwort originalAntwort : originalAntworten) {
                if (userAntwort.getAntwortID() == originalAntwort.getAntwortID()) {
                    AntwortComponent antwortComponent = antwortComponents.get(userAntwort.getAntwortID());
                    if (antwortComponent instanceof AntwortCheckBox aCB) {
                        if (aCB.isSelected() == originalAntwort.istWahr()) {
                            aCB.setBorder(rightBorder);
                        } else {
                            aCB.setBorder(wrongBorder);
                            richtigeAntwort = false;
                        }
                    } else if (antwortComponent instanceof AntwortRadioButton aRB) {
                        if (aRB.isSelected() == originalAntwort.istWahr()) {
                            aRB.setBorder(rightBorder);
                        } else {
                            aRB.setBorder(wrongBorder);
                            richtigeAntwort = false;
                        }
                    }
                }
            }
        }

        updateStatistik(userFrage, richtigeAntwort);

    }

    @Override
    public Frage getAktuelleFrage() {
        return null;
    }

    public int getFragenIndex() {
        return fragenIndex;
    }

    public Thema getThema() {
        return thema;
    }

    public void setThema(Thema thema) {
        this.thema = thema;
    }

    private void initButtons() {

        // Buttons and Icons
        vorherigeButton = new JButton("zurück");
        nächsteButton = new JButton("vor");
        checkFrageButton = new JButton("OK");
        checkFrageButton.setToolTipText("Antwort einloggen");
        beendenButton = new JButton("Beenden");
        beendenButton.setHorizontalAlignment(SwingConstants.RIGHT);
        nächsteButton.addActionListener(e -> {nächsteFrage();});
        vorherigeButton.addActionListener(e -> {vorherigeFrage();});
        checkFrageButton.addActionListener(e -> {checkAntwort();});

        Dimension buttonDimension = new Dimension(120, 24);
        Image zurückButtonImage;
        Image nächsteButtonImage = null;
        Image einloggenButtonImage = null;
        Image beendenButtonImage = null;

        try {

            URL zurückButtonURL =  getClass().getResource("..\\..\\img\\arrow_back_FILL0_24.png");
            if (zurückButtonURL != null) {
                zurückButtonImage = ImageIO.read(zurückButtonURL);
                Icon zurückIcon = new ImageIcon(zurückButtonImage);
                vorherigeButton.setIcon(zurückIcon);
                vorherigeButton.setMaximumSize(buttonDimension);
                vorherigeButton.setPreferredSize(buttonDimension);
            }

            URL nächsteButtonURL = getClass().getResource("..\\..\\img\\arrow_forward_FILL0_24.png");
            if (nächsteButtonURL != null) {
                nächsteButtonImage = ImageIO.read(nächsteButtonURL);
                Icon nächsteButtonIcon = new ImageIcon(nächsteButtonImage);
                nächsteButton.setIcon(nächsteButtonIcon);
                nächsteButton.setMaximumSize(buttonDimension);
                nächsteButton.setPreferredSize(buttonDimension);
                nächsteButton.setHorizontalTextPosition(SwingConstants.LEFT);
            }

            URL einloggenButtonURL = getClass().getResource("..\\..\\img\\done_FILL0_24.png");
            if (einloggenButtonURL != null) {
                einloggenButtonImage = ImageIO.read(einloggenButtonURL);
                Icon einloggenButtonIcon = new ImageIcon(einloggenButtonImage);
                checkFrageButton.setIcon(einloggenButtonIcon);
                checkFrageButton.setMaximumSize(buttonDimension);
                checkFrageButton.setPreferredSize(buttonDimension);
            }

            URL beendenButtonURL = getClass().getResource("..\\..\\img\\logout_FILL0_24.png");
            if (beendenButtonURL != null) {
                beendenButtonImage = ImageIO.read(beendenButtonURL);
                Icon beendenButtonIcon = new ImageIcon(beendenButtonImage);
                beendenButton.setIcon(beendenButtonIcon);
                beendenButton.setMaximumSize(buttonDimension);
                beendenButton.setPreferredSize(buttonDimension);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Border und Separator
        EmptyBorder insetBorder = new EmptyBorder(new Insets(0, 8, 0, 8));
        JSeparator blätternSeparator = new JSeparator(JSeparator.VERTICAL);
        blätternSeparator.setBorder(insetBorder);
        JSeparator einloggenSeparator = new JSeparator(JSeparator.VERTICAL);
        einloggenSeparator.setBorder(insetBorder);

        // Panel und Layout
        GridLayout gridLayout = new GridLayout(0, 7);
        gridLayout.setHgap(2);
        JPanel selectButtonsPanel = new JPanel(gridLayout);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        selectButtonsPanel.add(vorherigeButton, constraints);

        constraints.gridx = 1;
        selectButtonsPanel.add(nächsteButton, constraints);

        selectButtonsPanel.add(Box.createHorizontalGlue());
        constraints.gridx = 3;
        selectButtonsPanel.add(checkFrageButton, constraints);

        selectButtonsPanel.add(Box.createHorizontalGlue());
        selectButtonsPanel.add(Box.createHorizontalGlue());
        constraints.gridx = 6;
        constraints.anchor = GridBagConstraints.LINE_END;
        selectButtonsPanel.add(beendenButton, constraints);

        abfragePanel.setBorder(new EmptyBorder(8,5,8,5));
        abfragePanel.add(selectButtonsPanel, BorderLayout.PAGE_END);

        fragePanel = new FragePanel(frageList.get(fragenIndex), false);
        aktiveFragePanel.add(fragePanel);

    }

    private void initFrageList() {

        frageList = thema.getFrageList();
        Collections.shuffle(frageList);

        frageJListDefaultListModel = new DefaultListModel<>();
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            // gewählte Frage laden
            if (!e.getValueIsAdjusting()) {
                fragenIndex = fragenJList.getSelectedIndex();
                if (fragenIndex != -1) {
                    loadFrage(fragenIndex);
                }
            }
        });

        int i = 1;
        for (Frage frage : frageList) {
            frage.setFragenNummer(i);
            frageJListDefaultListModel.addElement(frage);
            i++;
        }

        fragenJList.setModel(frageJListDefaultListModel);
        fragenJList.setSelectionModel(selectionModel);
    }

    private void initStatistik() {

        thema = controller.loadThemaStatistik(thema);

        for (Frage frage : thema.getFrageList()) {

            int fragenBeantwortet = frage.getAbgefragt();
            int fragenDavonRichtig = frage.getDavonRichtig();
            int fragenDavonFalsch = frage.getDavonFalsch();

            fragenBeantwortetGesamt += fragenBeantwortet;
            fragenDavonRichtigGesamt += fragenDavonRichtig;
            fragenDavonFalschGesamt += fragenDavonFalsch;
        }

        updateLabels();
    }

    private void loadFrage(int nr) {

        aktiveFragePanel.removeAll();
        fragePanel = new FragePanel(frageList.get(nr), false);
        aktiveFragePanel.add(fragePanel);
        aktiveFragePanel.updateUI();
    }

    private void nächsteFrage() {
        if (fragenIndex < frageList.size() - 1) {
            fragenIndex++;
            fragenJList.setSelectedIndex(fragenIndex);
            loadFrage(fragenIndex);
        }
    }

    private void updateLabels() {

        float erfolgsQuote = (float) fragenDavonRichtigGesamt / (float) fragenBeantwortetGesamt * 100;
        fragenBeantwortetGesamtLabelWert.setText(String.valueOf(fragenBeantwortetGesamt));
        davonRichtigGesamtLabelWert.setText(String.valueOf(fragenDavonRichtigGesamt));
        davonFalschGesamtLabelWert.setText(String.valueOf(fragenDavonFalschGesamt));
        erfolgsquoteGesamtLabelWert.setText(String.format("%.02f", erfolgsQuote) + " %");

    }

    private void updateStatistik(Frage userFrage, boolean richtigeAntwort) {

        fragenBeantwortetGesamt++;

        if (richtigeAntwort) {
            fragenDavonRichtigGesamt++;
        } else {
            fragenDavonFalschGesamt++;
        }

        updateLabels();

        String username = controller.getUsername();
        int frage_id = userFrage.getFrageID();
        controller.updateStatistik(username, frage_id, richtigeAntwort);
    }

    private void vorherigeFrage() {
        if (fragenIndex > 0) {
            fragenIndex--;
            fragenJList.setSelectedIndex(fragenIndex);
            loadFrage(fragenIndex);
        }
    }

}
