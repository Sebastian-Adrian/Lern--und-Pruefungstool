package view.ZuweisenPanelJTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class GruppenKeyListener implements KeyListener {


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        //Beim Drücken der DEL-Taste selektierte Node überprüfen. Wenn Node ein Thema, löschen.
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_DELETE) {

            Object sourceObject = e.getSource();

            if (sourceObject instanceof GruppenTree tree) {

                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                TreePath treePath = tree.getSelectionPath();

                if (treePath != null) {

                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    Object selectedNodeObject = selectedNode.getUserObject();

                    if (selectedNodeObject instanceof Map.Entry<?, ?>) {
                        model.removeNodeFromParent(selectedNode);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
