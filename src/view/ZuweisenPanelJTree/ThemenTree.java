package view.ZuweisenPanelJTree;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

public class ThemenTree extends JTree {


    public ThemenTree(TreeNode root) {
        super(root);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setDragEnabled(true);
        this.setTransferHandler(new ThemenTransferHandler());
        this.setCellRenderer(new ZuweisungCellRenderer());
    }
}
