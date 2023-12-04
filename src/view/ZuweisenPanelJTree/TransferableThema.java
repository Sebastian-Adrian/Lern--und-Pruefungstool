package view.ZuweisenPanelJTree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Map;

public class TransferableThema implements Transferable {

    protected static DataFlavor themaFlavor = new DataFlavor(Map.Entry.class, "Thema");
    protected static DataFlavor[] supportedFlavors = {themaFlavor};
    private final Map.Entry<Integer, String> thema;

    public TransferableThema(Map.Entry<Integer, String> thema) {
        this.thema = thema;
    }


    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(themaFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(themaFlavor)) {
            return thema;
        } else
            throw new UnsupportedFlavorException(flavor);
    }
}
