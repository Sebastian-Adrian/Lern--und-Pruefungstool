package view.ZuweisenPanelJTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class CheckAndAddNode implements Runnable {

    private volatile boolean isUnique = true;
    private final DefaultMutableTreeNode newNode;
    private final TreePath treePath;
    private final DefaultMutableTreeNode parentNode;

    public CheckAndAddNode(DefaultMutableTreeNode newNode, TreePath treePath, DefaultMutableTreeNode parentNode) {
        this.newNode = newNode;
        this.treePath = treePath;
        this.parentNode = parentNode;
    }

    @Override
    public void run() {

        int childCount = parentNode.getChildCount();
        Object newNodeObject = newNode.getUserObject();

        for (int i = 0; i < childCount; i++) {

            DefaultMutableTreeNode lastPathNode = (DefaultMutableTreeNode) parentNode.getChildAt(i);
            Object lastPathObject = lastPathNode.getUserObject();

            if (lastPathObject.hashCode() == newNodeObject.hashCode()) {
                isUnique = false;
                break;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wenn nicht vorhanden einfügen
        if (isUnique) {
            parentNode.insert(newNode, childCount);
        }

    }

    public boolean isUnique() {
        return isUnique;
    }
}
