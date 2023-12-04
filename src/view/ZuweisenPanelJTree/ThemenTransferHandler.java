package view.ZuweisenPanelJTree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.datatransfer.Transferable;
import java.util.Map;

public class ThemenTransferHandler extends TransferHandler {

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    @Override
    @SuppressWarnings("unchecked")
    // TODO: unchecked nötig?
    protected Transferable createTransferable(JComponent c) {

        if (c instanceof JTree jTree) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
            Object object = node.getUserObject();

            if (object instanceof Map.Entry<?,?> entry) {
                return new TransferableThema((Map.Entry<Integer, String>) entry);
            }
        }
        return null;
    }

}
