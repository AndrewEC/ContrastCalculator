package gov.intra.net.persist;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardImage implements Transferable {

	private final Image i;

	public ClipboardImage(Image i) {
		this.i = i;
	}

	public Object getTransferData(DataFlavor df) throws UnsupportedFlavorException, IOException {
		if (df.equals(DataFlavor.imageFlavor) && i != null) {
			return i;
		}
		throw new UnsupportedFlavorException(df);
	}

	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] df = new DataFlavor[1];
		df[0] = DataFlavor.imageFlavor;
		return df;
	}

	public boolean isDataFlavorSupported(DataFlavor df) {
		DataFlavor[] flavors = getTransferDataFlavors();
		for (DataFlavor f : flavors) {
			if (f.equals(df)) {
				return true;
			}
		}
		return false;
	}

	public void addToClipboard() {
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents(this, null);
	}

}
