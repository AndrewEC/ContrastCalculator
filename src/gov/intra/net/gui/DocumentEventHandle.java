package gov.intra.net.gui;

import gov.intra.net.util.Contraster;

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentEventHandle implements DocumentListener {

	private final Panel panel;

	public DocumentEventHandle(Panel panel) {
		this.panel = panel;
	}

	public void changedUpdate(DocumentEvent e) {
	}

	public void insertString(int offset){
	}
	
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == panel.getForeHex().getDocument()) {
			foreground();
		} else if (e.getDocument() == panel.getBackHex().getDocument()) {
			background();
		}
	}

	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == panel.getForeHex().getDocument()) {
			foreground();
		} else if (e.getDocument() == panel.getBackHex().getDocument()) {
			background();
		}
	}

	private void foreground() {
		String text = panel.getForeHex().getText();
		if (Contraster.isValidHex(text)) {
			Color c = Contraster.hexToColour(text);
			panel.getEventHandle().setForeColour(c, false);
			inputContrast();
		}
	}

	private void background() {
		String text = panel.getBackHex().getText();
		if (Contraster.isValidHex(text)) {
			Color c = Contraster.hexToColour(text);
			panel.getEventHandle().setBackColour(c, false);
			inputContrast();
		}
	}

	private void inputContrast() {
		String fore = panel.getForeHex().getText();
		String back = panel.getBackHex().getText();
		if (Contraster.isValidHex(fore) && Contraster.isValidHex(back)) {
			Contraster c = new Contraster();
			c.setForeground(Contraster.hexToColour(fore));
			c.setBackground(Contraster.hexToColour(back));
			double val = c.calculateContrast();
			panel.getEventHandle().setRatio(val);
		}
	}

}
