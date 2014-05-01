package gov.intra.net.gui;

import gov.intra.net.util.HexValidator;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class LimitedDocument extends PlainDocument {

	private final int limit;
	private JComponent parent;
	private boolean showError = true;

	public LimitedDocument(int limit, JComponent parent) {
		super();
		this.limit = limit;
		this.parent = parent;
	}

	public void setShowError(boolean showError) {
		this.showError = showError;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}

		if (str.startsWith(" ") || str.endsWith(" ")) {
			str.trim();
		}

		if (offset == 0 && str.length() == 1) {
			char c = str.toCharArray()[0];
			if (c != '#') {
				if (showError && parent != null) {
					JOptionPane.showMessageDialog(parent, "The first character in the hex must be the '#' symbol.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
		}

		if (str.length() > 1 && ofLength(str)) {
			return;
		}

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}

	private boolean ofLength(String str) {
		if (str.length() != 7) {
			JOptionPane.showMessageDialog(parent, "The pasted value needs to be 7 characters in length. Including the initial # character.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		if (!HexValidator.isValid6Hex(str)) {
			if (showError && parent != null) {
				JOptionPane.showMessageDialog(parent, "The pasted value is not a proper hex format.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return true;
		}
		return false;
	}

}
