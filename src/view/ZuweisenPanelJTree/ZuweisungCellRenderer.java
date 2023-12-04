package view.ZuweisenPanelJTree;

import model.Fach;
import model.Gruppe;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Map;

public class ZuweisungCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        this.hasFocus = hasFocus;
        this.selected = sel;

        if (value instanceof DefaultMutableTreeNode node) {

            Object nodeObject = node.getUserObject();
            if (nodeObject instanceof Gruppe || nodeObject instanceof Fach) {
                setText(value.toString());
                setIcon(UIManager.getIcon("FileView.directoryIcon"));

            } else if (nodeObject instanceof Map.Entry<?,?> entry){
                setText(entry.getValue().toString());
                setIcon(UIManager.getIcon("FileView.fileIcon"));
            }
        }

        return this;
    }
}
