package view.ThemenEditor;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddNewFrage extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextPane hinweisTextPane;
    private JTextField fragenTextField;
    private JLabel fragenTextLabel;
    private JRadioButton multipleChoiceSelectRbutton;
    private JRadioButton multipleChoiceCheckRbutton;
    private JRadioButton singleChoiceRadioButton;
    private JRadioButton texteingabeRadioButton;
    private final ThemenEditorSwing themenEditorSwing;

    public AddNewFrage(ThemenEditorSwing themenEditorSwing) {

        this.themenEditorSwing = themenEditorSwing;

        Dimension dimension = new Dimension(600, 400);
        setSize(dimension);
        setMinimumSize(dimension);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // onCancel() wenn X geklickt
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() wenn ESC gedrückt
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }

    private void onOK() {

        int thema_id = themenEditorSwing.getThemaEdit().getThemaNr();

        Frage frage = null;
        int fragenNummer = themenEditorSwing.getThemaEdit().getNextNumber();
        String fragenText = fragenTextField.getText();

        if (multipleChoiceCheckRbutton.isSelected()) {
            frage = new MultipleChoiceCheckFrage(fragenText, thema_id, fragenNummer);
        } else if (multipleChoiceSelectRbutton.isSelected()) {
            frage = new MultipleChoiceSelectFrage(fragenText, thema_id, fragenNummer);
        } else if (singleChoiceRadioButton.isSelected()) {
            frage = new SingleChoiceFrage(fragenText, thema_id, fragenNummer);
        } else if (texteingabeRadioButton.isSelected()) {
            frage = new TextEingabeFrage(fragenText, thema_id, fragenNummer);
        }

        this.themenEditorSwing.addFrage(frage);
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
