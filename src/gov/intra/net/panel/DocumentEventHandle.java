package gov.intra.net.panel;

import gov.intra.net.util.Contraster;
import gov.intra.net.util.HexValidator;

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import resources.Constants.ColourLayer;

public class DocumentEventHandle implements DocumentListener {

	private final Panel panel;

	public DocumentEventHandle(Panel panel) {
		this.panel = panel;
	}

	public void changedUpdate(DocumentEvent e) {
	}

	public void insertString(int offset) {
	}

	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == panel.getForeHex().getDocument()) {
			setColour(ColourLayer.FOREGROUND);
		} else if (e.getDocument() == panel.getBackHex().getDocument()) {
			setColour(ColourLayer.BACKGROUND);
		}
	}

	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == panel.getForeHex().getDocument()) {
			setColour(ColourLayer.FOREGROUND);
		} else if (e.getDocument() == panel.getBackHex().getDocument()) {
			setColour(ColourLayer.BACKGROUND);
		}
	}

	private void setColour(ColourLayer layer) {
		String text = "";
		if (layer == ColourLayer.FOREGROUND) {
			text = panel.getForeHex().getText();
		} else if (layer == ColourLayer.BACKGROUND) {
			text = panel.getBackHex().getText();
		}
		if (HexValidator.isValid6Hex(text)) {
			Color c = HexValidator.hexToColour(text);
			if (layer == ColourLayer.FOREGROUND) {
				PanelUtil.setForeColour(panel, c, false);
			} else if (layer == ColourLayer.BACKGROUND) {
				PanelUtil.setBackColour(panel, c, false);
			}
			inputContrast();
		}
	}

	private void inputContrast() {
		String fore = panel.getForeHex().getText();
		String back = panel.getBackHex().getText();
		if (HexValidator.isValid6Hex(fore) && HexValidator.isValid6Hex(back)) {
			Contraster c = new Contraster();
			c.setForeground(HexValidator.hexToColour(fore));
			c.setBackground(HexValidator.hexToColour(back));
			double val = c.calculateContrast();
			PanelUtil.setRatio(panel, val);
		}
	}

}
