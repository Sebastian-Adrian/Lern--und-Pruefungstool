package view.ZuweisenPanelJTree;

import model.Fach;
import model.Gruppe;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZuweisenPanel extends JFrame implements view.ZuweisenPanel {

    private JTree gruppenTree;
    private JLabel gruppenLabel;
    private JLabel themenLabel;
    private JTree themenTree;
    private JToolBar toolBar;
    private JScrollPane scrollPaneGruppenTree;
    private JScrollPane scrollPaneThemenTree;
    private JButton saveButton;
    private JPanel rootPane;

    public ZuweisenPanel() {

        super("Themen zuweisen");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(rootPane);
        Dimension frameSize = new Dimension(800, 600);
        this.setPreferredSize(frameSize);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void addFach(Fach fach) {

        DefaultTreeModel model = (DefaultTreeModel) themenTree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();

        if (rootNode == null) {
            rootNode = new DefaultMutableTreeNode();
        }

        DefaultMutableTreeNode fachNode = new DefaultMutableTreeNode(fach);
        HashMap<Integer, String> zugewieseneThemen = fach.getZugewieseneThemen();

        if (zugewieseneThemen != null) {
            for (Map.Entry<Integer, String> zugewiesenesThema : zugewieseneThemen.entrySet()) {

                DefaultMutableTreeNode thema = new DefaultMutableTreeNode(zugewiesenesThema);
                fachNode.add(thema);
            }
        }

        rootNode.add(fachNode);
        model.reload();
        model.setRoot(rootNode);

    }

    public void addGruppe(Gruppe gruppe) {

        DefaultTreeModel model = (DefaultTreeModel) gruppenTree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();

        if (rootNode == null) {
            rootNode = new DefaultMutableTreeNode();
        }

        DefaultMutableTreeNode gruppeNode = new DefaultMutableTreeNode(gruppe);
        HashMap<Integer, String> zugewieseneThemen = gruppe.getZugewieseneThemen();

        if (zugewieseneThemen != null) {
            for (Map.Entry<Integer, String> zugewiesenesThema : zugewieseneThemen.entrySet()) {

                DefaultMutableTreeNode themaNode = new DefaultMutableTreeNode(zugewiesenesThema);
                gruppeNode.add(themaNode);
            }
        }

        rootNode.add(gruppeNode);
        model.reload();
        model.setRoot(rootNode);
    }

    public void addSaveButtonActionListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    private void createUIComponents() {
        this.gruppenTree = new GruppenTree(new DefaultMutableTreeNode());
        this.themenTree = new ThemenTree(new DefaultMutableTreeNode());
    }

    public List<Gruppe> getGruppenNodes() {

        DefaultTreeModel gruppenModel = (DefaultTreeModel) gruppenTree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) gruppenModel.getRoot();

        List<Gruppe> gruppeList = new ArrayList<>();

        int childCount = rootNode.getChildCount();

        for (int i = 0; i < childCount; i++) {

            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            Object childObject = childNode.getUserObject();

            if (childObject instanceof Gruppe gruppe) {

                try {
                    Gruppe newGruppe = gruppe.clone();

                    if (childNode.getChildCount() > 0) {
                        for (int j = 0; j < childNode.getChildCount(); j++) {

                            DefaultMutableTreeNode leafNode = (DefaultMutableTreeNode) childNode.getChildAt(j);
                            Object leafNodeObject = leafNode.getUserObject();

                            if (leafNodeObject instanceof Map.Entry<?, ?> entry) {
                                newGruppe.addZugewiesenesThema(entry);
                            }
                        }
                    }
                    gruppeList.add(newGruppe);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return gruppeList;
    }
}
