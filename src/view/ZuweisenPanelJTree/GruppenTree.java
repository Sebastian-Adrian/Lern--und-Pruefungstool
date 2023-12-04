package view.ZuweisenPanelJTree;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class GruppenTree extends JTree {

    public GruppenTree(TreeNode root) {
        super(root);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setDragEnabled(true);
        this.setTransferHandler(new GruppenTransferHandler());
        this.setCellRenderer(new ZuweisungCellRenderer());
        this.addKeyListener(new GruppenKeyListener());
        this.setDropMode(DropMode.USE_SELECTION);
    }
}
