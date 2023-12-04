package view.ZuweisenPanelJTree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Map;

public class GruppenTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(TransferSupport support) {

        Transferable transferable = support.getTransferable();
        DataFlavor[] dataFlavors = transferable.getTransferDataFlavors();
        Object object = null;

        try {
            object = transferable.getTransferData(dataFlavors[0]);

        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }

        return object instanceof Map.Entry<?, ?>;
    }

    @Override
    // Cast-Möglichkeit in der canImport Methode überprüft
    @SuppressWarnings("unchecked")
    public boolean importData(TransferSupport support) {

        if (!canImport(support)) {
            return false;
        }

        JTree.DropLocation dropLocation = (JTree.DropLocation) support.getDropLocation();
        TreePath treePath = dropLocation.getPath();
        Transferable transferable = support.getTransferable();
        JTree tree = (JTree) support.getComponent();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DataFlavor[] dataFlavors = transferable.getTransferDataFlavors();

        Map.Entry<Integer, String> themaObject = null;

        if (dataFlavors != null) {
            try {
                themaObject = (Map.Entry<Integer, String>) transferable.getTransferData(dataFlavors[0]);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }

        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(themaObject, false);
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();

        // Zuweisung nicht in andere Zuweisung einfügen
        if (!(parentNode.getUserObject() instanceof Map.Entry<?, ?>)) {

            // leerer Ordner/Gruppe
            if (parentNode.isLeaf()) {
                parentNode.add(newNode);
                return true;
            } else {
                try {
                    CheckAndAddNode checkAndAddNode = new CheckAndAddNode(newNode, treePath, parentNode);
                    Thread thread = new Thread(checkAndAddNode);
                    thread.start();
                    thread.join();
                    if (checkAndAddNode.isUnique()) {
                        model.reload();
                        tree.expandPath(treePath);
                        return true;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
